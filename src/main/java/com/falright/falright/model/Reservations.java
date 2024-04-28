package com.falright.falright.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "reservations")
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer reservation_id;
    private Integer flight_id;
    private Integer passenger_id;
    private Integer seat_number;
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flights flights;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passengers passengers;

}
