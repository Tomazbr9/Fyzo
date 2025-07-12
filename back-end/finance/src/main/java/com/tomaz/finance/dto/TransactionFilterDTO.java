package com.tomaz.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.tomaz.finance.enums.TransactionType;

public record TransactionFilterDTO(
    BigDecimal maxAmount,
    BigDecimal minAmount,
    Integer categoryId,
    Integer accountId,
    TransactionType type,
    LocalDate startDate,
    LocalDate endDate,
    Integer page,
    Integer size
) {
    public TransactionFilterDTO {
        if (page == null) page = 0;
        if (size == null) size = 10;
    }
}
