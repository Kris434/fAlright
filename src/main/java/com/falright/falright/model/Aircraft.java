package com.falright.falright.model;

import com.falright.falright.repository.AircraftRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "aircraft")
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer aircraft_id;
    private String model;
    private Integer capacity;
    private Boolean status;

    public Aircraft(String model, Integer capacity, Boolean status)
    {
        this.model = model;
        this.capacity = capacity;
        this.status = status;
    }
}
