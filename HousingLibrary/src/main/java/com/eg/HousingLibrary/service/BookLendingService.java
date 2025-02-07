package com.eg.HousingLibrary.service;

import com.eg.HousingLibrary.ConverterUtility.EntityDTOMapper;
import com.eg.HousingLibrary.dto.BookLendingDTO;
import com.eg.HousingLibrary.model.Book;
import com.eg.HousingLibrary.model.BookLending;
import com.eg.HousingLibrary.model.User;
import com.eg.HousingLibrary.repository.BookLendingRepository;
import com.eg.HousingLibrary.repository.BookRepository;
import com.eg.HousingLibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookLendingService {

    @Autowired
    private final BookLendingRepository bookLendingRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BookRepository bookRepository;

    public BookLendingService(BookLendingRepository bookLendingRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.bookLendingRepository = bookLendingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public BookLendingDTO borrowBook(Integer userId, Integer bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookLending bookLending = new BookLending();
        bookLending.borrowBook(user, book);

        return EntityDTOMapper.toBookLendingDTO(bookLendingRepository.save(bookLending));
    }

    public BookLendingDTO returnBook(Integer lendingId) {
        Optional<BookLending> bookLendingOpt = bookLendingRepository.findById(lendingId);
        if (bookLendingOpt.isPresent()) {
            BookLending bookLending = bookLendingOpt.get();
            bookLending.returnBook();
            return EntityDTOMapper.toBookLendingDTO(bookLendingRepository.save(bookLending));
        }
        throw new RuntimeException("Lending record not found!");
    }


    public boolean isBookOverdue(Integer lendingId) {
        return bookLendingRepository.findById(lendingId)
                .map(BookLending::isOverdue)
                .orElse(false);
    }
}
