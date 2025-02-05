package com.example.housing.model;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class PrintBook extends Book {
    private HashSet<String> availableStores; // Locations where the physical book is available

    // Constructor for PrintBook-specific attributes
    public PrintBook(int bookID, String bookName, String ISBN, int totalQuantity, int issuedQuantity, HashSet<String> availableStores) {
        super(bookID, bookName, ISBN,  totalQuantity, issuedQuantity);
        this.availableStores = new LinkedHashSet<>(availableStores.size());
    }


    @Override
    public String getBookInfo() {
        return "PrintBook Information:\n" +
                "Book Name: " + getBookName() + "\n" +
                "Book isbn"+getISBN()+ "\n"+
                "Book Total Quantity: " + getTotalQuantity() + "\n"+
                "Book Issue Quantity: " + getIssuedQuantity() + "\n"+
                "Book Available Stores: " + availableStores;
    }

    public void addAvailableQuantity(int qty) {
             setIssuedQuantity(getIssuedQuantity() -qty);
    }

    public HashSet<String> getBookStore() {
        return availableStores;
    }
}
