package com.tomaz.finance.dto;


public class CategoryDTO {
	private Long id;
	private String name;
	private Integer type;
	private String color;
	
	public CategoryDTO() {
		
	}

	public CategoryDTO(Long id, String name, Integer type, String color) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.color = color;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
