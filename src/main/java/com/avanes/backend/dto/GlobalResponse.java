package com.avanes.backend.dto;

import lombok.Data;

@Data
public class GlobalResponse<T> {
    private boolean isSuccess;
    private String message;
    private T data;

    public GlobalResponse(boolean isSuccess, String message, T data) {
        this.isSuccess = isSuccess;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}

