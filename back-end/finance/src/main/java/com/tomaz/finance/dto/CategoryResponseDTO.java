package com.tomaz.finance.dto;

public record CategoryResponseDTO(
    Long id,
    String name,
    Integer type,
    String color
) {}
