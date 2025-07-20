package com.fyzo.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
	    @NotBlank(message = "O nome de usuário é obrigatório")
	    String username,

	    @NotBlank(message = "O email é obrigatório")
	    @Email(message = "Informe um email válido")
	    String email,

	    @NotBlank(message = "A senha é obrigatória")
	    String password,

	    String role
	) {}
