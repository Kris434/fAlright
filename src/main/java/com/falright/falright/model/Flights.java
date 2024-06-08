package com.falright.falright.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flight_id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departure_time;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrival_time;
    private String destination;
    private String departure_point;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircrafts aircrafts_id;
}
