package com.tomaz.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GoalDTO {
	private Long id;
	private String name;
	private BigDecimal targetAmount;
	private BigDecimal savedAmount;
	private LocalDate targetDate;
	private boolean completed;
	
	public GoalDTO() {
		
	}

	public GoalDTO(Long id, String name, BigDecimal targetAmount, BigDecimal savedAmount, LocalDate targetDate,
			boolean completed) {
		super();
		this.id = id;
		this.name = name;
		this.targetAmount = targetAmount;
		this.savedAmount = savedAmount;
		this.targetDate = targetDate;
		this.completed = completed;
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

	public BigDecimal getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(BigDecimal targetAmount) {
		this.targetAmount = targetAmount;
	}

	public BigDecimal getSavedAmount() {
		return savedAmount;
	}

	public void setSavedAmount(BigDecimal savedAmount) {
		this.savedAmount = savedAmount;
	}

	public LocalDate getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(LocalDate targetDate) {
		this.targetDate = targetDate;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	
}
