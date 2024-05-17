package com.falright.falright.repository;

import com.falright.falright.model.Reservations;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservations, Integer> {
}
