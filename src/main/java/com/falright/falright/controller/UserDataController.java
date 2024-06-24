package com.falright.falright.controller;

import com.falright.falright.model.Users;
import com.falright.falright.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserDataController {
    private final UserService userService;

    @Autowired
    public UserDataController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-data")
    public String getUserProfile(Model model, HttpSession session) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            model.addAttribute("username", loggedInUser.getUsername());
            model.addAttribute("email", loggedInUser.getEmail());
            model.addAttribute("role", loggedInUser.getRole());

            return "userData";
        } else {
            return "redirect:/login";
        }
    }

}