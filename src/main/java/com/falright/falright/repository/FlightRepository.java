package com.falright.falright.repository;

import com.falright.falright.model.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flights, Integer> {
    @Query("SELECT f FROM Flights f WHERE f.departure_point = :departure AND f.destination = :destination " +
            "AND (:departureDateStart IS NULL OR f.departure_time BETWEEN :departureDateStart AND :departureDateEnd) " +
            "AND (:arrivalDateStart IS NULL OR f.arrival_time BETWEEN :arrivalDateStart AND :arrivalDateEnd)")
    List<Flights> findFlightsByCriteria(@Param("departure") String departure,
                                        @Param("destination") String destination,
                                        @Param("departureDateStart") LocalDateTime departureDateStart,
                                        @Param("departureDateEnd") LocalDateTime departureDateEnd,
                                        @Param("arrivalDateStart") LocalDateTime arrivalDateStart,
                                        @Param("arrivalDateEnd") LocalDateTime arrivalDateEnd);

    // Query to get flights by departure and destination if dates are not provided
    @Query("SELECT f FROM Flights f WHERE f.departure_point = :departure AND f.destination = :destination")
    List<Flights> findFlightsByDepartureAndDestination(@Param("departure") String departure,
                                                       @Param("destination") String destination);
}

