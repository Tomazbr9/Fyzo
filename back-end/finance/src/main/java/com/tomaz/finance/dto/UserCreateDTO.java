package com.tomaz.finance.dto;

import java.util.List; 

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserCreateDTO {
	
	@NotBlank(message = "O nome de usuário é obrigatório")
	private String username;
	
	@NotBlank(message = "O email é obrigatório")
	@Email(message = "Informe um email válido")
    private String email;
	
	@NotBlank(message = "A senha é obrigatória")
    private String password;
	
	private List<String> roles;
    
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}
