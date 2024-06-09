package com.falright.falright.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aircraft_id;

    @NotBlank(message = "Nazwa samolotu nie może być pusta!")
    private String model;

    @NotNull(message = "Ilość miejsc nie może być pusta!")
    @Min(value = 1, message = "Ilość miejsc nie może być mniejsza niż jeden!")
    private Integer capacity;
    private Boolean status;

    public Aircrafts(String model, Integer capacity, Boolean status)
    {
        this.model = model;
        this.capacity = capacity;
        this.status = status;
    }
}
