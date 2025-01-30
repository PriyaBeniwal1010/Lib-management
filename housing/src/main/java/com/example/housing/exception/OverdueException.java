package com.example.housing.exception;

public class OverdueException extends RuntimeException {
    public OverdueException(String message) {
        super(message);
    }
}
