package com.falright.falright.controller;

import com.falright.falright.model.Users;
import com.falright.falright.repository.AircraftRepository;
import com.falright.falright.repository.FlightRepository;
import com.falright.falright.service.ReportService;
import com.falright.falright.service.UserService;
import com.falright.falright.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class AdminController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ReportService reportService;

    public AdminController(UserService userService, UserRepository userRepository, ReportService reportService)
    {
        this.userService = userService;
        this.userRepository = userRepository;
        this.reportService = reportService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminAccess(Model model, HttpSession session)
    {
        Users user = (Users) session.getAttribute("loggedInUser");

        if(user != null && user.getRole() == Users.Role.ADMIN)
        {
            List<Users> users = userRepository.findAll();
            List<Users.Role> roles = Arrays.asList(Users.Role.values());

            model.addAttribute("users", users);
            model.addAttribute("roles", roles);

            return "admin";
        }
        else
        {
            return "home";
        }
    }

    @PostMapping("/updateRole")
    public String updateRole(HttpSession session, Model model,
                             @RequestParam("user") Integer userId,
                             @RequestParam("role") String role) {
        Users user = (Users) session.getAttribute("loggedInUser");

        if(user != null && user.getRole() == Users.Role.ADMIN) {
            Users userToUpdate = userRepository.findById(userId).orElse(null);

            if(userToUpdate != null) {
                userToUpdate.setRole(Users.Role.valueOf(role));
                userRepository.save(userToUpdate);
            }

            return "redirect:/admin";
        } else {
            return "home";
        }
    }

    @GetMapping("/admin/assignRole")
    public String showAssignRoleForm(Model model)
    {
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "assignRole";
    }

    @PostMapping("/admin/assignRole")
    public String assignRole(RedirectAttributes redirectAttributes,
                             @RequestParam("username") String username,
                             @RequestParam("role") Users.Role role)
    {
        userService.assignRole(username, role);

        redirectAttributes.addFlashAttribute("message", "Uprawnienie zostało zmienione!");
        return "redirect:/admin";
    }

    @GetMapping("/admin/report")
    public String generateReport(Model model)
    {
        model.addAttribute("aircraftWithMostFlights", reportService.getAircraftWithMostFlights());
        model.addAttribute("flightWithMostReservations", reportService.getFlightWithMostReservations());
        model.addAttribute("longestFlight", reportService.getLongestFlight());
        model.addAttribute("mostExpensiveFlight", reportService.getMostExpensiveFlight());
        model.addAttribute("flightWithMostPassengers", reportService.getFlightWithMostPassengers());

        return "report";
    }

}
