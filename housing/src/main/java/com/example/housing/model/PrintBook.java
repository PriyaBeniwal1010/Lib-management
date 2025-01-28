package com.example.housing.model;

import java.util.Date;
import java.util.List;

public class PrintBook extends Book {
    private List<String> availableStores; // Locations where the physical book is available

    // Constructor for PrintBook-specific attributes
    public PrintBook(int bookID, String bookName, String author, String ISBN, String publisher, String year, int rating, int price, int totalQuantity, int issuedQuantity, List<String> availableStores, Date issued_date, Date returned_date) {
        super(bookID, bookName, author, ISBN, publisher, year, rating, price, totalQuantity, issuedQuantity, issued_date, returned_date);
        this.availableStores = availableStores;
    }


    @Override
    public String getBookInfo() {
        return "PrintBook Information:\n" +
                "Book Name: " + getBookName() + "\n" +
                "Author: " + getAuthor() + "\n" +
                "Available at stores: " + availableStores;
    }


    @Override
    public String borrowBook(int qty) {
        if (availableBooks() > 0) {
            // In case of print books, they are "borrowed" from a physical store
            return "PrintBook borrowed successfully from store.";
        } else {
            return "Sorry, this PrintBook is currently unavailable.";
        }
    }

    public void addAvailableQuantity(int qty) {
             setIssuedQuantity(getIssuedQuantity() -qty);
    }
}
