package com.tomaz.finance.dto;

import jakarta.validation.constraints.Email; 

public class UserUpdateDTO {
    
	private String username;
	
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
