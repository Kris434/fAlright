package com.falright.falright.controller;

import com.falright.falright.repository.AircraftRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdminController {
    private final AircraftRepository aircraftRepository;

    public AdminController(AircraftRepository aircraftRepository)
    {
        this.aircraftRepository = aircraftRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminAccess(Model model, HttpSession session)
    {
         return "admin";
    }
}
