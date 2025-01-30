package com.example.housing.service;

import com.example.housing.exception.BookNotFoundException;
import com.example.housing.exception.LimitExceedException;
import com.example.housing.exception.OverdueException;
import com.example.housing.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.*;

public class LibraryService {
    private ArrayList<Book> books;
    private Queue<Member> requestQueue;

    public LibraryService() {
        books = new ArrayList<>();
        requestQueue = new LinkedList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addRequest(Member member) {
        requestQueue.offer(member);
    }

    public void processRequests(Book book, int qty) {
        while (!requestQueue.isEmpty()) {
            Member member = requestQueue.poll();
            member.borrowBook(book, qty);
        }
    }

    public Book issueBook() {
        // Find the first available book
        try {
            for (Book book : books) {
                if (book.getAvailableQuantity() > 1) {
                    book.setIssuedQuantity(book.getAvailableQuantity() - 1);
                    System.out.println("Issue book: " + book.getISBN());
                    return book;  // Issue the book
                }
            }
            return null;  // No books available
        }catch (BookNotFoundException e) {
            throw new BookNotFoundException("Book not found");
        }catch(LimitExceedException f){
            throw new LimitExceedException("Limit exceeded");
        }
    }

    public void sortBooksByISBN() {
        books.sort(Comparator.comparing(Book::getISBN));
    }

    // Sort members by ID
    public void sortMembersByID(List<Member> members) {
        members.sort(Comparator.comparingInt(Member::getMemberID));
    }

    // Filter available books using Streams
    public List<Book> filterAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailableQty)
                .collect(Collectors.toList());
    }

    // Filter members with overdue books using Streams
    public List<Member> filterOverdueMembers(List<Member> members) {
        try {
            Date currentDate = new Date();  // Current date as Date

            return members.stream()
                    .filter(member -> member.viewBorrowedBooks() != null && !member.viewBorrowedBooks().isEmpty() &&
                            Arrays.stream(member.viewBorrowedBooks().split(","))
                                    .anyMatch(bookTitle -> issueBook().isBookOverdue(member)))  // Assuming you have a method to check if a book is overdue
                    .collect(Collectors.toList());
        }catch (OverdueException e) {
            throw new OverdueException("Overdue Exception");
        }

    }

    //        public void displayBooks (Book books) {
//            for (Book book : books) {
//                System.out.println(book);
//            }
//        }
}

