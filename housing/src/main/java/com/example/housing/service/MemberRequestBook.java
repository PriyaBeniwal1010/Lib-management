package com.example.housing.service;

import com.example.housing.exception.LimitExceedException;
import com.example.housing.model.Book;
import com.example.housing.model.Member;
import com.example.housing.model.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class MemberRequestBook {
    BlockingDeque<Pair<Member,Book>> requestBook;
    ExecutorService executor;
    public MemberRequestBook() {
        requestBook = new LinkedBlockingDeque<>();
        executor= Executors.newFixedThreadPool(5);
    }

    public void addRequest(Member member, Book book){
        requestBook.offer(new Pair<>(member, book));
    }

    public Pair<Member,Book> getRequest() {
        if(!requestBook.isEmpty()) {
            return requestBook.poll();
        }
        else{
            return null;
        }
    }

    public void processRequest(Member member, Book book, int qty) {
        executor.submit(()->{
            try {
                Pair<Member, Book> request = requestBook.take();
                try{
                    if(request.getKey() instanceof StudentMember) {
                        String status=processStudentRequest((StudentMember)request.getKey(), request.getValue(), qty);
                        System.out.println(status);
                        throw new LimitExceedException("Limit exceeded");
                    }else {
                        System.out.println(processStudentRequest((StudentMember) request.getKey(), request.getValue(), qty));
                    }
                }catch (LimitExceedException e) {
                    System.out.println(e.getMessage());
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        });
    }

    private String processStudentRequest(StudentMember student, Book book, int qty) {
        try {
            // Here, you might check if the student has exceeded the book limit
            if (student.getBorrowedBooks().size() >= StudentMember.MAX_BOOKS_ALLOWED) {
                throw new LimitExceedException("You have exceeded the maximum number of borrowed books.");
            }

            MemberLibraryService memberLibraryService = new MemberLibraryService();
            BookLibraryService bookLibraryService = new BookLibraryService();
            if(book instanceof EBook){
                EBook requestedBook=(EBook)book;
                if(requestedBook.availableBook(requestedBook.getBookID())){
                    student.getBorrowedBooks().add(requestedBook);
                    memberLibraryService.updateMemberRecord(student.getMemberID(), student.getName(), student.getPassword(), student.getBorrowedBooks());
                    bookLibraryService.updateBookDetails(requestedBook.getBookID(),requestedBook.getBookName(), requestedBook.getISBN(), requestedBook.getTotalQuantity(), requestedBook.setIssuedQuantity(requestedBook.getIssuedQuantity()-qty), requestedBook.isDRMProtected(), requestedBook.getDownloadLink(), null);
                    return "Book issued Successfully to"+student.getName();
                }

            }else{
                PrintBook requestedBook=(PrintBook)book;
                if(requestedBook.getAvailableQuantity() >= qty){
                    student.getBorrowedBooks().add(requestedBook);
                    memberLibraryService.updateMemberRecord(student.getMemberID(), student.getName(), student.getPassword(), student.getBorrowedBooks());
                    bookLibraryService.updateBookDetails(requestedBook.getBookID(),requestedBook.getBookName(), requestedBook.getISBN(), requestedBook.getTotalQuantity(), requestedBook.getAvailableQuantity(), false, null, requestedBook.getBookStore());
                    return "Book issued Successfully to"+student.getName();
                }
            }

        } catch (LimitExceedException e) {
            System.out.println(e.getMessage());
        }
        return "Some Error";
    }

    // Shut down the executor gracefully
    public void shutdown() {
        executor.shutdown();
    }

}
