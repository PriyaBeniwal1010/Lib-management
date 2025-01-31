package com.example.housing.controller;

import com.example.housing.exception.BookNotBorrowedException;
import com.example.housing.exception.BookNotFoundException;
import com.example.housing.model.*;
import com.example.housing.model.Library;
import com.example.housing.service.BookLibraryService;
import com.example.housing.service.MemberLibraryService;
import com.example.housing.service.MemberRequestBook;
import com.example.housing.view.LibraryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;


@Service
public class LibraryController {
    private final BookLibraryService bookLibraryService;
    private final MemberLibraryService memberLibraryService;
    private final MemberRequestBook memberRequestBook;
    private final LibraryView view;

    @Autowired
    public LibraryController(BookLibraryService bookLibraryService,
                             MemberLibraryService memberLibraryService,
                             MemberRequestBook memberRequestBook,
                             LibraryView view) {

        this.bookLibraryService = bookLibraryService;
        this.memberLibraryService = memberLibraryService;
        this.memberRequestBook = memberRequestBook;
        this.view = view;
    }

    public String addBook(Book book) {
        // Calling the BookLibraryService method to add the book
        return bookLibraryService.addBook(book);
    }

    // This can be used to get details of a specific book by bookId
    public String getBookDetails(int bookId) {
      return bookLibraryService.getBookDetails(bookId);
    }

    public void updateBookDetails(int bookId, String bookName, String ISBN, int totalQty, int issuedQty, boolean isDRMProtected, String downloadLink) {
        HashSet<String> bookStore = new HashSet<>();
        bookLibraryService.updateBookDetails(bookId, bookName, ISBN, totalQty, issuedQty, isDRMProtected, downloadLink, bookStore);
    }

    // This can be used to delete a book from the library by bookId
    public String deleteBook(int bookId) {
        return bookLibraryService.deleteBook(bookId);
    }

    // Fetch all books in the library
    public List<Book> getAllBooks() {
        return bookLibraryService.getBookStock();
    }

    public void addMember(Member member) {
        memberLibraryService.addMember(member);
    }

    // Method to remove a member
    public void removeMember(Member member) {
        memberLibraryService.removeMember(member);
    }

    // Method to update a member's details
    public void updateMemberRecord(int id, String name, String password, HashSet<Book> borrowedBooks) {
        memberLibraryService.updateMemberRecord(id, name, password, borrowedBooks);
    }

    // Method to remove a member by ID
    public String removeMemberRecord(int id) {
        return memberLibraryService.removeMemberRecord(id);
    }

    public void addRequest(Member member, Book book) {
        memberRequestBook.addRequest(member, book);
    }

    // Method to process a book request for a specific member and book
    public void processRequest(Member member, Book book, int qty) {
        memberRequestBook.processRequest(member, book, qty);
    }

    // Method to shut down the executor gracefully
    public void shutdownExecutor() {
        memberRequestBook.shutdown();
    }






}
