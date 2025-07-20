package com.fyzo.app.dto;

import java.math.BigDecimal;

public record AccountResponseDTO(
		String name, 
		BigDecimal balance

) {}
