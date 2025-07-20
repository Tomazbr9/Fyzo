package com.fyzo.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalUpdateDTO(
    String name,
    BigDecimal targetAmount,
    BigDecimal savedAmount,
    LocalDate targetDate
) {}
