package com.falright.falright.controller;

import com.falright.falright.model.Users;
import com.falright.falright.repository.UserRepository;
import com.falright.falright.repository.ValidationGroups;
import com.falright.falright.service.EmailServiceImpl;
import com.falright.falright.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired private EmailServiceImpl emailService;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/changePassword")
    public String getChangePassword(Model model) {
        model.addAttribute("user", new Users());
        return "changePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(HttpSession session, Model model, @Validated({ValidationGroups.ChangePassword.class}) @ModelAttribute("user") Users user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        Users loggedInUser = (Users) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            // Dodanie walidacji haseł
            if (user.getNewPassword() != null && user.getRpassword() != null) {
                if (!user.getNewPassword().equals(user.getRpassword())) {
                    bindingResult.addError(new FieldError("user", "rpassword", "Hasła muszą być takie same!"));
                }
            }

            if (bindingResult.hasErrors()) {
                return "changePassword";
            }

            if (passwordEncoder.matches(user.getPassword(), loggedInUser.getPassword())) {

                loggedInUser.setPassword(passwordEncoder.encode(user.getNewPassword()));
                userService.saveUser(loggedInUser);
                emailService.sendEmail(loggedInUser.getEmail(), "Zmiana hasła", "Twoje hasło zostało zmienione pomyślnie!");
                redirectAttributes.addFlashAttribute("message", "Hasło zostało zmienione!");

                return "redirect:/user-data";
            } else {
                model.addAttribute("error", "Stare hasło jest nieprawidłowe");
                return "changePassword";
            }
        } else {
            return "redirect:/login";
        }
    }

}
