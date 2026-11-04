package com.valora.api.domain.Caixinha.dto;

import java.math.BigDecimal;

public record CaixinhaDTO(
        Long idUsuario,
        String nmCaixinha,
        BigDecimal vlMeta,
        BigDecimal vlAtual
) {}