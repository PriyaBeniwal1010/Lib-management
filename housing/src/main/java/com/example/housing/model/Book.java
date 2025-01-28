package com.example.housing.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public abstract class Book {
    // Book details
    private int bookID;
    private String bookName;
    private String author;
    private String ISBN;
    private String publisher;
    private String year;
    private int rating;
    private int price;
    private int totalQuantity;  // Total number of books available in the library
    private int issuedQuantity; // Number of books already borrowed
    private Date date_of_issue;
    private Date date_of_return;


    // Constructor to initialize the book object with details
    public Book(int bookID, String bookName, String author, String ISBN, String publisher, String year, int rating, int price, int totalQuantity, int issuedQuantity, Date date_of_issue, Date date_of_return) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.ISBN = ISBN;
        this.publisher = publisher;
        this.year = year;
        this.rating = rating;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.issuedQuantity = issuedQuantity;
        this.date_of_issue = date_of_issue;
        this.date_of_return = date_of_return;
    }

    // Getter and setter methods for each attribute
    public Date getDate_of_issue() {
        return date_of_issue;
    }
    public void setDate_of_issue(Date date_of_issue) { this.date_of_issue = date_of_issue; }
    public Date getDate_of_return() {
        return date_of_return; }
    public void setDate_of_return(Date date_of_return) { this.date_of_return = date_of_return; }

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public void setIssuedQuantity(int issuedQuantity) {
        this.issuedQuantity = issuedQuantity;
    }

    public boolean isAvailableQty() {
        return (totalQuantity - issuedQuantity>0)?true:false;
    }

    public int availableBooks() {
        return totalQuantity - issuedQuantity;
    }

    public boolean isBookOverdue(Member member) {
        LocalDate currentDate = LocalDate.now();  // Get current date
        Date returnDate = this.date_of_return;  // Get the return date of the book

        // Convert java.util.Date to LocalDate
        LocalDate returnDateLocal = returnDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Check if the current date is after the return date (i.e., overdue)
        return currentDate.isAfter(returnDateLocal);
    }

    // Returns detailed info about the book
    public String tostring() {
        return "Book Information:\n" +
                "Book ID: " + getBookID() + "\n" +
                "Book Name: " + getBookName() + "\n" +
                "Author: " + getAuthor() + "\n" +
                "ISBN: " + getISBN() + "\n" +
                "Publisher: " + getPublisher() + "\n" +
                "Year: " + getYear() + "\n" +
                "Rating: " + getRating() + "/5\n" +
                "Price: $" + getPrice() + "\n" +
                "Total Quantity: " + getTotalQuantity() + "\n" +
                "Available Copies: " + availableBooks();
    }

    // Calculates the number of available books in the library
    public abstract String getBookInfo();

    // Abstract method for borrowing a book, since different book types may have different ways to borrow
    public abstract String borrowBook(int qty);

    // Returns how many copies are available for borrowing

    public int getAvailableQuantity() {
        return totalQuantity - issuedQuantity;
    }

    public void addAvailableQuantity(int qty) {
        this.issuedQuantity -= qty;  // Update the issued quantity to reflect the return
    }
}