package com.valora.api.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizaUsuarioDTO(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco
) {
}
