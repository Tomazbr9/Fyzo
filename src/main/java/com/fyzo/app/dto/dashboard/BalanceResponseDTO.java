package com.fyzo.app.dto.dashboard;

import java.math.BigDecimal;

public record BalanceResponseDTO(BigDecimal totalRevenue, BigDecimal totalExpense, BigDecimal balance) {
    
    public BalanceResponseDTO(BigDecimal totalRevenue, BigDecimal totalExpense) {
        this(totalRevenue, totalExpense,
             (totalRevenue == null || totalExpense == null)
                 ? BigDecimal.ZERO
                 : totalRevenue.subtract(totalExpense));
    }
}

