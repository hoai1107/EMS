package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.service.UserService;
import com.example.employeemanagementsystem.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String indexPage(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("registerError", null);
        model.addAttribute("errorMessage", null);

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user, Model model) {
        user.setActive(true);
        user.setRoles("ROLE_USER");
        model.addAttribute("registerError", false);

        try {
            userService.saveNewUser(user);
        } catch (Exception exception) {
            model.addAttribute("registerError", true);
            model.addAttribute("errorMessage", exception.getMessage());
            return "register";
        }

        return "register";
    }

//    @GetMapping("/update_role")
//    public ResponseEntity<?> updateRole(@RequestParam("username") String username) {
//
//        if(Objects.equals(username, null)){
//            return ResponseEntity.badRequest().body("Username must not be null!");
//        }
//
//        try {
//            userService.updateUserRoles(username);
//        } catch (Exception exception) {
//            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
//        }
//
//        return ResponseEntity.ok("Update successfully");
//    }
}
