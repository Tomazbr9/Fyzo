package com.fyzo.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponseDTO (
		String title,
		String description,
	    BigDecimal amount,
	    LocalDate date
) {}
