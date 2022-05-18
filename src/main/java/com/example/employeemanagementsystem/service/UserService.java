package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.repository.UserRepository;
import com.example.employeemanagementsystem.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void saveNewUser(User user) throws Exception {
        if (isUsernameExist(user.getUsername())) {
            throw new Exception("Username already existed!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
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
