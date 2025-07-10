package com.tomaz.finance.dto;

import jakarta.validation.constraints.Email; 

public record UserUpdateDTO(
    String username,

    @Email(message = "Informe um e-mail válido")
    String email
) {}
