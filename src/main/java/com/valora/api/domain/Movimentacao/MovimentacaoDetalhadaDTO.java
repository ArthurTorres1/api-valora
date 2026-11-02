package com.valora.api.domain.Movimentacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MovimentacaoDetalhadaDTO(
        Long id,
        String descricao,
        BigDecimal valor,
        TipoMovimentacao tipo,
        LocalDate dataMovimentacao,
        Boolean inseridaManual,
        String nmCategoria,
        String conta
) {
    public MovimentacaoDetalhadaDTO(MovimentacaoModel model) {
        this(
                model.getId(),
                model.getDescricao(),
                model.getValor(),
                model.getTipo(),
                model.getDataMovimentacao(),
                model.getInseridaManual(),
                model.getCategoria() != null ? model.getCategoria().getNmCategoria() : null,
                model.getContaOrigem() != null ? model.getContaOrigem().getNome() : null
        );
    }
}
