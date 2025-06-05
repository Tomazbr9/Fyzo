package com.tomaz.finance.enums;

public enum TransactionType {
    EXPENSE(1),
    REVENUE(2);
    
    private Integer code;
	
	private TransactionType(Integer code) {
		this.code = code;
	}
	
	public Integer getCode() {
		return this.code;
	}
	
	public static TransactionType valueOf(Integer code) {
		for(TransactionType value : TransactionType.values()) {
			if(value.getCode() == code) {
				return value;
			}
		}
		throw new IllegalArgumentException("Invalid TransactionType code");
	}
}
