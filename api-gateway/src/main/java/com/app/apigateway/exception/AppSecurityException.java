package com.app.apigateway.exception;

public class AppSecurityException extends RuntimeException {

    public AppSecurityException(String message) {
        super(message);
    }
}
