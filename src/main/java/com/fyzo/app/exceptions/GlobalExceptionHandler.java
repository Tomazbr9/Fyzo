package com.fyzo.app.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fyzo.app.dto.exception.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(ResourceNotFoundException exeption, HttpServletRequest request) {
        return buildErrorResponse(exeption.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(UsernameNotFoundPersonException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNameNotFound(UsernameNotFoundPersonException exeption, HttpServletRequest request) {
        return buildErrorResponse(exeption.getMessage(), request.getRequestURI(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UnauthorizedResourceAccessException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorized(UnauthorizedResourceAccessException exeption, HttpServletRequest request) {
        return buildErrorResponse(exeption.getMessage(), request.getRequestURI(), HttpStatus.FORBIDDEN);
    }
    
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUsernameAlreadyExists(UsernameAlreadyExistsException exception, HttpServletRequest request){
    	Map<String, String> messageError = new HashMap<>(Map.of("username", exception.getMessage())); 
        return ResponseEntity.badRequest().body(messageError);
    }
    
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(EmailAlreadyExistsException exception, HttpServletRequest request){
    	Map<String, String> messageError = new HashMap<>(Map.of("email", exception.getMessage())); 
        return ResponseEntity.badRequest().body(messageError);
    }
    
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidCredentials(InvalidCredentialsException exeption, HttpServletRequest request) {
        return buildErrorResponse(exeption.getMessage(), request.getRequestURI(), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(TokenMissingException.class)
    public ResponseEntity<ErrorResponseDTO> handleTokenMissing(TokenMissingException exeption, HttpServletRequest request) {
        return buildErrorResponse(exeption.getMessage(), request.getRequestURI(), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidToken(InvalidTokenException exeption, HttpServletRequest request) {
        return buildErrorResponse(exeption.getMessage(),request.getRequestURI(), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<ErrorResponseDTO> handleTokenGeneration(TokenGenerationException exeption, HttpServletRequest request) {
        return buildErrorResponse(exeption.getMessage(), request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception exeption, HttpServletRequest request) {
        return buildErrorResponse("Erro interno no servidor", request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(String message, String request, HttpStatus status) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            status.value(),
            message,
            request,
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }

}
