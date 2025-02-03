package com.example.housing.service;

import com.example.housing.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookLibraryService {
    private HashSet<Book> bookStock;


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

    public List<Book> sortBooksByTitle() {
        return bookStock.stream()
                .sorted(Comparator.comparing(Book::getBookName)) // Assuming getBookName() returns the title of the book
                .collect(Collectors.toList());
    }


    // Filter books by availability (e.g., books that have available quantity > 0)
    public List<Book> filterAvailableBooks() {
        return bookStock.stream()
                .filter(book -> book.getAvailableQuantity() > 0) // Assuming getAvailableQuantity() returns the available quantity
                .collect(Collectors.toList());
    }

    // Filter books by type (e.g., EBooks)
    public List<Book> filterEBooks() {
        return bookStock.stream()
                .filter(book -> book instanceof EBook) // Filters only EBook type
                .collect(Collectors.toList());
    }


}

