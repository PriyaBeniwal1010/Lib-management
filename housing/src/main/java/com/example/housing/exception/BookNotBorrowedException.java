package com.example.housing.exception;

public class BookNotBorrowedException extends RuntimeException {
    public BookNotBorrowedException(String msg) {
        super(msg);
    }
}
