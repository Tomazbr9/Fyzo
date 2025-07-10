package com.tomaz.finance.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
    @NotBlank(message = "O nome de usuário é obrigatório")
    String username,

    @NotBlank(message = "A senha é obrigatória")
    String password
) {}
