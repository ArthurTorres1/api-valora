package com.valora.api.domain.Conta;

import com.valora.api.domain.Usuario.UsuarioModel;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "Conta")
@Table(name = "T_VLR_CONTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ContaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONTA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private UsuarioModel usuario;

    @Column(name = "NM_CONTA", length = 100, nullable = false)
    private String nome;

    @Column(name = "VL_SALDO", precision = 19, scale = 2, nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "DT_CRIACAO", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "ST_ATIVO", nullable = false)
    private Boolean ativo;

    public ContaModel(@Valid CadastroContaDTO cadastroContaDTO, UsuarioModel usuario) {
        this.usuario = usuario;
        this.nome = cadastroContaDTO.nome();
        this.saldo = cadastroContaDTO.saldo();
        this.dataCriacao = LocalDateTime.now();
        this.ativo = true;
    }

    public void atualizarInformacoes(@Valid AtualizaContaDTO novaContaDTO) {
        if(novaContaDTO.nome() != null) {
            this.nome = novaContaDTO.nome();
        }
    }

    public void desativar() {
        this.ativo = false;
    }

    public void creditar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
    }

    public void debitar(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
    }
}