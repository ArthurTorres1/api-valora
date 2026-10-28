package com.valora.api.domain.Conta;

import java.math.BigDecimal;

public record DadosListagemContaDTO(
        Long id,
        String nome,
        BigDecimal saldo
) {
    public DadosListagemContaDTO(ContaModel conta) {
        this(
                conta.getId(),
                conta.getNome(),
                conta.getSaldo()
        );
    }
}