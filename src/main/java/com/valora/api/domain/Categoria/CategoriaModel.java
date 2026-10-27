package com.valora.api.domain.Categoria;

import com.valora.api.domain.Usuario.UsuarioModel;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "T_VLR_CATEGORIA")
public class CategoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CATEGORIA")
    private Long idCategoria;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private UsuarioModel usuario;

    @Column(name = "NM_CATEGORIA", nullable = false, length = 100)
    private String nmCategoria;

    @Column(name = "DT_CRIACAO", nullable = false)
    private LocalDate dtCriacao = LocalDate.now();

    public CategoriaModel() {}

    public CategoriaModel(Long idCategoria, UsuarioModel usuario, String nmCategoria, LocalDate dtCriacao) {
        this.idCategoria = idCategoria;
        this.usuario = usuario;
        this.nmCategoria = nmCategoria;
        this.dtCriacao = dtCriacao;
    }

    public Long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Long idCategoria) { this.idCategoria = idCategoria; }

    public UsuarioModel getUsuario() { return usuario; }
    public void setUsuario(UsuarioModel usuario) { this.usuario = usuario; }

    public String getNmCategoria() { return nmCategoria; }
    public void setNmCategoria(String nmCategoria) { this.nmCategoria = nmCategoria; }

    public LocalDate getDtCriacao() { return dtCriacao; }
    public void setDtCriacao(LocalDate dtCriacao) { this.dtCriacao = dtCriacao; }
}