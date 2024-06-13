package com.falright.falright.service;

import com.falright.falright.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final FlightRepository flightRepository;

    public ReportService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Object[]> getAircraftWithMostFlights() {
        return flightRepository.findAircraftWithMostFlights();
    }

    public List<Object[]> getFlightWithMostReservations() {
        return flightRepository.findFlightWithMostReservations();
    }

    public List<Object[]> getLongestFlight() {
        return flightRepository.findLongestFlight();
    }

    public List<Object[]> getMostExpensiveFlight() {
        return flightRepository.findMostExpensiveFlight();
    }

    public List<Object[]> getFlightWithMostPassengers() {
        return flightRepository.findFlightWithMostPassengers();
    }
}
