package com.fyzo.app.dto.exception;

import java.time.LocalDateTime;

public record ErrorResponseDTO(int status, String message,  String path, LocalDateTime timestamp) {
}

