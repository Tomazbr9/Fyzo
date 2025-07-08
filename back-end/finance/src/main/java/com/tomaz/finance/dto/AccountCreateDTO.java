package com.tomaz.finance.dto;

import jakarta.validation.constraints.NotBlank;

public class AccountCreateDTO {
	
	@NotBlank(message = "Nome da conta é obrigatório")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
