package com.example.housing.exception;
//Book not borrowed exception will throw an error if the member is trying to return a book the member never borrowed
public class BookNotBorrowedException extends RuntimeException {
    public BookNotBorrowedException(String msg) {
        super(msg);
    }
}
