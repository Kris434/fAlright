package com.falright.falright.controller;

import com.falright.falright.model.Flights;
import com.falright.falright.model.Passengers;
import com.falright.falright.model.Reservations;
import com.falright.falright.model.Users;
import com.falright.falright.repository.FlightRepository;
import com.falright.falright.repository.PassengersRepository;
import com.falright.falright.repository.ReservationRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ReservationController {

    private final FlightRepository flightsRepository;
    private final PassengersRepository passengersRepository;
    private final ReservationRepository reservationRepository;

    public ReservationController(FlightRepository flightsRepository, PassengersRepository passengersRepository, ReservationRepository reservationRepository) {
        this.flightsRepository = flightsRepository;
        this.passengersRepository = passengersRepository;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/reservation")
    public String showReservationForm(HttpSession session, @RequestParam("flightId") Integer flightId) {
        if (session.getAttribute("loggedInUser") != null) {
            Reservations reservations = new Reservations();
            Optional<Flights> optionalFlights = flightsRepository.findById(flightId);

            optionalFlights.ifPresent(reservations::setFlights_id);

            session.setAttribute("reservation", reservations);

            return "reservation-form";
        }
        else
        {
            return "home";
        }
    }

    @PostMapping("/personal-info")
    public String getPersonalInfoReservation(Model model, HttpSession session, @RequestParam("firstName") String name, @RequestParam("lastName") String lastName, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("email") String email, @RequestParam("city") String city, @RequestParam("postCode") String postCode, @RequestParam("address") String address)
    {
        Reservations reservation = (Reservations) session.getAttribute("reservation");
        Passengers passenger = new Passengers();
        Users user = (Users) session.getAttribute("loggedInUser");

        List<Reservations> list = reservationRepository.findByFlightId(reservation.getFlights_id());
        model.addAttribute("seats", list);

        passenger.setName(name);
        passenger.setSurname(lastName);
        passenger.setPhone_number(Integer.parseInt(phoneNumber));
        passenger.setUsers_id(user);
        passenger.setAddress(address);
        passenger.setCity(city);
        passenger.setEmail(email);
        passenger.setPost_code(postCode);

        reservation.setPassengers_id(passenger);

        passengersRepository.save(passenger);

        session.setAttribute("passenger", passenger);
        session.setAttribute("reservation", reservation);

        return "reservation-seats";
    }

    @PostMapping("/finalize")
    public String finalizeReservation(Model model, HttpSession session, @RequestParam("seat") Integer seat)
    {
        Passengers passenger = (Passengers) session.getAttribute("passenger");
        Reservations reservationSession = (Reservations) session.getAttribute("reservation");
        Reservations reservation = reservationRepository.findBySeatAndFlight(seat, reservationSession.getFlights_id());

        reservation.setStatus(true);
        reservation.setPassengers_id(passenger);
        reservationRepository.save(reservation);

        return "reservation-success";
    }

    @GetMapping("/your-reservations")
    public String showReservationsToUser(HttpSession session, Model model)
    {
        Users user = (Users) session.getAttribute("loggedInUser");

        List<Reservations> reservations = (List<Reservations>) reservationRepository.findByLoggedUser(user.getUser_id());

        model.addAttribute("userReservations", reservations);

        return "user-reservations";
    }
}
