package com.fyzo.app.dto.account;

import jakarta.validation.constraints.NotBlank;

public record AccountRequestDTO(
    @NotBlank(message = "Nome da conta é obrigatório")
    String name
) {}
