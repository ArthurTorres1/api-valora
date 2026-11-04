package com.valora.api.domain.Conta.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CadastroContaDTO(
        @NotBlank(message = "O nome da conta é obrigatório")
        String nome,

        BigDecimal saldo
) {
}