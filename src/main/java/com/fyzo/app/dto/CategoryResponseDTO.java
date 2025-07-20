package com.fyzo.app.dto;

import com.fyzo.app.enums.TransactionType;

public record CategoryResponseDTO(
    Long id,
    String name,
    TransactionType type,
    String color
) {}
