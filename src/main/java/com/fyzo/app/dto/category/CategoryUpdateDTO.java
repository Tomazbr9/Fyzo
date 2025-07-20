package com.fyzo.app.dto.category;

import com.fyzo.app.enums.TransactionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CategoryUpdateDTO(

    @NotBlank(message = "O nome da categoria é obrigatório")
    String name,

    @NotNull(message = "O tipo da categoria é obrigatório")
    TransactionType type,

    @Pattern(regexp = "^#[A-Fa-f0-9]{6}$", message = "A cor deve estar no formato hexadecimal (ex: #FF5733)")
    String color

) {}
