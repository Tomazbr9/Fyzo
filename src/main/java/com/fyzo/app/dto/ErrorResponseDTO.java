package com.fyzo.app.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(int status, String message, LocalDateTime timestamp) {
}

