package com.example.housing.exception;

public class MemberNotExists extends RuntimeException {
    public MemberNotExists(String message) {
        super(message);
    }
}
