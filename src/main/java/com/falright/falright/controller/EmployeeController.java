package com.falright.falright.controller;

import com.falright.falright.model.Aircrafts;
import com.falright.falright.model.Flights;
import com.falright.falright.model.Reservations;
import com.falright.falright.model.Users;
import com.falright.falright.repository.AircraftRepository;
import com.falright.falright.repository.FlightRepository;
import com.falright.falright.repository.ReservationRepository;
import com.falright.falright.service.ReportService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {
    private final AircraftRepository aircraftRepository;
    private final FlightRepository flightRepository;
    private final ReservationRepository reservationRepository;
    private final ReportService reportService;

    public EmployeeController(AircraftRepository aircraftRepository, FlightRepository flightRepository,
                              ReservationRepository reservationRepository, ReportService reportService) {

        this.aircraftRepository = aircraftRepository;
        this.flightRepository = flightRepository;
        this.reservationRepository = reservationRepository;
        this.reportService = reportService;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee")
    public String employeePage(HttpSession session, Model model) {

        Users user = (Users) session.getAttribute("loggedInUser");


        if(user != null && user.getRole() == Users.Role.EMPLOYEE) {
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
    @GetMapping("/employee/addAircraft")
    public String showAddAircraftForm(Model model) {

        model.addAttribute("aircraft", new Aircrafts());
        return "addAircraft";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/addAircraft")
    public String addAircraft(@Valid @ModelAttribute("aircraft") Aircrafts aircraft,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes,
                              HttpSession session, Model model) {

        Users user = (Users) session.getAttribute("loggedInUser");

        if(user != null && user.getRole() == Users.Role.EMPLOYEE)
        {
            if (bindingResult.hasErrors()) {
                model.addAttribute("aircraft", aircraft);
                return "addAircraft";
            }


            aircraft.setStatus(true);
            aircraftRepository.save(aircraft);

            List<Aircrafts> aircraftsList = (List<Aircrafts>) aircraftRepository.findAll();
            session.setAttribute("aircrafts", aircraftsList);
            redirectAttributes.addFlashAttribute("message", "Samolot został dodany pomyślnie!");
            return "redirect:/employee";
        }
        else {
            return "home";
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/addFlight")
    public String showAddFlightForm(Model model, HttpSession session) {

        List<Aircrafts> aircraftsList = (List<Aircrafts>) aircraftRepository.findAll();
        session.setAttribute("aircrafts", aircraftsList);
        model.addAttribute("aircraft", new Aircrafts());
        model.addAttribute("flight", new Flights());
        return "addFlight";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/addFlight")
    public String addFlight(@Valid @ModelAttribute("flight") Flights flight, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            HttpSession session, Model model,
                            @RequestParam("aircraftId") Aircrafts aircraft) {

        Users user = (Users) session.getAttribute("loggedInUser");

        if(user != null && user.getRole() == Users.Role.EMPLOYEE) {

            if(flight.getDeparture_time() != null && flight.getArrival_time() != null) {

                LocalDateTime now = LocalDateTime.now();

                if(flight.getDeparture_time().isAfter(flight.getArrival_time())) {
                    bindingResult.addError(new FieldError("flight", "departure_time", "Data wylotu musi być przed datą przylotu!"));
                }

                if(flight.getDeparture_time().isBefore(now)) {
                    bindingResult.addError(new FieldError("flight", "departure_time", "Data wylotu musi być w przyszłości!"));
                }
                if(flight.getArrival_time().isBefore(now)) {
                    bindingResult.addError(new FieldError("flight", "arrival_time", "Data przylotu musi być w przyszłości!"));
                }
            }

            if (!flight.getDeparture_point().isEmpty() && !flight.getDestination().isEmpty()) {
                if (flight.getDeparture_point().equals(flight.getDestination())) {
                    bindingResult.addError(new FieldError("flight", "destination", "Miejsce wylotu nie może być takie samo jak miejsce docelowe!"));
                }
            }

            if (bindingResult.hasErrors()) {
                model.addAttribute("flight", flight);
                model.addAttribute("aircraft", aircraft.getAircraft_id());
                return "addFlight";
            }

            flight.setAircrafts_id(aircraft);
            flight.setStatus(true);
            flightRepository.save(flight);

            List<Reservations> reservationList = new ArrayList<>();

            for(int i = 0; i < aircraft.getCapacity(); i++) {
                Reservations r = new Reservations();

                r.setSeat_number(i + 1);
                r.setFlights_id(flight);
                r.setStatus(false);

                reservationList.add(r);
                reservationRepository.save(r);
            }

            model.addAttribute("aircraft", aircraft);
            redirectAttributes.addFlashAttribute("message", "Lot został dodany pomyślnie!");
            return "redirect:/employee";
        }
        else {
            return "home";
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/report")
    public String generateReport(Model model) {

        model.addAttribute("aircraftWithMostFlights", reportService.getAircraftWithMostFlights());
        model.addAttribute("flightWithMostReservations", reportService.getFlightWithMostReservations());
        model.addAttribute("longestFlight", reportService.getLongestFlight());
        model.addAttribute("mostExpensiveFlight", reportService.getMostExpensiveFlight());
        model.addAttribute("flightWithMostPassengers", reportService.getFlightWithMostPassengers());

        return "report";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/downloadReport")
    public ResponseEntity<InputStreamResource> downloadReport() throws IOException {

        ByteArrayInputStream excelStream = reportService.generateExcelReport();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(excelStream));
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/employee/showFlights")
    public String showFlights(Model model) {

        model.addAttribute("flights", flightRepository.findAll());
        return "showFlight";
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/employee/deleteFlight")
    public String deleteFlight(@RequestParam("id") Integer id, RedirectAttributes redirectAttributes) {

        Flights flight = flightRepository.findById(id).orElse(null);

        if(flight != null) {
            flight.setStatus(false);
            flightRepository.save(flight);
            redirectAttributes.addFlashAttribute("message", "Lot został usunięty pomyślnie!");
        }

        return "redirect:/employee";
    }
}
