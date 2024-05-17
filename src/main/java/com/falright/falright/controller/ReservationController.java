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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

            if(optionalFlights.isPresent())
            {
                reservations.setFlights_id(optionalFlights.get());
            }

            session.setAttribute("reservation", reservations);

            return "reservation-form";
        }
        else
        {
            return "home";
        }
    }

    @PostMapping("/finalize")
    public String finalizeReservation(HttpSession session, @RequestParam("firstName") String name,
                                      @RequestParam("lastName") String lastName, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("seat") Integer seat)
    {
        Reservations reservation = (Reservations) session.getAttribute("reservation");
        Passengers passenger = new Passengers();
        Users user = (Users) session.getAttribute("loggedInUser");

        passenger.setName(name);
        passenger.setSurname(lastName);
        passenger.setPhone_number(Integer.parseInt(phoneNumber));
        passenger.setUsers_id(user);

        reservation.setPassengers_id(passenger);
        reservation.setSeat_number(seat);

        passengersRepository.save(passenger);
        reservationRepository.save(reservation);

        session.setAttribute("passenger", reservation);

        return "reservation-success";
    }
}
