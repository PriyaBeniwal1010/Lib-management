package com.example.housing;

import com.example.housing.controller.*;
import com.example.housing.model.Book;
import com.example.housing.model.Member;
import com.example.housing.model.StudentMember;
import com.example.housing.model.PrintBook;
import com.example.housing.service.BookLibraryService;
import com.example.housing.service.MemberLibraryService;
import com.example.housing.service.MemberRequestBook;
import com.example.housing.view.LibraryView;

import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        // Instantiate the services and components manually (no Spring Boot dependency injection here)
        LibraryView libraryView = new LibraryView();
        BookLibraryService bookLibraryService = new BookLibraryService();
        MemberLibraryService memberLibraryService = new MemberLibraryService();
        MemberRequestBook memberRequestBook = new MemberRequestBook();
        LibraryController memberRequestBookController = new LibraryController(bookLibraryService, memberLibraryService, memberRequestBook, libraryView);

        // Initialize Books and Members for Testing
        libraryView.displayMessage("Starting Library Application...");

        // Create a member
        Member member = new StudentMember(1, "John Doe", "password123", new HashSet<>(), 8);
        memberLibraryService.addMember(member);

        // Create a book
        HashSet<String> stores = new HashSet<>();
        stores.add("Delhi");
        stores.add("Mumbai");


        Book book = new PrintBook(1, "Java Programming", "12345", 10, 5, stores);
        bookLibraryService.addBook(book);

        // Show initial book details
        String bookDetails = bookLibraryService.getBookDetails(book.getBookID());
        libraryView.displayMessage("Initial Book Details: " + bookDetails);

        // Add member's request to borrow a book
        memberRequestBookController.addRequest(member, book);
        memberRequestBookController.processRequest(member, book, 1);

        // Attempt to borrow a book with qty exceeding limit
        Book ebook = new PrintBook(2, "Advanced Java", "12346", 5, 3, stores);
        memberRequestBookController.addRequest(member, ebook);
        memberRequestBookController.processRequest(member, ebook, 10);  // Trying to borrow more than allowed

        // Shut down executor after processing
        memberRequestBookController.shutdownExecutor();

        // Remove member
        memberLibraryService.removeMember(member);
        libraryView.displayMessage("Member removed: " + member.getName());
    }
}



































//package com.example.housing;
//
//import com.example.housing.controller.LibraryController;
//import com.example.housing.model.*;
//import com.example.housing.service.LibraryService;
//import com.example.housing.view.LibraryView;
//
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//public class Main {
//    public static void main(String[] args) {
//
//        Date date_of_issue_admin = Library.convertToDate(LocalDate.parse("2024-11-01"));
//        Date date_of_issue_stud = Library.convertToDate(LocalDate.parse("2025-12-12"));
//        Date returnDate1 = Library.convertToDate(LocalDate.parse("2025-01-10")); // Past return date (overdue)
//        Date returnDate2 = Library.convertToDate(LocalDate.parse("2025-02-15")); //
//        Date returnDate3 = Library.convertToDate(LocalDate.parse("2025-03-16"));
//        Date returnDate4 = Library.convertToDate(LocalDate.parse("2024-11-11"));
//
//        // Books in Library
//        Book ebook1 = new EBook(1001, "The Catcher in the Rye", "J.D. Salinger", "9780316769488", "Little, Brown and Company", "1951", 10, 5, 277, 400, "www.housing/ebooks/TheCatcherInTheRye.com", true, date_of_issue_admin, returnDate3);
//        Book ebook2 = new EBook(1002, "To Kill a Mockingbird", "Harper Lee", "9780060935467", "HarperCollins", "1960", 8, 3, 281, 350, "www.housing/ebooks/ToKillAMockingbird.com", true, date_of_issue_stud, returnDate1);
//        Book ebook3 = new EBook(1003, "Moby-Dick", "Herman Melville", "9781853260087", "Dover Publications", "1851", 5, 2, 585, 500, "www.housing/ebooks/MobyDick.com", false, date_of_issue_admin, returnDate4);
//        Book pbook1 = new PrintBook(2001, "To Kill a Mockingbird", "Harper Lee", "9780061120084", "J.B. Lippincott & Co.", "1960", 4, 18, 100, 45, Arrays.asList("Flipkart", "Amazon India", "Crossword"), date_of_issue_admin, returnDate3);
//        Book pbook2 = new PrintBook(2002, "1984", "George Orwell", "9780451524935", "Secker & Warburg", "1949", 5, 15, 50, 30, Arrays.asList("Bookish Santa", "Flipkart", "Pothi.com"), date_of_issue_stud, returnDate1);
//        Book pbook3 = new PrintBook(2003, "Moby-Dick", "Herman Melville", "9781503280786", "Harper & Brothers", "1851", 3, 20, 75, 20, Arrays.asList("Sapna Book House", "Amazon India", "Reliance Digital"), date_of_issue_stud, returnDate2);
//
//        // List of Books
//        List<Book> books = new ArrayList<>(Arrays.asList(ebook1, ebook2, ebook3, pbook1, pbook2, pbook3));
//
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate localDate = LocalDate.now();
//        // Current date
//        Date currentDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()); // Use LocalDate for current date
//
//        // Convert LocalDate to java.util.Date
//
//        ArrayList<Book> books1 = new ArrayList<>();
//        books1.add(ebook1);
//        books1.add(pbook3);
//
//        ArrayList<Book> books2 = new ArrayList<>();
//        books2.add(ebook3);
//        books2.add(pbook2);
//
//        // Create member instances (one overdue, one on time)
//        Member admin1 = new Member(101, "Admin1", "admin101@gmail.com", "adminpass101", null);
//        Member admin2 = new Member(102, "Admin2", "admin102@gmail.com", "adminpass102", null);
//        Member stud1 = new StudentMember(201, "student1", "student101@gmail.com", "studentpass101", books1);
//        Member stud2 = new StudentMember(202, "student2", "student102@gmail.com", "studentpass102", books2);
//
//        // List of Members
//        List<Member> members = Arrays.asList(admin1, admin2, stud1, stud2);
//
//        // Library Setup: Housing Library with books and members
//        Library housing = new Library(books, members);
//        LibraryView view = new LibraryView();
//        LibraryController controller = new LibraryController(housing, view);
//
//        //Authenticating the user
//        controller.authenticateMember(201, "studentpass101"); // Authenticating Student1
//        controller.authenticateMember(102, "adminpass102");  // Authenticating Admin2
//
//        //Add new printBook
//        Book pbook4 = new PrintBook(2004, "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", "Scribner", "1925", 6, 10, 180, 80, Arrays.asList("Amazon", "Barnes & Noble", "Flipkart"), date_of_issue_admin, returnDate2);
//        controller.addBook(pbook4);
//        // Listing Available Books
//        controller.listBooks();
//
//        // Borrowing Books (Example)
//        controller.borrowBook(201, 1001, 4); // Student1 borrows "The Catcher in the Rye"
//        controller.borrowBook(202, 2002, 1); // Student2 borrows "1984"
//
//        // Viewing Borrowed Books for a Student
//        controller.viewBorrowedBooks(201); // Student1's borrowed books
//
//        // Returning Books (Example)
//        System.out.println("Returning books not present yet");
//        controller.returnBook(207, 1001, 1); // Student1 returns "The Catcher in the Rye"
//
//        // Listing Available Books again (after returning)
//        controller.listBooks();
////        LibraryService service = new LibraryService();
////        service.addBook(ebook1);
////        service.addBook(ebook2);
////        service.addBook(pbook4);
////        service.addBook(pbook3);
////        service.addRequest(stud1);
////        service.addRequest(admin2);
////
////       service.processRequests(pbook1,2); // This will issue books in FIFO order
////        service.processRequests(pbook2,2);
////
////        // Sort books and members
////        service.sortBooksByISBN();
////        service.sortMembersByID(Arrays.asList(stud1, admin2));
////
////        // Filter available books
////        List<Book> availableBooks = service.filterAvailableBooks();
////        availableBooks.forEach(book -> System.out.println("Available Book: " + book.getISBN()));
////
////        // Filter overdue members
////        List<Member> overdueMembers = service.filterOverdueMembers(members);
////        overdueMembers.forEach(member -> System.out.println("Overdue Member: " + member.getName()));
//
//
//
//
//
//    }
//}
