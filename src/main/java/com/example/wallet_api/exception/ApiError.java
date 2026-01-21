package com.example.wallet_api.exception;

// Простой формат ошибки для API
public class ApiError {

    private final String message;

    public ApiError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}