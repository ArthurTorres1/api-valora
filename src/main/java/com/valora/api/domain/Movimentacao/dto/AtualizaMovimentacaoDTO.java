package com.valora.api.domain.Movimentacao.dto;

import com.valora.api.domain.Movimentacao.TipoMovimentacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AtualizaMovimentacaoDTO(
        String descricao,
        BigDecimal valor,
        TipoMovimentacao tipo,
        LocalDate dataMovimentacao,
        Long idConta,
        Long idContaDestino,
        Long idCaixinha,
        Long idCategoria
) {
}
