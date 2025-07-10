package com.tomaz.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;

public record TransactionUpdateDTO(

    String title,
    String description,

    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    BigDecimal amount,

    LocalDate date,
    Integer type,
    Long categoryId,
    Long accountId

) {}
