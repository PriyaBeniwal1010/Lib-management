package com.eg.HousingLibrary.repository;

import com.eg.HousingLibrary.model.BookLending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookLendingRepository extends JpaRepository<BookLending, Integer> {
    List<BookLending> findByDueDateBeforeAndReturnedDateIsNull(LocalDate today);
}
