package com.example.housing.exception;

public class LimitExceedException extends RuntimeException {

public LimitExceedException(String message) {
             super(message);
       }
}
