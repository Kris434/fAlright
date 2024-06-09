package com.falright.falright.controller;

import com.falright.falright.model.Aircrafts;
import com.falright.falright.model.Flights;
import com.falright.falright.model.Reservations;
import com.falright.falright.model.Users;
import com.falright.falright.repository.AircraftRepository;
import com.falright.falright.repository.FlightRepository;
import com.falright.falright.repository.ReservationRepository;
import com.falright.falright.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {
    private final AircraftRepository aircraftRepository;
    private final FlightRepository flightRepository;
    private final ReservationRepository reservationRepository;

    public EmployeeController(AircraftRepository aircraftRepository, FlightRepository flightRepository,
                              ReservationRepository reservationRepository)
    {
        this.aircraftRepository = aircraftRepository;
        this.flightRepository = flightRepository;
        this.reservationRepository = reservationRepository;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee")
    public String employeePage(HttpSession session, Model model)
    {
        Users user = (Users) session.getAttribute("loggedInUser");


        if(user != null && user.getRole() == Users.Role.EMPLOYEE)
        {
            List<Aircrafts> aircraftsList = (List<Aircrafts>) aircraftRepository.findAll();
            session.setAttribute("aircrafts", aircraftsList);
            model.addAttribute("aircraft", new Aircrafts());
            model.addAttribute("flight", new Flights());

            return "employee";
        }
        else {
            return "home";
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/addAircraft")
    public String addAircraft(@Valid @ModelAttribute("aircraft") Aircrafts aircraft, BindingResult bindingResult,
                              RedirectAttributes redirectAttributes, HttpSession session, Model model)
    {
        Users user = (Users) session.getAttribute("loggedInUser");

        if(user != null && user.getRole() == Users.Role.EMPLOYEE)
        {
            if (bindingResult.hasErrors()) {
                model.addAttribute("aircraft", aircraft);
                return "employee";
            }


            aircraft.setStatus(true);
            aircraftRepository.save(aircraft);

            List<Aircrafts> aircraftsList = (List<Aircrafts>) aircraftRepository.findAll();
            session.setAttribute("aircrafts", aircraftsList);

            return "redirect:/employee";
        }
        else {
            return "home";
        }
    }

    @PostMapping("/employee/addFlight")
    public String addFlight(@ModelAttribute("flight") Flights flight,
                             HttpSession session, Model model,
                            @RequestParam("aircraft") Aircrafts aircraft,
                            @RequestParam("destination") String destination,
                            @RequestParam("departurePoint") String departurePoint,
                            @RequestParam("depTime") LocalDateTime depTime,
                            @RequestParam("arrivalTime") LocalDateTime arrivalTime,
                            @RequestParam("price") Double price)
    {
        Users user = (Users) session.getAttribute("loggedInUser");

        if(user != null && user.getRole() == Users.Role.EMPLOYEE) {


            flight.setAircrafts_id(aircraft);
            flight.setDestination(destination);
            flight.setDeparture_time(depTime);
            flight.setArrival_time(arrivalTime);
            flight.setPrice(price);
            flight.setDeparture_point(departurePoint);
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

            model.addAttribute("aircraft", aircraft);
            return "redirect:/employee";
        }
        else {
            return "home";
        }
    }

}
