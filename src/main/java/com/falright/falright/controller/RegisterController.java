package com.falright.falright.controller;

import com.falright.falright.model.Users;
import com.falright.falright.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    private final UserRepository userRepository;


    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register-submit")
    public String register(@ModelAttribute Users user, Model model) {
        String plainPassword = user.getPassword();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(plainPassword);

        user.setPassword(hashedPassword);
        user.setRole(Users.Role.LOGGED);
        userRepository.save(user); // zapisanie użytkownika do bazy danych

        model.addAttribute("user", user);
        return "register-success";
    }
}
