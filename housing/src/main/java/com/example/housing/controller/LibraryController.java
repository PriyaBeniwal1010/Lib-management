package com.example.housing.controller;

import com.example.housing.model.*;
import com.example.housing.model.Library;
import com.example.housing.view.LibraryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class LibraryController {
    private Library library;
    private final LibraryView view;

    @Autowired
    public LibraryController(Library library, LibraryView view) {
        this.library = library;
        this.view = view;
    }

    // Add a new book to the library
    public void addBook(Book book) {
        library.addBook(book);
        view.displayMessage("Book added successfully: " + book.getBookName());
    }

    // Remove a book from the library
    public void removeBook(int bookID) {
        String result = library.removeBook(bookID);
        view.displayMessage(result);
    }

    // Add a new member to the library
    public void addMember(Member member) {
        library.addMember(member);
        view.displayMessage("Member added successfully: " + member.getName());
    }

    // Authenticate a member
    public void authenticateMember(int memberID, String password) {
        Member member = library.authenticateMember(memberID, password);
        if (member != null) {
            view.displayMessage("Member authenticated successfully: " + member.getName());
        } else {
            view.displayMessage("Authentication failed.");
        }
    }

    // Borrow a book for a member
    public void borrowBook(int memberID, int bookID, int qty) {
        String result = library.borrowBook(memberID, bookID, qty);
        view.displayMessage(result);
    }

    // Return a book for a member
    public void returnBook(int memberID, int bookID, int qty) {
        String result = library.returnBook(memberID, bookID, qty);
        view.displayMessage(result);
    }

    // List all available books in the library
    public void listBooks() {
        String bookList = library.listBooks();
        view.displayMessage(bookList);
    }

    // View borrowed books for a specific member
    public void viewBorrowedBooks(int memberID) {
        String borrowedBooks = library.viewBorrowedBooks(memberID);
        view.displayMessage(borrowedBooks);
    }



}
