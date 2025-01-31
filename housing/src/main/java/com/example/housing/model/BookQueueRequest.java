package com.example.housing.model;

import com.example.housing.exception.BookNotBorrowedException;
import com.example.housing.exception.LimitExceedException;

public class BookQueueRequest implements Runnable {
    private Member member;
    private Book book;
    private int qty;
    private String action;

    public BookQueueRequest(Member member, Book book, int qty, String action) {
        this.member = member;
        this.book = book;
        this.qty = qty;
        this.action = action;
    }


    @Override
    public void run() {
        try{
            if("borrow".equals(action)){
                synchronized (book){
                    if(book.getAvailableQuantity()>=qty){
                        member.borrowBook(book, qty);
                        System.out.println("Book"+book.getBookName()+"issued successfully to "+member.getName());
                        book.setIssuedQuantity(book.getIssuedQuantity()+qty);

                    }
                    else{
                        System.out.println("Requested no of qty not available.");
                    }
                }

            }else if("return".equals(action)){
                try{
                    synchronized(book){
                        if(member.getBorrowedBooks().contains(book)){
                            member.returnBook(book, qty);
                            System.out.println("Book returned successfully to "+member.getName());
                            book.setIssuedQuantity(book.getIssuedQuantity()-qty);
                        }else{
                            throw new BookNotBorrowedException("Book Not Borrowed by the member"+member.getName());
                        }
                    }
                }catch(BookNotBorrowedException b){
                    System.out.println(b.getMessage());
                }
            }
        }catch (LimitExceedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
