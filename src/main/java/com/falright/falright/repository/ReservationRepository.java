package com.falright.falright.repository;

import com.falright.falright.model.Flights;
import com.falright.falright.model.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservations, Integer> {
    @Query(value = "SELECT r FROM Reservations r WHERE r.flights_id = ?1")
    List<Reservations> findByFlightId(final Flights flightId);

    @Query(value = "SELECT r FROM Reservations r JOIN Passengers p ON r.passengers_id.passenger_id = p.passenger_id JOIN Users u ON p.users_id.user_id = u.user_id WHERE u.user_id = ?1")
    List<Reservations> findByLoggedUser(final Integer userId);

    @Query(value = "SELECT r FROM Reservations r WHERE r.seat_number = ?1 AND r.flights_id = ?2")
    Reservations findBySeatAndFlight(final Integer seat, final Flights flight);
}
