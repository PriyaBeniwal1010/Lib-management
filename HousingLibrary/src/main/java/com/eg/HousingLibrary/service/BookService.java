package com.eg.HousingLibrary.service;

import com.eg.HousingLibrary.ConverterUtility.EntityDTOMapper;
import com.eg.HousingLibrary.dto.BookDTO;
import com.eg.HousingLibrary.model.Book;
import com.eg.HousingLibrary.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;



import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    //Processing bulk books to be added in the database
    public CompletableFuture<Void> processBulkBooks(List<BookDTO> books) {
        return CompletableFuture.runAsync(() -> {
            for (BookDTO bookDTO : books) {
                Book book = new Book();
                book.setTitle(bookDTO.getTitle());
                book.setAuthor(bookDTO.getAuthor());
                bookRepository.save(book);
                System.out.println("Processed book: " + bookDTO.getTitle() + " by " + Thread.currentThread().getName());
            }
        });
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(EntityDTOMapper::toBookDTO)
                .collect(Collectors.toList());
    }

    //Add a new book to the db
    @Transactional
    public BookDTO addBook(BookDTO bookDTO) {
        Book book = EntityDTOMapper.toBookEntity(bookDTO);
        return EntityDTOMapper.toBookDTO(bookRepository.save(book));
    }

    //Get a book by id to perform update and another operations
    public Optional<Book> getBookById(Integer id){
        return bookRepository.findById(id);
    }

    @Transactional
    public BookDTO updateBook(Integer id, BookDTO bookdto){
        if(bookRepository.existsById(id)){
            Book book = EntityDTOMapper.toBookEntity(bookdto);
            return EntityDTOMapper.toBookDTO(bookRepository.save(book));
        }
        return null;
    }

    @Transactional
    public void deleteBook(Integer id){
        bookRepository.deleteById(id);
    }

}
