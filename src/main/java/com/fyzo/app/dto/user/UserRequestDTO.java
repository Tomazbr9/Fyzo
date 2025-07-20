package com.fyzo.app.dto.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
	    @NotBlank(message = "O nome de usuário é obrigatório")
	    @Column(unique = true)
	    String username,

	    @NotBlank(message = "O email é obrigatório")
	    @Email(message = "Informe um email válido")
	    String email,

	    @NotBlank(message = "A senha é obrigatória")
	    String password,

	    String role
	) {}
