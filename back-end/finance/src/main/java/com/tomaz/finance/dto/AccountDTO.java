package com.tomaz.finance.dto;

import java.math.BigDecimal;

public class AccountDTO {
	
	private String name;
	private BigDecimal balance;
	
	public AccountDTO() {
		
	}
	
	public AccountDTO(String name, BigDecimal balance) {
		super();
		this.name = name;
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
