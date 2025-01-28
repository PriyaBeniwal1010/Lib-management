package com.example.housing.model;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Member {
    private int memberID;
    private String name;
    private String email;
    private String password;
    private ArrayList<Book> borrowedBooks;// List of books the member has borrowed

    // Constructor to initialize member attributes
    public Member(int memberID, String name, String email, String password, ArrayList<Book> borrowedBooks) {
        this.memberID = memberID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getter and setter methods
    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Authenticate the member by matching ID and password
    public boolean authenticate(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

    // Borrow a book if it's available
    public String borrowBook(Book book, int qty) {
        // Check if book is available to borrow
        String borrowMessage = book.borrowBook(qty);
        if (borrowMessage.equals("Book Issued Successfully")) {
            for (int i = 0; i < qty; i++) {
                borrowedBooks.add(book);  // Add the borrowed book to the member's list
            }
            return borrowMessage;
        } else {
            return borrowMessage;  // If borrowing fails, return the error message
        }
    }

    // Return a borrowed book
    public String returnBook(Book book, int qty) {
        int booksReturned = 0;
        for (int i = 0; i < borrowedBooks.size(); i++) {
            if (borrowedBooks.get(i).getBookID() == book.getBookID()) {
                borrowedBooks.remove(i);  // Remove book from borrowed list
                booksReturned++;
                if (booksReturned == qty) {
                    break;
                }
            }
        }
        if (booksReturned == qty) {
            return "Book(s) returned successfully.";
        } else {
            return "Error: You are trying to return more books than borrowed.";
        }
    }

    // View all borrowed books
    public String viewBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            return "You have not borrowed any books.";
        } else {
            StringBuilder borrowedBooksList = new StringBuilder("Borrowed Books:\n");
            for (Book book : borrowedBooks) {
                borrowedBooksList.append(book.getBookName()).append("\n");
            }
            return borrowedBooksList.toString();
        }
    }

}
