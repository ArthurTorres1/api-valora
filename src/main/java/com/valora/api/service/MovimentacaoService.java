package com.valora.api.service;

import com.valora.api.domain.Caixinha.CaixinhaModel;
import com.valora.api.domain.Categoria.CategoriaModel;
import com.valora.api.domain.Conta.ContaModel;
import com.valora.api.domain.Movimentacao.MovimentacaoModel;
import com.valora.api.domain.Movimentacao.TipoMovimentacao;
import com.valora.api.domain.Movimentacao.dto.AtualizaMovimentacaoDTO;
import com.valora.api.domain.Movimentacao.dto.CadastroMovimentacaoDTO;
import com.valora.api.domain.Movimentacao.dto.DadosListagemMovimentacaoDTO;
import com.valora.api.domain.Usuario.UsuarioModel;
import com.valora.api.infra.exception.RecursoNaoEncontradoException;
import com.valora.api.infra.exception.ValidacaoException;
import com.valora.api.repository.CaixinhaRepository;
import com.valora.api.repository.CategoriaRepository;
import com.valora.api.repository.ContaRepository;
import com.valora.api.repository.MovimentacaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CaixinhaRepository caixinhaRepository;


    public MovimentacaoModel salvarMovimentacao(CadastroMovimentacaoDTO dto, UsuarioModel usuarioLogado) {
        if (dto.idConta() == null && dto.idCaixinha() == null) {
            throw new ValidacaoException("A movimentação precisa estar vinculada a uma Conta ou a uma Caixinha.");
        }

        var categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new ValidacaoException("Categoria inválida"));

        var conta = dto.idConta() != null
                ? contaRepository.findByIdAndUsuario(dto.idConta(), usuarioLogado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta não encontrada"))
                : null;

        var contaDestino = dto.idContaDestino() != null
                ? contaRepository.findByIdAndUsuario(dto.idContaDestino(), usuarioLogado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta destino não encontrada"))
                : null;

        var caixinha = dto.idCaixinha() != null
                ? caixinhaRepository.findByIdAndUsuario(dto.idCaixinha(), usuarioLogado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Caixinha não encontrada"))
                : null;

        switch (dto.tipo()) {
            case DESPESA -> {
                if (conta != null) conta.debitar(dto.valor());
                else caixinha.debitar(dto.valor());
            }
            case RECEITA -> {
                if (conta != null) conta.creditar(dto.valor());
                else caixinha.creditar(dto.valor());
            }
            case CAIXINHA_GUARDAR -> {
                if (conta == null || caixinha == null) {
                    throw new ValidacaoException("Para guardar dinheiro, informe a conta de origem e a caixinha de destino.");
                }
                conta.debitar(dto.valor());
                caixinha.creditar(dto.valor());
            }
            case CAIXINHA_RESGATE -> {
                if (conta == null || caixinha == null) {
                    throw new ValidacaoException("Para resgatar dinheiro, informe a caixinha de origem e a conta de destino.");
                }
                caixinha.debitar(dto.valor());
                conta.creditar(dto.valor());
            }
            case TRANSFERENCIA_ENTRE_CONTAS -> {
                if (conta == null || contaDestino == null) {
                    throw new ValidacaoException("Transferência entre contas exige uma conta de origem e uma de destino.");
                }
                conta.debitar(dto.valor());
                contaDestino.creditar(dto.valor());
            }
        }

        var novaMovimentacao = new MovimentacaoModel(dto, usuarioLogado, conta, categoria, caixinha);
        movimentacaoRepository.save(novaMovimentacao);
        return novaMovimentacao;
    }

    public Page<DadosListagemMovimentacaoDTO> getMovimentacoes(UsuarioModel usuarioLogado, Long contaId, Long categoriaId, TipoMovimentacao tpMovimentacao, Pageable page) {
        return movimentacaoRepository
                .buscarComFiltrosOpcionais(usuarioLogado, contaId, categoriaId, tpMovimentacao, page)
                .map(DadosListagemMovimentacaoDTO::new);
    }

    public MovimentacaoModel detalharMovimentacao(Long id, UsuarioModel usuarioLogado) {
        return movimentacaoRepository.findByIdAndUsuario(id, usuarioLogado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Movimentação não encontrada"));
    }


    @Transactional
    public MovimentacaoModel atualizarMovimentacao(Long id, @Valid AtualizaMovimentacaoDTO dto, UsuarioModel usuarioLogado) {
        var movimentacao = movimentacaoRepository.findByIdAndUsuario(id, usuarioLogado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Movimentação não encontrada"));

        // Guardar o estado ANTES da atualização para reverter corretamente
        BigDecimal valorAntigo = movimentacao.getValor();
        TipoMovimentacao tipoAntigo = movimentacao.getTipo();

        // 1. REVERTER O SALDO ANTIGO (Estorno)
        // Desfaz o que a movimentação original tinha feito nos saldos atuais
        reverterSaldos(movimentacao, valorAntigo, tipoAntigo);

        // 2. BUSCAR NOVAS ENTIDADES (Se o DTO enviou novos IDs, busca no banco. Se não, mantém os atuais)
        ContaModel contaOrigem = dto.idConta() != null
                ? contaRepository.findByIdAndUsuario(dto.idConta(), usuarioLogado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta origem inexistente"))
                : movimentacao.getContaOrigem();

        ContaModel contaDestino = dto.idContaDestino() != null
                ? contaRepository.findByIdAndUsuario(dto.idContaDestino(), usuarioLogado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Conta destino inexistente"))
                : movimentacao.getContaDestino();

        CaixinhaModel caixinha = dto.idCaixinha() != null
                ? caixinhaRepository.findByIdAndUsuario(dto.idCaixinha(), usuarioLogado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Caixinha inexistente"))
                : movimentacao.getCaixinha();

        CategoriaModel categoria = dto.idCategoria() != null
                ? categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new ValidacaoException("Categoria inválida"))
                : movimentacao.getCategoria();

        // 3. ATUALIZAR OS DADOS NA MODEL
        movimentacao.atualizarInformacoes(dto, contaOrigem, contaDestino, caixinha, categoria);
        // 4. APLICAR O NOVO SALDO
        // Executa a regra financeira com os dados já atualizados
        aplicarSaldos(movimentacao);

        return movimentacao;
    }

    @Transactional
    public void desativarMovimentacao(Long id, UsuarioModel usuarioLogado) {
        var movimentacao = movimentacaoRepository.findByIdAndUsuario(id, usuarioLogado)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Movimentação não encontrada"));

        // 1. Desfaz o impacto financeiro nas contas e caixinhas (Estorno)
        reverterSaldos(movimentacao, movimentacao.getValor(), movimentacao.getTipo());

        // 2. Marca a movimentação como inativa (Soft Delete)
        movimentacao.desativar();
    }


    private void reverterSaldos(MovimentacaoModel m, BigDecimal valorAntigo, TipoMovimentacao tipoAntigo) {
        // Usa o tipoAntigo no switch e o valorAntigo nos débitos/créditos
        switch (tipoAntigo) {
            case DESPESA -> {
                if (m.getContaOrigem() != null) m.getContaOrigem().creditar(valorAntigo);
                else m.getCaixinha().creditar(valorAntigo);
            }
            case RECEITA -> {
                if (m.getContaOrigem() != null) m.getContaOrigem().debitar(valorAntigo);
                else m.getCaixinha().debitar(valorAntigo);
            }
            case CAIXINHA_GUARDAR -> {
                m.getContaOrigem().creditar(valorAntigo);
                m.getCaixinha().debitar(valorAntigo);
            }
            case CAIXINHA_RESGATE -> {
                m.getCaixinha().creditar(valorAntigo);
                m.getContaOrigem().debitar(valorAntigo);
            }
            case TRANSFERENCIA_ENTRE_CONTAS -> {
                m.getContaOrigem().creditar(valorAntigo);
                m.getContaDestino().debitar(valorAntigo);
            }
        }
    }

    private void aplicarSaldos(MovimentacaoModel m) {
        switch (m.getTipo()) {
            case DESPESA -> {
                if (m.getContaOrigem() != null) m.getContaOrigem().debitar(m.getValor());
                else m.getCaixinha().debitar(m.getValor());
            }
            case RECEITA -> {
                if (m.getContaOrigem() != null) m.getContaOrigem().creditar(m.getValor());
                else m.getCaixinha().creditar(m.getValor());
            }
            case CAIXINHA_GUARDAR -> {
                if (m.getContaOrigem() == null || m.getCaixinha() == null) throw new ValidacaoException("Faltam contas para guardar.");
                m.getContaOrigem().debitar(m.getValor());
                m.getCaixinha().creditar(m.getValor());
            }
            case CAIXINHA_RESGATE -> {
                if (m.getContaOrigem() == null || m.getCaixinha() == null) throw new ValidacaoException("Faltam contas para resgate.");
                m.getCaixinha().debitar(m.getValor());
                m.getContaOrigem().creditar(m.getValor());
            }
            case TRANSFERENCIA_ENTRE_CONTAS -> {
                if (m.getContaOrigem() == null || m.getContaDestino() == null) throw new ValidacaoException("Faltam contas para transferência.");
                m.getContaOrigem().debitar(m.getValor());
                m.getContaDestino().creditar(m.getValor());
            }
        }
    }
}
