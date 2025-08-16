package com.fyzo.app.dto.account;

import java.math.BigDecimal;

public record AccountResponseDTO(
		Long id,
		String name, 
		String imageUrl,
		BigDecimal balance

) {}
