package com.avanes.backend.config;

import com.avanes.backend.dtos.GlobalResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle EntityNotFoundException (404 Not Found)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<GlobalResponse<String>> handleEntityNotFoundException(EntityNotFoundException ex) {
        GlobalResponse<String> response = new GlobalResponse<>(false, "Resource not found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Handle IllegalArgumentException (400 Bad Request)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GlobalResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        GlobalResponse<String> response = new GlobalResponse<>(false, "Invalid request", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handle validation errors (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<String>> handleValidationException(MethodArgumentNotValidException ex) {
        GlobalResponse<String> response = new GlobalResponse<>(false, "Validation failed", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handle all other unhandled exceptions (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<String>> handleGeneralException(Exception ex) {
        GlobalResponse<String> response = new GlobalResponse<>(false, "Internal Server Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
