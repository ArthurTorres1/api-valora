package com.valora.api.domain.Movimentacao;

import com.valora.api.domain.Conta.ContaModel;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AtualizaMovimentacaoDTO(
        String descricao,
        BigDecimal valor,
        TipoMovimentacao tipo,
        LocalDate dataMovimentacao,
        Long idConta,
        Long idCategoria
) {
}
