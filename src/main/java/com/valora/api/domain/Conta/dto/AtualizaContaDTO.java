package com.valora.api.domain.Conta.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizaContaDTO(
        @NotNull(message = "O ID da conta é obrigatório")
        Long id,

        String nome
) {
}