package com.falright.falright.model;

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
@Table(name = "aircrafts")
public class Aircrafts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer aircraft_id;
    private String model;
    private Integer capacity;
    private Boolean status;

    public Aircrafts(String model, Integer capacity, Boolean status)
    {
        this.model = model;
        this.capacity = capacity;
        this.status = status;
    }
}
