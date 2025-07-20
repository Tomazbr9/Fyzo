package com.fyzo.app.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email; 

public record UserUpdateDTO(
		
	@Column(unique = true)
    String username,

    @Email(message = "Informe um e-mail válido")
    String email,
    
    String password
    
    
) {}
