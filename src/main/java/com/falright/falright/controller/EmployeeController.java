package com.falright.falright.controller;

import com.falright.falright.model.Aircrafts;
import com.falright.falright.model.Users;
import com.falright.falright.repository.AircraftRepository;
import com.falright.falright.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final UserRepository userRepository;
    private final AircraftRepository aircraftRepository;

    public EmployeeController(UserRepository userRepository, AircraftRepository aircraftRepository)
    {
        this.userRepository = userRepository;
        this.aircraftRepository = aircraftRepository;
    }

    @GetMapping("")
    public String employeePage(HttpSession session, Model model)
    {
        Users user = (Users) session.getAttribute("loggedInUser");

        if(user != null && user.getRole() == Users.Role.ADMIN)
        {
            List<Aircrafts> aircraftsList = (List<Aircrafts>) aircraftRepository.findAll();

            session.setAttribute("aircrafts", aircraftsList);

            return "employee";
        }
        else
        {
            return "home";
        }
    }

    @PostMapping("/addAircraft")
    public String addAircraft(HttpSession session, Model model, @RequestParam("aircraftName") String name, @RequestParam("capacity") Integer capacity)
    {
        Users user = (Users) session.getAttribute("loggedInUser");

        if(user != null && user.getRole() == Users.Role.ADMIN)
        {
            Aircrafts aircraft = new Aircrafts();

            aircraft.setModel(name);
            aircraft.setCapacity(capacity);
            aircraft.setStatus(true);

            aircraftRepository.save(aircraft);

            List<Aircrafts> aircraftsList = (List<Aircrafts>) aircraftRepository.findAll();

            session.setAttribute("aircrafts", aircraftsList);

            return "employee";
        }
        else
        {
            return "home";
        }
    }
}
