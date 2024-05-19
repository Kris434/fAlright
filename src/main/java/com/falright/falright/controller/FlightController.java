package com.falright.falright.controller;

import com.falright.falright.model.Aircrafts;
import com.falright.falright.model.Flights;
import com.falright.falright.model.Reservations;
import com.falright.falright.repository.AircraftRepository;
import com.falright.falright.repository.FlightRepository;
import com.falright.falright.repository.ReservationRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/add/{aircraft_id}")
    public String addFlight(HttpSession session, Model model, @PathVariable("aircraft_id") Integer airId)
    {
        Optional<Aircrafts> oAircraft = aircraftRepository.findById(airId);
        Aircrafts aircraft = new Aircrafts();

        if(oAircraft.isPresent())
        {
            aircraft = oAircraft.get();
        }

        Flights flight = new Flights();

        flight.setAircrafts_id(aircraft);

        flightRepository.save(flight);

        List<Reservations> reservationsList = new ArrayList<>();

        for(int i = 0; i < aircraft.getCapacity(); i++)
        {
            Reservations r = new Reservations();

            r.setSeat_number(i + 1);
            r.setFlights_id(flight);
            r.setStatus(false);

            reservationsList.add(r);
            reservationRepository.save(r);
        }

        session.setAttribute("reservationList", reservationsList);

        return "home";
    }
}
