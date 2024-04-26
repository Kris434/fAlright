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
@Table(name = "baggage")
public class Baggage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer baggage_id;
    private Integer passenger_id;
    private Boolean status;

    private Size size;
    public enum Size {
        SMALL, MEDIUM, LARGE
    }

    @OneToMany
    @JoinColumn(name = "passenger_id")
    private List<Passengers> passengers;
}
