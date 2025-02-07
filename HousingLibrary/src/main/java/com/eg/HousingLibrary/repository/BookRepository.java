package com.eg.HousingLibrary.repository;

import com.eg.HousingLibrary.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BookRepository extends JpaRepository<Book, Integer> {
}
