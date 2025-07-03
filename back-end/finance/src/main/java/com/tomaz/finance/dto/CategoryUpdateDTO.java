package com.tomaz.finance.dto;

import jakarta.validation.constraints.Pattern;

public class CategoryUpdateDTO {
    
	
	private String name;
    private Integer type;
    
    @Pattern(regexp = "^#[A-Fa-f0-9]{6}$", message = "A cor deve estar no formato hexadecimal (ex: #FF5733)")
    private String color;
    
    public CategoryUpdateDTO() {
    	
    }
    
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
    
    
	
}
