package com.valora.api.domain.Caixinha.dto;

import java.math.BigDecimal;

public record CaixinhaDTO(
        Long idCaixinha,
        Long idUsuario,
        String nmCaixinha,
        BigDecimal vlMeta,
        BigDecimal vlAtual
) {}