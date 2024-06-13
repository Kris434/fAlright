package com.falright.falright.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

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

    @NotNull(message = "Data wylotu nie może być pusta!")
    @Future(message = "Data wylotu musi być w przyszłości!")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departure_time;

    @NotNull(message = "Data przylotu nie może być pusta!")
    @Future(message = "Data przylotu musi być w przyszłości!")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrival_time;

    @NotBlank(message = "Pole miejsca przylotu nie może być puste!")
    private String destination;

    @NotBlank(message = "Pole miejsce wylotu nie może być puste!")
    private String departure_point;

    @NotNull(message = "Cena nie może być pusta")
    @Positive(message = "Cena musi być większa od zera")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircrafts aircrafts_id;

}
