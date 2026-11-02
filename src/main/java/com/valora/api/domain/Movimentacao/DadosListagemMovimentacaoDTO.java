package com.valora.api.domain.Movimentacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosListagemMovimentacaoDTO(
        Long id,
        String descricao,
        BigDecimal valor,
        TipoMovimentacao tipo,
        LocalDate dataMovimentacao,
        String categoria,
        String conta
) {
    public DadosListagemMovimentacaoDTO(MovimentacaoModel model) {
        this(
                model.getId(),
                model.getDescricao(),
                model.getValor(),
                model.getTipo(),
                model.getDataMovimentacao(),
                model.getCategoria().getNmCategoria(),
                model.getContaOrigem().getNome()
        );
    }
}
