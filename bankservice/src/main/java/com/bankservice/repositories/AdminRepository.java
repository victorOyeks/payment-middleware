package com.bankservice.repositories;

import com.bankservice.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findByUserName(String username);

    boolean existsByUserName(String adminEmail);
}
