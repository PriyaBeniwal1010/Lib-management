package com.example.housing.model;

import com.example.housing.exception.BookNotBorrowedException;
import com.example.housing.model.Validation.NotNull;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Member implements Serializable {
    @NotNull(message = "MemberID is a must")
    private int memberID;
    private String name;
    private String password;
    private HashSet<Book> borrowedBooks;// List of books the member has borrowed

    // Constructor to initialize member attributes
    public Member(int memberID, String name, String password, HashSet<Book> borrowedBooks) {
        this.memberID = memberID;
        this.name = name;
        this.password = password;
        this.borrowedBooks = new HashSet<>();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashSet<Book> getBorrowedBooks() {
        return borrowedBooks;
    }
    // Authenticate the member by matching ID and password
    public boolean authenticate(String enteredPassword) {
        return this.password.equals(enteredPassword);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return memberID == member.memberID;  // Compare by id
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberID);  // Use id to calculate hash code
    }

    public String borrowBook(Book book, int qty) {
        borrowedBooks.add(book);
        book.setIssuedQuantity(book.getIssuedQuantity() + qty);
        return book.getBookName()+"issued Successfully";
    }

    public String returnBook(Book book, int qty) {
        borrowedBooks.remove(book);
        book.setIssuedQuantity(book.getIssuedQuantity() - qty);
        return book.getBookName()+"returned Successfully";
    }
}
