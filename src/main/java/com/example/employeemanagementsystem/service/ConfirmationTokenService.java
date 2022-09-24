package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.repository.ConfirmationTokenRepository;
import com.example.employeemanagementsystem.validate.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenService {
    @Autowired
    ConfirmationTokenRepository repository;

    public void saveToken(ConfirmationToken token){
        repository.save(token);
    }

    public ConfirmationToken getConfirmationToken(String token){
        return repository.findByToken(token);
    }
}
