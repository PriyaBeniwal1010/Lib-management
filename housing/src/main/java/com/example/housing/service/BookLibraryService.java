package com.example.housing.service;

import com.example.housing.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookLibraryService {
    HashSet<Book> bookStock;

    @Autowired
    public BookLibraryService() {
        bookStock = new HashSet<>();
    }

    public String addBook(Book book) {
        bookStock.add(book);
        return book.toString()+"Added Successfully to the Library";
    }

    public String getBookDetails(int bookId) {
        for(Book book : bookStock) {
            if(book.getBookID() == bookId) {
                return book.toString();
            }
        }

        return "Book Not Found";
    }

    public void updateBookDetails(int bookId, String bookName, String ISBN, int totalqty, int issuqQty, boolean isDRMProtected, String downloadLink, HashSet<String> bookStore) {
        for(Book book:bookStock) {
            if(book.getBookID() == bookId) {
                bookStock.remove(book);

                if (book instanceof PrintBook) {
                    // Update the PrintBook
                    PrintBook updatedBook = new PrintBook(bookId, bookName, ISBN, totalqty, issuqQty, bookStore);
                    bookStock.add(updatedBook);
                } else if (book instanceof EBook) {
                    // Update the EBook
                    EBook updatedBook = new EBook(bookId, bookName, ISBN, downloadLink, isDRMProtected);
                    bookStock.add(updatedBook);
                }

            }
            System.out.println("Book Details Updated Successfully");
        }
        System.out.println("Book Details Updation Failed");

    }

    public String deleteBook(int bookId) {
        bookStock.remove(bookId);
        return "Book Removed Successfully";
    }

    public List<Book> getBookStock() {
        return new ArrayList<>(bookStock);
    }
}

