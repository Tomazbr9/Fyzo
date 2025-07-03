package com.tomaz.finance.dto;

import java.math.BigDecimal;

public class BalanceDTO {

	private BigDecimal totalRevenue;
	private BigDecimal totalExpense;
	private BigDecimal balance;
	
	public BalanceDTO(BigDecimal totalRevenue, BigDecimal totalExpense) {
	
		this.totalRevenue = totalRevenue;
		this.totalExpense = totalExpense;
		this.balance = totalRevenue.subtract(totalExpense);
	}

	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}


	public BigDecimal getTotalExpense() {
		return totalExpense;
	}

	public BigDecimal getBalance() {
		return balance;
	}
	
}
