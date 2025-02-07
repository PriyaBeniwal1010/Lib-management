package com.eg.HousingLibrary.service;

import com.eg.HousingLibrary.ConverterUtility.EntityDTOMapper;
import com.eg.HousingLibrary.dto.BookDTO;
import com.eg.HousingLibrary.model.Book;
import com.eg.HousingLibrary.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;


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
