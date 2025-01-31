package com.example.housing.model;

import java.util.Objects;

public abstract class Book {
    // Book details
    private int bookID;
    private String bookName;
    private String ISBN;
    private int totalQuantity;  // Total number of books available in the library
    private int issuedQuantity; // Number of books already borrowed;


    // Constructor to initialize the book object with details
    public Book(int bookID, String bookName, String ISBN, int totalQuantity, int issuedQuantity) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.ISBN = ISBN;
        this.totalQuantity = totalQuantity;
        this.issuedQuantity = issuedQuantity;
    }

    public Book(int bookID, String bookName, String isbn) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.ISBN = isbn;
    }

    // Getter and setter methods for each attribute
    public int getBookID() {
        return bookID;
    }
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }
    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public int getTotalQuantity() {
        return totalQuantity;
    }
    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    public int getIssuedQuantity() {
        return issuedQuantity;
    }
    public int setIssuedQuantity(int issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
        return issuedQuantity;
    }

    public abstract String getBookInfo();

    public int getAvailableQuantity() {
        return totalQuantity - issuedQuantity;
    }

    public void addAvailableQuantity(int qty) {
        this.issuedQuantity -= qty;  // Update the issued quantity to reflect the return
    }

    //Checking if we have instances of that book present
    public boolean availableBook(int bookId) {
        return getIssuedQuantity() > 0;// Assuming the book is available
    }
    // Method to get the number of available units (returns an int)
    public int availableBook(int bookId, int ReqQty) {
        if (getIssuedQuantity() >=ReqQty) {
            return getAvailableQuantity();
        }
        return 0;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ISBN.equals(book.ISBN); // Consider ISBN as the unique identifier
    }

    @Override
    public int hashCode() {
        return Objects.hash(ISBN); // Generate hash code based on ISBN
    }



    // Returns detailed info about the book
    public String tostring() {
        return "Book Information:\n" +
                "Book ID: " + getBookID() + "\n" +
                "Book Name: " + getBookName() + "\n" +
                "ISBN: " + getISBN() + "\n" +
                "Total Quantity: " + getTotalQuantity() + "\n" +
                "Available Copies: " + getAvailableQuantity() + "\n";
    }


}