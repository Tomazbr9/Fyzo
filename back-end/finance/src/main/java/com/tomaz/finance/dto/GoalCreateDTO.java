package com.tomaz.finance.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GoalCreateDTO {

	private String name;
	private BigDecimal targetAmount;
	private LocalDate targetDate;
	
	public GoalCreateDTO() {
		
	}

	public GoalCreateDTO(String name, BigDecimal targetAmount, BigDecimal savedAmount, LocalDate targetDate,
			boolean completed) {
		super();
		this.name = name;
		this.targetAmount = targetAmount;
		this.targetDate = targetDate;
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

	public LocalDate getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(LocalDate targetDate) {
		this.targetDate = targetDate;
	}

	
	
}
