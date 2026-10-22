package com.valora.api.dto;

import java.math.BigDecimal;

public record CaixinhaDTO(
        Long idCaixinha,
        Long idUsuario,
        String nmCaixinha,
        BigDecimal vlMeta,
        BigDecimal vlAtual
) {}