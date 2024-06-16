package com.falright.falright.config;

import com.falright.falright.model.Flights;
import com.falright.falright.repository.FlightRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
public class FlightStatusUpdate {
    private final FlightRepository flightRepository;

    public FlightStatusUpdate(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Scheduled(cron = "0 0/30 * * * *")
    public void updateFlightsStatus() {
        List<Flights> flights = flightRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Flights flight : flights) {
            if (flight.getDeparture_time().isBefore(now) && flight.getArrival_time().isAfter(now)) {
                flight.setStatus(true);
            } else {
                flight.setStatus(false);
            }
            flightRepository.save(flight);
        }
    }
}
