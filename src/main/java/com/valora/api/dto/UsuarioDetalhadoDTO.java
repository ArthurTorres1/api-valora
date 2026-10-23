package com.valora.api.dto;

import com.valora.api.model.Endereco.Endereco;
import com.valora.api.model.Usuario.UsuarioModel;

public record UsuarioDetalhadoDTO(Long id, String nome, String email, String telefone, Endereco endereco) {
    public UsuarioDetalhadoDTO(UsuarioModel usuarioModel){
        this(
                usuarioModel.getId(),
                usuarioModel.getNome(),
                usuarioModel.getEmail(),
                usuarioModel.getTelefone(),
                usuarioModel.getEndereco()
        );
    }
}
