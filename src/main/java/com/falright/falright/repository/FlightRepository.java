package com.falright.falright.repository;

import com.falright.falright.model.Flights;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flights, Integer> {
}
