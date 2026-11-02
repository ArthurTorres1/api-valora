package com.valora.api.controller;

import com.valora.api.domain.Conta.ContaModel;
import com.valora.api.domain.Movimentacao.*;
import com.valora.api.domain.Usuario.UsuarioModel;
import com.valora.api.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CaixinhaRepository caixinhaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<MovimentacaoDetalhadaDTO> cadastrar(
            @RequestBody @Valid CadastroMovimentacaoDTO dto,
            @AuthenticationPrincipal UsuarioModel usuarioLogado,
            UriComponentsBuilder uriBuilder) {

        if (dto.idConta() == null && dto.idCaixinha() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "A movimentação precisa estar vinculada a uma Conta ou a uma Caixinha.");
        }

        var categoria = categoriaRepository.findById(dto.idCategoria())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria inválida"));

        var conta = dto.idConta() != null
                ? contaRepository.findByIdAndUsuario(dto.idConta(), usuarioLogado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada"))
                : null;

        var contaDestino = dto.idContaDestino() != null
                ? contaRepository.findByIdAndUsuario(dto.idContaDestino(), usuarioLogado)
                  .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta destino não encontrada"))
                : null;

        var caixinha = dto.idCaixinha() != null
                ? caixinhaRepository.findByIdAndUsuario(dto.idCaixinha(), usuarioLogado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Caixinha não encontrada"))
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
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Para guardar dinheiro, informe a conta de origem e a caixinha de destino.");
                }
                conta.debitar(dto.valor());
                caixinha.creditar(dto.valor());
            }
            case CAIXINHA_RESGATE -> {
                if (conta == null || caixinha == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Para resgatar dinheiro, informe a caixinha de origem e a conta de destino.");
                }
                caixinha.debitar(dto.valor());
                conta.creditar(dto.valor());
            }
            case TRANSFERENCIA_ENTRE_CONTAS -> {
                if (conta == null || contaDestino == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,"Transferência entre contas exige uma conta de origem e uma de destino.");
                }
                conta.debitar(dto.valor());
                contaDestino.creditar(dto.valor());
            }
        }

        var novaMovimentacao = new MovimentacaoModel(dto, usuarioLogado, conta, categoria, caixinha);
        movimentacaoRepository.save(novaMovimentacao);

        var uri = uriBuilder.path("/api/movimentacao/{id}").buildAndExpand(novaMovimentacao.getId()).toUri();
        return ResponseEntity.created(uri).body(new MovimentacaoDetalhadaDTO(novaMovimentacao));
    }

    @GetMapping("/listar-movimentacoes")
    public ResponseEntity<Page<DadosListagemMovimentacaoDTO>> listarMovimentacoes(
            @RequestParam(required = false) Long contaId,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(required = false) TipoMovimentacao tpMovimentacao,
            @PageableDefault(size = 12, page = 0, sort = {"dataMovimentacao"}) Pageable page,
            @AuthenticationPrincipal UsuarioModel usuarioLogado) {

        var listagem = movimentacaoRepository
                .buscarComFiltrosOpcionais(usuarioLogado, contaId, categoriaId, tpMovimentacao, page)
                .map(DadosListagemMovimentacaoDTO::new);

        return ResponseEntity.ok(listagem);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MovimentacaoDetalhadaDTO> detalharMovimentacao(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioModel usuarioLogado) {

        var movimentacao = movimentacaoRepository.findByIdAndUsuario(id, usuarioLogado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimentação não encontrada"));

        return ResponseEntity.ok(new MovimentacaoDetalhadaDTO(movimentacao));
    }

    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity atualizarMovimentacao(
            @PathVariable Long id,
            @RequestBody @Valid AtualizaMovimentacaoDTO dto,
            @AuthenticationPrincipal UsuarioModel usuarioLogado) {

        var movimentacao = movimentacaoRepository.findByIdAndUsuario(id, usuarioLogado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimentação não encontrada"));

        ContaModel contaNew = null;
        if(dto.idConta() != null) {
           contaNew = contaRepository.findByIdAndUsuario(dto.idConta(), usuarioLogado)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta inexistente"));
        }


        movimentacao.atualizarInformacoes(dto, contaNew);

        return ResponseEntity.ok(new MovimentacaoDetalhadaDTO(movimentacao));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioModel usuarioLogado) {

        var movimentacao = movimentacaoRepository.findByIdAndUsuario(id, usuarioLogado)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimentação não encontrada"));

        movimentacao.desativar();
        return ResponseEntity.noContent().build();
    }
}