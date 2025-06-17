package com.tomaz.finance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryCreateDTO {
    
	@NotBlank(message = "O nome da categoria é obrigatório")
	private String name;
	
	@NotNull(message = "Por favor, escolha o tipo da categoria")
    private Integer type;
	
	@NotBlank(message = "É obrigatório a escolha de uma cor para a categoria")
    private String color;
	
	@NotNull(message = "O id do usuário é obrigatório")
    private Long userId;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
