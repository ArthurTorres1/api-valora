package com.valora.api.domain.Movimentacao.dto;

import com.valora.api.domain.Movimentacao.TipoMovimentacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CadastroMovimentacaoDTO(
        @NotBlank String descricao,
        @NotNull @Positive BigDecimal valor,
        @NotNull TipoMovimentacao tipo, // RECEITA, DESPESA, TRANSFERENCIA
        @NotNull LocalDate dataMovimentacao,
        @NotNull Long idCategoria,

        // Se for Despesa/Receita: É a conta afetada.
        // Se for Transferência: É a conta de ORIGEM (de onde o dinheiro sai).
        Long idConta,

        Long idContaDestino,

        // Só preenchido se for guardar dinheiro.
        // Se for Transferência: É a caixinha de DESTINO (para onde o dinheiro vai).
        Long idCaixinha
) {}
