package com.eg.HousingLibrary.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class BookLending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "UserId cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull(message = "Book cannot be null")
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private LocalDate borrowedDate;
    @Future(message ="Due Date must be future Date")
    private LocalDate dueDate;
    private LocalDate returnedDate;

    private static final int LENDING_PERIOD_DAYS = 14; // 2 weeks lending period

    public void borrowBook(User user, Book book) {
        this.user = user;
        this.book = book;
        this.borrowedDate = LocalDate.now();
        this.dueDate = borrowedDate.plusDays(LENDING_PERIOD_DAYS);
        this.returnedDate = null; // Book is not returned yet
    }


    public void returnBook() {
        this.returnedDate = LocalDate.now();
    }


    public boolean isOverdue() {
        return (returnedDate == null && LocalDate.now().isAfter(dueDate));
    }


    public long getOverdueDays() {
        if (isOverdue()) {
            return LocalDate.now().toEpochDay() - dueDate.toEpochDay();
        }
        return 0;
    }
}
