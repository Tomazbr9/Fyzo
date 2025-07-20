package com.fyzo.app.dto.goal;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalRequestDTO(
    String name,
    BigDecimal targetAmount,
    LocalDate targetDate
) {}
