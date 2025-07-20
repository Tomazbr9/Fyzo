package com.fyzo.app.dto;

import com.fyzo.app.enums.TransactionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CategoryCreateDTO(
    @NotBlank(message = "O nome da categoria é obrigatório")
    String name,

    @NotNull(message = "Por favor, escolha o tipo da categoria")
    TransactionType type,

    @NotBlank(message = "É obrigatório a escolha de uma cor para a categoria")
    @Pattern(regexp = "^#[A-Fa-f0-9]{6}$", message = "A cor deve estar no formato hexadecimal (ex: #FF5733)")
    String color
) {}
