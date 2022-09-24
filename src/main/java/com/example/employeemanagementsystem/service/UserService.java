package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.repository.UserRepository;
import com.example.employeemanagementsystem.user.User;
import com.example.employeemanagementsystem.validate.ConfirmationToken;
import com.example.employeemanagementsystem.validate.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ConfirmationTokenService tokenService;
    @Autowired
    EmailService emailService;

    public void saveNewUser(User user) throws Exception {
        if (isUsernameExist(user.getUsername())) {
            throw new Exception("Username already existed!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                user,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30)
        );

        tokenService.saveToken(confirmationToken);

        String link = "http://localhost:8080/confirm?token=" + token;
        //TODO: Send confirmation email

        emailService.send(user.getEmail(), emailService.buildEmail(user.getUsername(), link));
    }

    public boolean confirmToken(String token) {
        ConfirmationToken confirmationToken = tokenService.getConfirmationToken(token);
        if (confirmationToken == null) {
            return false;
        }

        User user = confirmationToken.getUser();
        LocalDateTime current = LocalDateTime.now();

        if (current.isAfter(confirmationToken.getExpiredAt())) {
            return false;
        }

        user.setActive(true);
        userRepository.save(user);

        return true;
    }

    public boolean isUsernameExist(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public void updateUserRoles(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        User user = optionalUser.get();
        user.setRoles("ROLE_USER,ROLE_ADMIN");

        userRepository.save(user);
    }
}
