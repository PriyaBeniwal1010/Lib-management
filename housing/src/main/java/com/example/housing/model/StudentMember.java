package com.example.housing.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class StudentMember extends Member {
    private static final int MAX_BOOKS_ALLOWED = 5;

    public StudentMember(int memberID, String name, String email, String password,
                         ArrayList<Book> borrowedBooks) {
        // Calling the superclass constructor with 'super'
        super(memberID, name, email, password, borrowedBooks);
    }

    @Override
    public String borrowBook(Book book, int qty) {
        if (qty > MAX_BOOKS_ALLOWED) {
            return "You can borrow up to " + MAX_BOOKS_ALLOWED + " books only.";
        } else {
            return super.borrowBook(book, qty);
        }
    }


}

