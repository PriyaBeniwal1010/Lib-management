package com.eg.HousingLibrary.controller;

import com.eg.HousingLibrary.utility.EntityDTOMapper;
import com.eg.HousingLibrary.dto.BookDTO;
import com.eg.HousingLibrary.dto.BookLendingDTO;
import com.eg.HousingLibrary.dto.UserDTO;
import com.eg.HousingLibrary.exception.UserNotFoundException;
import com.eg.HousingLibrary.model.BookIssueReceipt;
import com.eg.HousingLibrary.repository.UserRepository;
import com.eg.HousingLibrary.service.BookLendingService;
import com.eg.HousingLibrary.service.BookService;
import com.eg.HousingLibrary.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/user/add")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.addUser(userDTO));
    }

    @GetMapping("/user/list")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/user/remove/{id}")
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


    @PutMapping("/user/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO userdto) {
        UserDTO updatedUser = userService.updateUser(id, userdto);
        return ResponseEntity.ok(updatedUser); // Return the updated user data
    }


    // Create a new book lending
    @PostMapping("/issue/borrow")
    public ResponseEntity<BookLendingDTO> borrowBook(@Valid @RequestParam Integer userId, @RequestParam Integer bookId) {
        return ResponseEntity.ok(bookLendingService.borrowBook(userId, bookId));
    }

    @PostMapping("/issue/return")
    public ResponseEntity<BookLendingDTO> returnBook(@RequestParam Integer lendingId) {
        return ResponseEntity.ok(bookLendingService.returnBook(lendingId));
    }

    @GetMapping("/issue/borrow/overdue/{lendingId}")
    public ResponseEntity<Boolean> isBookOverdue(@PathVariable("lendingId") Integer lendingId) {
        boolean isOverdue = bookLendingService.isBookOverdue(lendingId);
        return ResponseEntity.ok(isOverdue);
    }

    @PostMapping("/book/add")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @GetMapping("/book/list")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Get Book by ID
    @GetMapping("/book/{id}")
    public Optional<BookDTO> getBookById(@PathVariable("id") Integer id) {
       return bookService.getBookById(id).map(EntityDTOMapper::toBookDTO);
    }

    // Update Book by ID
    @PutMapping("/book/update/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Integer id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        if (updatedBook != null) {
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Book by ID
    @DeleteMapping("/book/remove/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build(); // No content as the book is deleted
    }

    @GetMapping("/user-book/overdue")
    public void getOverdueBooks() {
        bookLendingService.sendOverDueReminders();
        ResponseEntity.ok();
    }

    @PostMapping("/book/add-bulk")
    public ResponseEntity<String> processBulkBooks(@RequestBody List<BookDTO> books) {
        bookService.processBulkBooks(books);
        return ResponseEntity.ok("Book processing started asynchronously!");
    }

    @PostMapping("/user/{userId}/upload-img")
    public ResponseEntity<String> uploadProfilePicture(@PathVariable("userId") Integer id, @RequestParam MultipartFile file) {
        String filePath=userService.uploadProfilePicture(id, file);
        return ResponseEntity.ok("File uploaded Successfully"+filePath);
    }

    @GetMapping("/user/generate-receipt")
    public String generateNewReceipt() {
        // Fetch a new instance of BookIssueReceipt
        BookIssueReceipt receipt = context.getBean(BookIssueReceipt.class);
        return receipt.getReceiptId();
    }





}
