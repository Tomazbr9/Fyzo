package com.tomaz.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GoalUpdateDTO {
	
	private String name;
	private BigDecimal targetAmount;
	private BigDecimal savedAmount;
	private LocalDate targetDate;
	
	public GoalUpdateDTO() {
		
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
	
	
}
