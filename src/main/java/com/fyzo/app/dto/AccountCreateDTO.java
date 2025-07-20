package com.fyzo.app.dto;

import jakarta.validation.constraints.NotBlank;

public record AccountCreateDTO(
    @NotBlank(message = "Nome da conta é obrigatório")
    String name
) {}
