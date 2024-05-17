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
@Table(name = "passengers")
public class Passengers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer passenger_id;
    private String name;
    private String surname;
    private Integer phone_number;
    private String email;
    private String address;
    private String city;
    private String post_code;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users_id;


}
