package com.example.housing.controller;

import ch.qos.logback.core.model.Model;
import com.example.housing.exception.BookNotBorrowedException;
import com.example.housing.exception.BookNotFoundException;
import com.example.housing.model.*;
import com.example.housing.model.Library;
import com.example.housing.service.BookLibraryService;
import com.example.housing.service.MemberLibraryService;
import com.example.housing.service.MemberRequestBook;
import com.example.housing.view.LibraryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCheckpointRestore;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController

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

    @PostMapping("/Books")
    public String addBook(@RequestBody Book book){
        // Calling the BookLibraryService method to add the book
        return bookLibraryService.addBook( book);
    }

    // This can be used to get details of a specific book by bookId
    @GetMapping("/books/{ID}")
    public String getBookDetails(@PathVariable("ID") int bookId) {
       return bookLibraryService.getBookDetails(bookId);

    }

    @PutMapping("/Books/{ID}")
    public void updateBookDetails(@PathVariable("ID") int bookId, @RequestBody String bookName) {
        bookLibraryService.updateBookDetails(bookId, bookName);
        System.out.print("Book updated successfully");
    }


    // This can be used to delete a book from the library by bookId
    @DeleteMapping("/Books/{ID}")
    public String deleteBook(@PathVariable("ID")int bookId) {
        try {
            return bookLibraryService.deleteBook(bookId);
        }catch (BookNotFoundException e) {
            return e.getMessage();
        }

    }

    // Fetch all books in the library
    @GetMapping("/Books")
    public List<Book> getAllBooks() {
//        getAllBooksHashSet<String> locations = new HashSet<>(Arrays.asList("hr", "delhi", "mumbai"));
//        Book book1=new PrintBook(78, "Hello", "9076554332", 90, 87, locations);
//        return List.of(book1);
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

//    @RequestMapping(value = "/greet", method=RequestMethod.GET)
//    public String hello(Model model) {
//      //  model.addAttribute( "Hello World, welcome to my library!");
//        return "hello welcome to the library";
//    }





}
