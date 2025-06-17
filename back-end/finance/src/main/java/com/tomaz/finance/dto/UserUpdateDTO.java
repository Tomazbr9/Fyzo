package com.tomaz.finance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserUpdateDTO {
    
	@NotBlank(message = "O nome de usuário é obrigatório")
	private String username;
	
	@NotBlank(message = "O e-mail é obrigatório")
	@Email(message = "Informe um e-mail válido")
    private String email;
	
	public UserUpdateDTO() {
		
	}
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
}
