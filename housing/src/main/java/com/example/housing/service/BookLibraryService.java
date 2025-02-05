package com.example.housing.service;

import com.example.housing.exception.BookNotFoundException;
import com.example.housing.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookLibraryService {
    //@Autowired
   private HashSet<Book> bookStock;
   private Library library;

    public BookLibraryService() {

        bookStock = new HashSet<>();

        // Add some books to the bookStock
        bookStock.add(new PrintBook(98, "Welcome to the Disney World", "278339", 98, 23, new HashSet<>(Arrays.asList("Delhi", "Mumbai", "Gujarat", "Goa"))));
        bookStock.add(new EBook(1002, "The Autobiography of XYZ", "209230340", "www.goodreads/theAutobiographyofXYZ.com", true));
    }

    public String addBook(Book book) {
        bookStock.add(book);
        return book.toString();
        //return "\nAdded Successfully to the Library";
    }

    public String getBookDetails(int bookId) {
        for(Book book : bookStock) {
            if(book.getBookID() == bookId) {
                return book.toString();
            }
        }
        return "Book Not Found";
    }

    public void updateBookDetails(int bookId, String bookName) {
        Iterator<Book> iterator = bookStock.iterator();
        boolean isUpdated = false;
        while (iterator.hasNext()) {
            Book book = iterator.next();

            if (book.getBookID() == bookId) {
                book.setBookName(bookName);
                isUpdated = true;
                break;
            }
        }
        if (isUpdated) {
            System.out.println("Book Details Updated Successfully");
        } else {
            System.out.println("Book Details Updation Failed: Book not found");
        }
    }

    public String deleteBook(int bookId) throws BookNotFoundException {
        boolean removed = bookStock.removeIf(book -> book.getBookID() == bookId);
        if (removed) {
            return bookStock.toString();
        } else {
            throw new BookNotFoundException("Book with ID " + bookId + " not found.");
        }
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

