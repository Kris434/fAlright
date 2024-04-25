package com.falright.falright.controller;

import com.falright.falright.model.Aircraft;
import com.falright.falright.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/aircrafts")
public class AircraftController {
    private AircraftRepository aircraftRepository;

    @Autowired
    public AircraftController(AircraftRepository aircraftRepository)
    {
        this.aircraftRepository = aircraftRepository;
    }

    @GetMapping("")
    public @ResponseBody Iterable<Aircraft> getAircrafts()
    {
        return aircraftRepository.findAll();
    }

    @GetMapping("/add/{model}/{capacity}")
    public String addNewAircraft(@PathVariable("model") String model, @PathVariable("capacity") Integer capacity)
    {
        Aircraft aircraft = new Aircraft(model, capacity, false);

        aircraftRepository.save(aircraft);

        return "redirect:/";
    }
}
