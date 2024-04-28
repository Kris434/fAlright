package com.falright.falright.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "flights")
public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer flight_id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departure_time;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrival_time;
    private String destination;
    private String departure_point;
    //private Integer aircraft_id;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircrafts aircrafts;
}
