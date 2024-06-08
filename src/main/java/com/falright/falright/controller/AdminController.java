package com.falright.falright.controller;

import com.falright.falright.model.Users;
import com.falright.falright.repository.AircraftRepository;
import com.falright.falright.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class AdminController {
    private final AircraftRepository aircraftRepository;
    private final UserService userService;

    public AdminController(AircraftRepository aircraftRepository, UserService userService)
    {
        this.aircraftRepository = aircraftRepository;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminAccess(Model model, HttpSession session)
    {
         return "admin";
    }
}


