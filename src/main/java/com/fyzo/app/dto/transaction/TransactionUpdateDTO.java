package com.fyzo.app.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fyzo.app.enums.TransactionType;

import jakarta.validation.constraints.DecimalMin;

public record TransactionUpdateDTO(

    String title,
    String description,

    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    BigDecimal amount,

    LocalDate date,
    TransactionType type,
    Long categoryId,
    Long accountId

) {}
