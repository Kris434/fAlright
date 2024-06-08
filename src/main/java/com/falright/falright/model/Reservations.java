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
@Table(name = "reservations")
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservation_id;


    private Integer seat_number;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flights flights_id;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passengers passengers_id;

}
