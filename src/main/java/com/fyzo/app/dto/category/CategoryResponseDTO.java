package com.fyzo.app.dto.category;

import com.fyzo.app.enums.TransactionType;

public record CategoryResponseDTO(
    Long id,
    String name,
    String icon,
    TransactionType type,
    String color
) {}
