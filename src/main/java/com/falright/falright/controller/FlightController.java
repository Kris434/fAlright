package com.falright.falright.controller;

import com.falright.falright.model.Flights;
import com.falright.falright.repository.AircraftRepository;
import com.falright.falright.repository.FlightRepository;
import com.falright.falright.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private AircraftRepository aircraftRepository;
    private FlightRepository flightRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public FlightController(AircraftRepository aircraftRepository, FlightRepository flightRepository, ReservationRepository reservationRepository)
    {
        this.flightRepository = flightRepository;
        this.aircraftRepository = aircraftRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping("")
    public String getFlights(Model model) {
        model.addAttribute("flights", flightRepository.findAll());
        return "flights";
    }

  
    @GetMapping("/search")
    public String searchFlights(@RequestParam("departure") String departure,
                                @RequestParam("destination") String destination,
                                @RequestParam(value = "departure_date", required = false) LocalDate departureDate,
                                @RequestParam(value = "arrival_date", required = false) LocalDate arrivalDate,
                                Model model) {

        LocalDateTime departureDateTimeStart = (departureDate != null) ? departureDate.atStartOfDay() : null;
        LocalDateTime departureDateTimeEnd = (departureDate != null) ? departureDate.atTime(23, 59, 59) : null;
        LocalDateTime arrivalDateTimeStart = (arrivalDate != null) ? arrivalDate.atStartOfDay() : null;
        LocalDateTime arrivalDateTimeEnd = (arrivalDate != null) ? arrivalDate.atTime(23, 59, 59) : null;

        List<Flights> flights;

        if (departureDate != null && arrivalDate != null) {
            flights = flightRepository.findFlightsByCriteria(
                    departure, destination, departureDateTimeStart, departureDateTimeEnd, arrivalDateTimeStart, arrivalDateTimeEnd);
        } else {
            flights = flightRepository.findFlightsByDepartureAndDestination(departure, destination);
        }

        if (flights.isEmpty()) {
            model.addAttribute("message", "Nie znaleziono lotów spełniających podane kryteria.");
            return "home";
        } else {
            model.addAttribute("flights", flights);
        }

        return "flights";
    }
}
