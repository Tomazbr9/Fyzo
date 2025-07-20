package com.fyzo.app.dto;

import java.math.BigDecimal;

public record BalanceResponseDTO(BigDecimal totalRevenue, BigDecimal totalExpense) {
    
    public BigDecimal balance() {
        if (totalRevenue == null || totalExpense == null) {
            return BigDecimal.ZERO;
        }
        return totalRevenue.subtract(totalExpense);
    }
}
