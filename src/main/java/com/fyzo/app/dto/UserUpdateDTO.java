package com.fyzo.app.dto;

import jakarta.validation.constraints.Email; 

public record UserUpdateDTO(
    String username,

    @Email(message = "Informe um e-mail válido")
    String email,
    
    String password
    
    
) {}
