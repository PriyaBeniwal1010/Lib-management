package com.eg.HousingLibrary.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookLendingDTO {
    private Integer id;
    private Integer userId; // References the User
    private Integer bookId; // References the Book
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;


}
