package com.valora.api.domain.Conta;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ContaDetalhadaDTO(
        Long id,
        String nome,
        BigDecimal saldo,
        LocalDateTime dataCriacao,
        Long idUsuario
) {
    public ContaDetalhadaDTO(ContaModel conta) {
        this(
                conta.getId(),
                conta.getNome(),
                conta.getSaldo(),
                conta.getDataCriacao(),
                conta.getUsuario().getId()
        );
    }
}