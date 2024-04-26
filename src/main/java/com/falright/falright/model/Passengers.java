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
@Table(name = "passengers")
public class Passengers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer passenger_id;
    private String name;
    private String contact_information;
    private Integer user_id;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Users> users;

    @OneToMany
    @JoinColumn(name = "passenger_id")
    private List<Reservations> passengers;
}
