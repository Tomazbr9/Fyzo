package com.fyzo.app.dto;

import java.math.BigDecimal;

public record CategorySummaryDTO(
    Long id,
    String categoryName,
    BigDecimal total
) {}
