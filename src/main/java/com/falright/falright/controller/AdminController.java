package com.falright.falright.controller;

import com.falright.falright.model.Aircrafts;
import com.falright.falright.model.Users;
import com.falright.falright.repository.AircraftRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

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
        Users user = (Users) session.getAttribute("loggedInUser");

        if(user != null && user.getRole() == Users.Role.ADMIN)
        {
            List<Aircrafts> aircraftsList = (List<Aircrafts>) aircraftRepository.findAll();

            model.addAttribute("aircrafts", aircraftsList);

            return "admin";
        }
        else
        {
            return "home";
        }
    }
}
