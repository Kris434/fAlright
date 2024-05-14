package com.falright.falright.controller;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ReservationController {
    @GetMapping("/reservation2")
    public String showReservationForm(Model model) {
        // Dodaj dowolne potrzebne dane do modelu (np. szczegóły lotu)
        return "reservation-form"; // Zwróć nazwę szablonu Thymeleaf
    }

    @PostMapping("/reservation")
    public String handleReservationForm(@ModelAttribute User user) {
        // Przetwarzaj rezerwację (np. zapisz w bazie danych)
        // Tutaj możesz użyć flightId, passengerName i phoneNumber

        // Przekieruj na stronę sukcesu lub wyświetl komunikat potwierdzenia
        return "reservation-form"; // Zwróć nazwę szablonu dla sukcesu
    }
}
