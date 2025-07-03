package com.tomaz.finance.dto;

import java.math.BigDecimal;

public class CategorySummaryDTO {
	
	private Long id;
	private String categoryName;
	private BigDecimal total;
	
	public CategorySummaryDTO(Long id, String categoryName, BigDecimal total) {
		
		this.id = id;
		this.categoryName = categoryName;
		this.total = total;
	}

	public Long getId() {
		return id;
	}


	public String getCategoryName() {
		return categoryName;
	}


	public BigDecimal getTotal() {
		return total;
	}
}
	

