package com.fyzo.app.dto.goal;

import java.math.BigDecimal;
import java.time.LocalDate;

public record GoalResponseDTO(
    Long id,
    String name,
    BigDecimal targetAmount,
    BigDecimal savedAmount,
    LocalDate targetDate,
    boolean completed
) {}
