package com.valora.api.domain.Usuario;

import com.valora.api.domain.Endereco.Endereco;

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
