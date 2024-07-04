package com.falright.falright.repository;

import com.falright.falright.model.Aircrafts;
import com.falright.falright.model.Flights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flights, Integer>
{
    @Query("SELECT f FROM Flights f WHERE f.departure_point = :departure AND f.destination = :destination " +
            "AND (:departureDateStart IS NULL OR f.departure_time BETWEEN :departureDateStart AND :departureDateEnd) " +
            "AND (:arrivalDateStart IS NULL OR f.arrival_time BETWEEN :arrivalDateStart AND :arrivalDateEnd)")
    List<Flights> findFlightsByCriteria(@Param("departure") String departure,
                                        @Param("destination") String destination,
                                        @Param("departureDateStart") LocalDateTime departureDateStart,
                                        @Param("departureDateEnd") LocalDateTime departureDateEnd,
                                        @Param("arrivalDateStart") LocalDateTime arrivalDateStart,
                                        @Param("arrivalDateEnd") LocalDateTime arrivalDateEnd);


    @Query("SELECT f FROM Flights f WHERE f.departure_point = :departure AND f.destination = :destination")
    List<Flights> findFlightsByDepartureAndDestination(@Param("departure") String departure,
                                                       @Param("destination") String destination);


    @Query("SELECT a.model, COUNT(f) AS num_flights " +
            "FROM Flights f JOIN f.aircrafts_id a " +
            "GROUP BY a.model ORDER BY num_flights DESC")
    List<Object[]> findAircraftWithMostFlights();

    @Query("SELECT f.departure_point, f.destination, COUNT(r) AS num_reservations " +
            "FROM Flights f JOIN Reservations r ON f.flight_id = r.flights_id.flight_id " +
            "WHERE r.status = true " +
            "GROUP BY f.departure_point, f.destination ORDER BY num_reservations DESC")
    List<Object[]> findFlightWithMostReservations();

    @Query(value = "SELECT f.departure_point, f.destination, TIMESTAMPDIFF(HOUR, f.departure_time, f.arrival_time) AS duration_hours " +
            "FROM flights f ORDER BY duration_hours DESC", nativeQuery = true)
    List<Object[]> findLongestFlight();

    @Query("SELECT f.departure_point, f.destination, f.price " +
            "FROM Flights f ORDER BY f.price DESC")
    List<Object[]> findMostExpensiveFlight();

    @Query("SELECT f.departure_point, f.destination, COUNT(r) AS num_passengers " +
            "FROM Flights f JOIN Reservations r ON f.flight_id = r.flights_id.flight_id " +
            "GROUP BY f.departure_point, f.destination ORDER BY num_passengers DESC")
    List<Object[]> findFlightWithMostPassengers();

    @Query("Select f from Flights f WHERE f.aircrafts_id = :aircraft")
    List<Flights> findFlightsByAircraft(@Param("aircraft") Aircrafts aircraft);

}

