package com.valora.api.dto;

import com.valora.api.model.Usuario.UsuarioModel;

public record DadosListagemUsuarioDTO(String nome, String email, String telefone) {

    public DadosListagemUsuarioDTO(UsuarioModel usuario) {
        this(usuario.getNome(), usuario.getEmail(), usuario.getTelefone());
    }
}
