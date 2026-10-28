package com.valora.api.domain.Conta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CadastroContaDTO(
        @NotBlank(message = "O nome da conta é obrigatório")
        String nome,

        BigDecimal saldo,

        @NotNull(message = "O ID do usuário é obrigatório")
        Long idUsuario
) {
}