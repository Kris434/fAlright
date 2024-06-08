package com.falright.falright.repository;

import com.falright.falright.model.Aircrafts;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AircraftRepository extends CrudRepository<Aircrafts, Integer> {

}
