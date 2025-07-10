package com.tomaz.finance.dto;

import java.math.BigDecimal;

public record AccountResponseDTO(
		String name, 
		BigDecimal balance

) {}
