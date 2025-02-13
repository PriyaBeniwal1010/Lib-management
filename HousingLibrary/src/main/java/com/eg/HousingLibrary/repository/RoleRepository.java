package com.eg.HousingLibrary.repository;

import com.eg.HousingLibrary.model.ERole;
import com.eg.HousingLibrary.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
