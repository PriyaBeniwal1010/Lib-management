package com.example.housing.model;

import com.example.housing.exception.BookNotBorrowedException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private List<Book> books;
    private List<Member> members;

    public Library(List<Book> books, List<Member> members) {
        this.books = books;
        this.members = members;
    }

    public static Date convertToDate(LocalDate localDate) {
        LocalDateTime localDateTime = localDate.atStartOfDay();
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    // Method to add a new book to the library
    public void addBook(Book book) {
        books.add(book);
    }

    // Method to remove a book by its ID
    public String removeBook(int bookID) {
        Book book = findBookByID(bookID);
        if (book != null) {
            books.remove(book);
            return "Book removed successfully.";
        }
        return "Book not found.";
    }

    // Method to add a new member to the library
    public void addMember(Member member) {
        members.add(member);
    }

    // Authenticate a member by their ID and password
    public Member authenticateMember(int memberID, String password) {
        return members.stream().filter((Member member) -> member.getMemberID() == memberID).findFirst().orElse(null);
    }

    // Issue a book to a member if available
    public synchronized String borrowBook(int memberID, int bookID, int qty) {
        Member member = findMemberByID(memberID);
        Book book = findBookByID(bookID);

        if (book == null || member == null) {
            return "Member or Book not found.";
        }

        // Check if there are enough available copies of the book
        if (book.getAvailableQuantity() < qty) {
            return "Not enough copies available.";
        }

        // Proceed with borrowing the book
        return member.borrowBook(book, qty);
    }

    // Return a borrowed book and update stock
    public synchronized String returnBook(int memberID, int bookID, int qty) throws BookNotBorrowedException {
        Member member = findMemberByID(memberID);
        Book book = findBookByID(bookID);
        try {
            member.getBorrowedBooks().contains(book);
        }catch(BookNotBorrowedException e) {
            System.out.println(e.getMessage());
        }

        if (member == null || book == null) {
            return "Member or Book not found.";
        }
        // Proceed with returning the book
        String returnStatus = member.returnBook(book, qty);

        if (returnStatus.equals("Book returned successfully.")) {
            // Update the stock of the book in the library
            updateBookStock(book, qty);
        }
        return returnStatus;
    }

    // Helper method to find a member by their ID
    private Member findMemberByID(int memberID) {
        for (Member member : members) {
            if (member.getMemberID() == memberID) {
                return member;
            }
        }
        return null;
    }

    // Helper method to find a book by its ID
    private Book findBookByID(int bookID) {
        for (Book book : books) {
            if (book.getBookID() == bookID) {
                return book;
            }
        }
        return null;
    }

    // Update the available stock of a book after it is returned
    private void updateBookStock(Book book, int qty) {
        if (book instanceof PrintBook) {
            PrintBook printBook = (PrintBook) book;
            printBook.addAvailableQuantity(qty);  // Add the returned quantity to available stock
        } else if (book instanceof EBook) {  // Ensure you're checking for EBook here
            EBook eBook = (EBook) book;
            eBook.addAvailableQuantity(qty);  // Add the returned quantity to available stock
        }
    }


    // List all available books
    public String listBooks() {
        if (books.isEmpty()) {
            return "No books available in the library.";
        } else {
            StringBuilder bookList = new StringBuilder("Books available in the library:\n");
            for (Book book : books) {
                bookList.append(book.getBookName()).append(" (ID: ").append(book.getBookID()).append(")\n");
            }
            return bookList.toString();
        }
    }

    // View the borrowed books of a specific member
    public String viewBorrowedBooks(int memberID) {
        Member member = findMemberByID(memberID);
        if (member == null) {
            return "Member with ID " + memberID + " not found.";
        }

        String borrowedBooks = member.viewBorrowedBooks();  // Get the list of borrowed books
        return borrowedBooks != null ? borrowedBooks : "No books borrowed.";
    }

    public List<Book> SortBooks() {
        return books.stream().sorted().collect(Collectors.toList());
    }

    public List<Member> SortMembers(){
        return members.stream().sorted(Comparator.comparing(Member::getMemberID)).collect(Collectors.toList());
    }

}
