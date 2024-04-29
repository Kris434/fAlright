package com.falright.falright.controller;

import com.falright.falright.model.Users;
import com.falright.falright.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        return "login"; // Zwraca widok formularza logowania
    }

    @PostMapping("/login-submit")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session, Model model) {

        // Znajdź użytkownika w bazie danych na podstawie nazwy użytkownika
        Optional<Users> optionalUser = userRepository.findByUsername(username);

        // Sprawdź, czy użytkownik istnieje i czy hasło się zgadza
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            String hashedPassword = user.getPassword(); // Pobierz zahaszowane hasło z bazy danych

            // Porównaj zahaszowane hasło z podanym hasłem
            if (passwordMatches(password, hashedPassword)) {
                // Ustaw atrybut sesji dla zalogowanego użytkownika
                session.setAttribute("loggedInUser", user);
                model.addAttribute("user", user);
                return "login-success"; // Przekieruj do strony powitalnej po zalogowaniu
            }
        }

        // Jeśli użytkownik nie istnieje lub hasło jest nieprawidłowe, zwróć do formularza logowania z komunikatem
        model.addAttribute("error", "Invalid username or password. Please try again.");
        return "login";
    }

    private boolean passwordMatches(String inputPassword, String hashedPassword) {
        // Użyj BCryptPasswordEncoder do porównania hasła
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(inputPassword, hashedPassword);
    }
}
