package com.valora.api.domain.Usuario;

public record DadosListagemUsuarioDTO(Long id,String nome, String email, String telefone) {

    public DadosListagemUsuarioDTO(UsuarioModel usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone());
    }
}
