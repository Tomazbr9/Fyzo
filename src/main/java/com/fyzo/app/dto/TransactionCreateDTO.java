package com.fyzo.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fyzo.app.enums.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransactionCreateDTO(
    
    @NotBlank(message = "Digite um título para essa transação")
    String title,
    
    String description,
    
    @NotNull(message = "O valor é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    BigDecimal amount,
    
    LocalDate date,
    
    TransactionType type,
    
    @NotNull(message = "O id da categoria é obrigatório")
    Long categoryId,
    
    @NotNull(message = "Obrigatório selecionar uma conta")
    Long accountId

) {}

