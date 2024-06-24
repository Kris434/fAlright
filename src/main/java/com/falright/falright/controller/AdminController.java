package com.falright.falright.controller;

import com.falright.falright.model.Users;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class AdminController {

    private final UserRepository userRepository;
    private final UserService userService;


    public AdminController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
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
        else {
            return "home";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/assignRole")
    public String showAssignRoleForm(Model model) {

        List<Users> users = userService.getAllNonAdminUsers();
        List<Users.Role> roles = Arrays.stream(Users.Role.values())
                .filter(role -> !role.equals(Users.Role.ADMIN))
                        .collect(Collectors.toList());

        model.addAttribute("users", users);
        model.addAttribute("roles", roles);

        return "assignRole";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/assignRole")
    public String assignRole(RedirectAttributes redirectAttributes,
                             @RequestParam("username") String username,
                             @RequestParam("role") Users.Role role) {

        userService.assignRole(username, role);

        redirectAttributes.addFlashAttribute("message", "Uprawnienie zostało zmienione!");
        return "redirect:/admin";
    }

}
