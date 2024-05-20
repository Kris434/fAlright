package com.falright.falright.controller;

import com.falright.falright.model.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserDataController {

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