package com.falright.falright.repository;

import com.falright.falright.model.Aircrafts;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends CrudRepository<Aircrafts, Integer> {

}
