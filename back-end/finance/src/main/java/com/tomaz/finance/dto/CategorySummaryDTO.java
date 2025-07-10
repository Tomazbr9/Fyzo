package com.tomaz.finance.dto;

import java.math.BigDecimal;

public record CategorySummaryDTO(
    Long id,
    String categoryName,
    BigDecimal total
) {}
