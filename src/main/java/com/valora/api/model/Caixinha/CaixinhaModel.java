package com.valora.api.model.Caixinha;

import com.valora.api.model.Usuario.UsuarioModel;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "T_VLR_CAIXINHA")
public class CaixinhaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CAIXINHA")
    private Long idCaixinha;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private UsuarioModel usuario;

    @Column(name = "NM_CAIXINHA", nullable = false, length = 100)
    private String nmCaixinha;

    @Column(name = "VL_META", nullable = false, precision = 10, scale = 2)
    private BigDecimal vlMeta;

    @Column(name = "VL_ATUAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal vlAtual;

    @Column(name = "DT_CRIACAO", nullable = false)
    private LocalDate dtCriacao = LocalDate.now();

    public CaixinhaModel() {}

    public CaixinhaModel(Long idCaixinha, UsuarioModel usuario, String nmCaixinha, BigDecimal vlMeta, BigDecimal vlAtual, LocalDate dtCriacao) {
        this.idCaixinha = idCaixinha;
        this.usuario = usuario;
        this.nmCaixinha = nmCaixinha;
        this.vlMeta = vlMeta;
        this.vlAtual = vlAtual;
        this.dtCriacao = dtCriacao;
    }

    public Long getIdCaixinha() { return idCaixinha; }
    public void setIdCaixinha(Long idCaixinha) { this.idCaixinha = idCaixinha; }

    public UsuarioModel getUsuario() { return usuario; }
    public void setUsuario(UsuarioModel usuario) { this.usuario = usuario; }

    public String getNmCaixinha() { return nmCaixinha; }
    public void setNmCaixinha(String nmCaixinha) { this.nmCaixinha = nmCaixinha; }

    public BigDecimal getVlMeta() { return vlMeta; }
    public void setVlMeta(BigDecimal vlMeta) { this.vlMeta = vlMeta; }

    public BigDecimal getVlAtual() { return vlAtual; }
    public void setVlAtual(BigDecimal vlAtual) { this.vlAtual = vlAtual; }

    public LocalDate getDtCriacao() { return dtCriacao; }
    public void setDtCriacao(LocalDate dtCriacao) { this.dtCriacao = dtCriacao; }
}