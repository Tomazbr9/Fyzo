package com.fyzo.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalCreateDTO(
    String name,
    BigDecimal targetAmount,
    LocalDate targetDate
) {}
