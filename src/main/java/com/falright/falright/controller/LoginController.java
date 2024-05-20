package com.falright.falright.controller;

import com.falright.falright.model.Users;
import com.falright.falright.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final UserRepository userRepository;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {

        Optional<Users> optionalUser = userRepository.findUserByUsername(username);

        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            String hashedPassword = user.getPassword();

            if (passwordMatches(password, hashedPassword)) {
                session.setAttribute("loggedInUser", user);
                session.setAttribute("role", user.getRole().name());


                if (user.getRole() == Users.Role.ADMIN) {
                    return "redirect:/admin";
                } else if (user.getRole() == Users.Role.EMPLOYEE) {
                    return "redirect:/employee";
                } else {
                    return "redirect:/home";
                }
            }
        }

        model.addAttribute("error", "Nieprawidłowa nazwa użytkownika lub hasło!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session)
    {
        session.removeAttribute("loggedInUser");
        return "home";
    }

    private boolean passwordMatches(String inputPassword, String hashedPassword) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(inputPassword, hashedPassword);
    }
}
