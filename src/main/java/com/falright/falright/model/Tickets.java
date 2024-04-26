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
@Entity
@Table(name = "tickets")
public class Tickets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ticket_id;
    private Integer flight_id;
    private String passenger_name;
    private Integer user_id;

    @OneToMany
    @JoinColumn(name = "flight_id")
    private List<Flights> flights;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Users> users;

}
