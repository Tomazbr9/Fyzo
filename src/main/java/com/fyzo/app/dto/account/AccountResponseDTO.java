package com.fyzo.app.dto.account;

import java.math.BigDecimal;

public record AccountResponseDTO(
		String name, 
		BigDecimal balance

) {}
