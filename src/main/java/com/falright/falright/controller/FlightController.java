package com.falright.falright.controller;

import com.falright.falright.model.Flights;
import com.falright.falright.repository.AircraftRepository;
import com.falright.falright.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private AircraftRepository aircraftRepository;
    private FlightRepository flightRepository;

    @Autowired
    public FlightController(AircraftRepository aircraftRepository, FlightRepository flightRepository)
    {
        this.flightRepository = flightRepository;
        this.aircraftRepository = aircraftRepository;
    }

    @GetMapping("")
    public String getFlights(Model model) {
        model.addAttribute("flights", flightRepository.findAll());
        return "flights";
    }
}
