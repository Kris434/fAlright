package com.falright.falright.controller;

import com.falright.falright.DTO.ReservationForm;
import com.falright.falright.model.Flights;
import com.falright.falright.model.Passengers;
import com.falright.falright.model.Reservations;
import com.falright.falright.model.Users;
import com.falright.falright.repository.FlightRepository;
import com.falright.falright.repository.PassengersRepository;
import com.falright.falright.repository.ReservationRepository;
import com.falright.falright.service.EmailServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ReservationController {

    @Autowired
    private EmailServiceImpl emailService;
    private final FlightRepository flightsRepository;
    private final PassengersRepository passengersRepository;
    private final ReservationRepository reservationRepository;

    public ReservationController(FlightRepository flightsRepository, PassengersRepository passengersRepository, ReservationRepository reservationRepository) {
        this.flightsRepository = flightsRepository;
        this.passengersRepository = passengersRepository;
        this.reservationRepository = reservationRepository;
    }

    @PostMapping("/reservation")
    public String showReservationForm(Model model, HttpSession session, @RequestParam("flightId") Integer flightId) {

        if (session.getAttribute("loggedInUser") != null) {
            Reservations reservations = new Reservations();
            Optional<Flights> optionalFlights = flightsRepository.findById(flightId);
            Flights flights = optionalFlights.get();

            reservations.setFlights_id(flights);

            session.setAttribute("reservation", reservations);
            session.setAttribute("flightPrice", flights.getPrice());

            model.addAttribute("form", new ReservationForm());
            return "reservation-form";
        }
        else {
            return "home";
        }
    }

    @PostMapping("/personal-info")
    public String addReservation(Model model, HttpSession session,
                                 @Valid @ModelAttribute("form") ReservationForm form,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (StringUtils.hasText(form.getPhoneNumber())) {
            if (form.getPhoneNumber().length() != 9) {
                bindingResult.addError(new FieldError("phoneNumber", "phoneNumber", "Numer telefonu musi składać się z 9 cyfr"));
            }
        }

        if (StringUtils.hasText(form.getPostCode())) {
            if (!form.getPostCode().matches("\\d{2}-\\d{3}")) {
                bindingResult.rejectValue("postCode", "postCode", "Kod pocztowy musi mieć format XX-XXX");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("form", form);
            return "reservation-form";
        }


        Reservations reservation = (Reservations) session.getAttribute("reservation");
        Double flightPrice = (Double) session.getAttribute("flightPrice");
        Passengers passenger = new Passengers();
        Users user = (Users) session.getAttribute("loggedInUser");

        List<Reservations> list = reservationRepository.findByFlightId(reservation.getFlights_id());
        model.addAttribute("seats", list);

        passenger.setName(form.getFirstName());
        passenger.setSurname(form.getLastName());
        passenger.setPhone_number(Integer.valueOf(form.getPhoneNumber()));
        passenger.setUsers_id(user);
        passenger.setAddress(form.getAddress());
        passenger.setCity(form.getCity());
        passenger.setEmail(form.getEmail());

        switch (form.getBaggage()) {
            case "PODRECZNY":
                session.setAttribute("totalPrice", flightPrice + 40.0);
                break;
            case "ZWYKLY":
                session.setAttribute("totalPrice", flightPrice + 80.0);
                break;
            default:
                session.setAttribute("totalPrice", flightPrice);
                break;
        }

        reservation.setPassengers_id(passenger);
        reservation.setBaggage(Reservations.Baggage.valueOf(form.getBaggage()));

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
        reservation.setBaggage(reservationSession.getBaggage());
        reservation.setTotalPrice((Double) session.getAttribute("totalPrice"));
        reservationRepository.save(reservation);

        String messageContent = "Twoja rezerwacja przebiegła pomyślnie! \n" +
                "Lot: " + reservation.getFlights_id().getDeparture_point() + " -> " + reservation.getFlights_id().getDestination() + "\n" +
                "Miejsce: " + seat + "\n" +
                "Data: " + reservation.getFlights_id().getDeparture_time() + "\n" +
                "Cena: " + reservation.getTotalPrice() + "zł" + "\n" +
                "Bagaż: " + reservation.getBaggage() + "\n";

        emailService.sendEmail(passenger.getEmail(), "Rezerwacja przebiegła pomyślnie!", messageContent);

        model.addAttribute("userReservation", reservation);

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
