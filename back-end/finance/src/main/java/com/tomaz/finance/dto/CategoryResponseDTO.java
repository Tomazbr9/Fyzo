package com.tomaz.finance.dto;

import com.tomaz.finance.enums.TransactionType;

public record CategoryResponseDTO(
    Long id,
    String name,
    TransactionType type,
    String color
) {}
