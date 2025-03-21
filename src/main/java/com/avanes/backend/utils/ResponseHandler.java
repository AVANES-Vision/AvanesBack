package com.avanes.backend.utils;

import com.avanes.backend.dtos.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    private ResponseHandler() {}

    public static <T> ResponseEntity<GlobalResponse<T>> success(T data, String message) {
        GlobalResponse<T> response = new GlobalResponse<>(true, message, data);
        return ResponseEntity.ok(response);
    }

    public static <T> ResponseEntity<GlobalResponse<T>> created(T data, String message) {
        GlobalResponse<T> response = new GlobalResponse<>(true, message, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public static <T> ResponseEntity<GlobalResponse<T>> error(String message, HttpStatus status) {
        GlobalResponse<T> response = new GlobalResponse<>(false, message, null);
        return ResponseEntity.status(status).body(response);
    }
}
