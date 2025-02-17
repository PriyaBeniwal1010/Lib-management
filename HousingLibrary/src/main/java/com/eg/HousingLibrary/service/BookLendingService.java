package com.eg.HousingLibrary.service;

import com.eg.HousingLibrary.utility.EntityDTOMapper;
import com.eg.HousingLibrary.dto.BookLendingDTO;
import com.eg.HousingLibrary.model.Book;
import com.eg.HousingLibrary.model.BookLending;
import com.eg.HousingLibrary.model.User;
import com.eg.HousingLibrary.repository.BookLendingRepository;
import com.eg.HousingLibrary.repository.BookRepository;
import com.eg.HousingLibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookLendingService {

    @Autowired
    private final BookLendingRepository bookLendingRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private NotificationService notificationService;

    public BookLendingService(BookLendingRepository bookLendingRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.bookLendingRepository = bookLendingRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Scheduled(cron="0 0 9 * * ?")
    public void sendOverDueReminders(){
        LocalDate today=LocalDate.now();
        List<BookLending> overDueBooks=bookLendingRepository.findByDueDateBeforeAndReturnedDateIsNull(today);
        for(BookLending lend:overDueBooks){
            System.out.print("Reminder: Book"+lend.getBook().getTitle()+"is overDue for user id"+ lend.getUser().getName());
        }

    }


    public BookLendingDTO borrowBook(Integer userId, Integer bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookLending bookLending = new BookLending();
        bookLending.borrowBook(user, book);

        notificationService.sendOverdueReminder(user.getEmail());
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
