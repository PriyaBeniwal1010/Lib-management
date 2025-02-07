package com.eg.HousingLibrary.controller;

import com.eg.HousingLibrary.dto.BookDTO;
import com.eg.HousingLibrary.dto.BookLendingDTO;
import com.eg.HousingLibrary.dto.UserDTO;
import com.eg.HousingLibrary.model.Book;
import com.eg.HousingLibrary.service.BookLendingService;
import com.eg.HousingLibrary.service.BookService;
import com.eg.HousingLibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Controller")
public class Controller {

    @Autowired
    private UserService userService;
    @Autowired
    private BookLendingService bookLendingService;
    @Autowired
    private BookService bookService;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.addUser(userDTO));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public void DeleteUser(@PathVariable("ID") Integer id){
        userService.deleteUser(id);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO userdto) {
        UserDTO updatedUser = userService.updateUser(id, userdto);
        return ResponseEntity.ok(updatedUser); // Return the updated user data
    }


    // Create a new book lending
    @PostMapping("/borrow")
    public ResponseEntity<BookLendingDTO> borrowBook(@RequestParam Integer userId, @RequestParam Integer bookId) {
        return ResponseEntity.ok(bookLendingService.borrowBook(userId, bookId));
    }

    @PostMapping("/return")
    public ResponseEntity<BookLendingDTO> returnBook(@RequestParam Integer lendingId) {
        return ResponseEntity.ok(bookLendingService.returnBook(lendingId));
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        return ResponseEntity.ok(bookService.addBook(bookDTO));
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }




}
