package com.eg.HousingLibrary.controller;

import com.eg.HousingLibrary.ConverterUtility.EntityDTOMapper;
import com.eg.HousingLibrary.dto.BookDTO;
import com.eg.HousingLibrary.dto.BookLendingDTO;
import com.eg.HousingLibrary.dto.UserDTO;
import com.eg.HousingLibrary.exception.UserNotFoundException;
import com.eg.HousingLibrary.model.Book;
import com.eg.HousingLibrary.model.BookIssueReceipt;
import com.eg.HousingLibrary.model.BookLending;
import com.eg.HousingLibrary.repository.UserRepository;
import com.eg.HousingLibrary.service.BookLendingService;
import com.eg.HousingLibrary.service.BookService;
import com.eg.HousingLibrary.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Tag(name = "Books API", description = "Manage books in the library")
public class Controller {

    @Autowired
    private UserService userService;
    @Autowired
    private BookLendingService bookLendingService;
    @Autowired
    private BookService bookService;
    @Autowired
    ApplicationContext context;

    //Make use of @Slf4j or else: private static final Logger logger= LoggerFactory.getLogger(Controller.class);
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.addUser(userDTO));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Integer id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (UserNotFoundException e) { // Custom exception for user not found
            log.warn("User with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } catch (Exception e) { // Generic exception handling
            log.error("Error deleting user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user. Please check the logs.");
        }
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO userdto) {
        UserDTO updatedUser = userService.updateUser(id, userdto);
        return ResponseEntity.ok(updatedUser); // Return the updated user data
    }


    // Create a new book lending
    @PostMapping("/borrow")
    public ResponseEntity<BookLendingDTO> borrowBook(@Valid @RequestParam Integer userId, @RequestParam Integer bookId) {
        return ResponseEntity.ok(bookLendingService.borrowBook(userId, bookId));
    }

    @PostMapping("/return")
    public ResponseEntity<BookLendingDTO> returnBook(@RequestParam Integer lendingId) {
        return ResponseEntity.ok(bookLendingService.returnBook(lendingId));
    }

    @GetMapping("/borrow/overdue/{id}")
    public ResponseEntity<Boolean> isBookOverdue(@PathVariable("lendingId") Integer lendingId) {
        boolean isOverdue = bookLendingService.isBookOverdue(lendingId);
        return ResponseEntity.ok(isOverdue);
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Get Book by ID
    @GetMapping("/books/{id}")
    public Optional<BookDTO> getBookById(@PathVariable("id") Integer id) {
       return bookService.getBookById(id).map(EntityDTOMapper::toBookDTO);
    }

    // Update Book by ID
    @PutMapping("/books/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Integer id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Book by ID
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build(); // No content as the book is deleted
    }

    @GetMapping("/overdue")
    public void getOverdueBooks() {
        bookLendingService.sendOverDueReminders();
        ResponseEntity.ok();
    }

    @PostMapping("/books/bulk")
    public ResponseEntity<String> processBulkBooks(@RequestBody List<BookDTO> books) {
        bookService.processBulkBooks(books);
        return ResponseEntity.ok("Book processing started asynchronously!");
    }

    @PostMapping("/{userId}/upload-img")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable("userId") Integer id, @RequestParam MultipartFile file) throws IOException {
        String filePath=userService.uploadProfilePicture(id, file);
        return ResponseEntity.ok("File uploaded Successfully"+filePath);
    }

    @GetMapping("/generate-receipt")
    public String generateNewReceipt() {
        // Fetch a new instance of BookIssueReceipt
        BookIssueReceipt receipt = context.getBean(BookIssueReceipt.class);
        return receipt.getReceiptId();
    }





}
