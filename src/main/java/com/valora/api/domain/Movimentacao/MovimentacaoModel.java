package com.valora.api.domain.Movimentacao;

import com.valora.api.domain.Caixinha.CaixinhaModel;
import com.valora.api.domain.Categoria.CategoriaModel;
import com.valora.api.domain.Conta.ContaModel;
import com.valora.api.domain.Usuario.UsuarioModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "T_VLR_MOVIMENTACAO")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class MovimentacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ajuste conforme a sua sequence no Oracle (ex: GenerationType.SEQUENCE)
    @Column(name = "ID_MOVIMENTACAO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private UsuarioModel usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CONTA")
    private ContaModel contaOrigem;

    @ManyToOne
    @JoinColumn(name = "ID_CONTA_DESTINO")
    private ContaModel contaDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CAIXINHA")
    private CaixinhaModel caixinha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private CategoriaModel categoria;

    // COLUNAS DA TABELA

    @Column(name = "DS_MOVIMENTACAO")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "TP_MOVIMENTACAO", nullable = false)
    private TipoMovimentacao tipo;

    @Column(name = "VL_MOVIMENTACAO", nullable = false)
    private BigDecimal valor;

    @Column(name = "DT_MOVIMENTACAO", nullable = false)
    private LocalDate dataMovimentacao;

    @Column(name = "CD_BELVO_TRANSACAO")
    private String codigoBelvo;

    @Column(name = "FL_INSERIDA_MANUAL", nullable = false)
    private Boolean inseridaManual;

    @Column(name = "DT_CRIACAO", updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "FL_ATIVO")
    private Boolean ativo;

    // CONSTRUTOR PARA CADASTRO
    public MovimentacaoModel(CadastroMovimentacaoDTO dto, UsuarioModel usuario, ContaModel conta, CategoriaModel categoria, CaixinhaModel caixinha) {
        this.ativo = true;
        this.usuario = usuario;
        this.contaOrigem = conta;
        this.categoria = categoria;
        this.caixinha = caixinha;
        this.dataCriacao = LocalDateTime.now();
        this.inseridaManual = true;

        this.descricao = dto.descricao();
        this.valor = dto.valor();
        this.tipo = dto.tipo();
        this.dataMovimentacao = dto.dataMovimentacao();

    }

    public void desativar() {
        this.ativo = false;
    }

    public void atualizarInformacoes(AtualizaMovimentacaoDTO dto, ContaModel contaNew) {
        if (dto.descricao() != null) {
            this.descricao = dto.descricao();
        }
        if (dto.valor() != null) {
            this.valor = dto.valor();
        }
        if (dto.dataMovimentacao() != null) {
            this.dataMovimentacao = dto.dataMovimentacao();
        }
        if (dto.tipo() != null) {
            this.tipo = dto.tipo();
        }
        if (dto.idConta() != null) {
            this.contaOrigem = contaNew;
        }
    }
}
