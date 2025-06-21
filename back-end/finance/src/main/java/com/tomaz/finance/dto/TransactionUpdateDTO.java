package com.tomaz.finance.dto;

import java.time.LocalDate;

public class TransactionUpdateDTO {
	
	private String title;
	private String description;
	private Double amount;
	private LocalDate date;
	private Integer type;
	private Long categoryId;
	
	public TransactionUpdateDTO() {
		
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategory(Long categoryId) {
		this.categoryId = categoryId;
	}
}
