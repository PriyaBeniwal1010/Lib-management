package com.eg.HousingLibrary.repository;

import com.eg.HousingLibrary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
