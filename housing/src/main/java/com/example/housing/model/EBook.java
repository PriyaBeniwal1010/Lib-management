package com.example.housing.model;

import java.util.Date;

public class EBook extends Book {
    private String downloadLink;
    private boolean  isDRMProtected;

    // Constructor for eBook-specific attributes
    public EBook(int bookID, String bookName, String author, String ISBN, String publisher, String year, int rating, int price, int totalQuantity, int issuedQuantity, String downloadLink, boolean isDRMProtected, Date issuedOn, Date returnedOn) {
        super(bookID, bookName, author, ISBN, publisher, year, rating, price, totalQuantity, issuedQuantity, issuedOn, returnedOn);
        this.downloadLink = downloadLink;
        this.isDRMProtected = false;
    }



    @Override
    public String getBookInfo() {
        return "eBook Information:\n" +
                "Book Name: " + getBookName() + "\n" +
                "Author: " + getAuthor() + "\n" +
                "Download Link: " + downloadLink + "\n"+
                "isDRMProtected: " + isDRMProtected + "\n";
    }


    @Override
    public String borrowBook(int qty) {
        if (availableBooks() > 0) {
            // Digital books are borrowed instantly, so no actual physical copying
            return "eBook downloaded successfully.";
        } else {
            return "Sorry, this eBook is currently unavailable.";
        }
    }

}
