package com.example.employeemanagementsystem.repository;

import com.example.employeemanagementsystem.validate.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken findByToken(String token);

}
