package com.falright.falright.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = "reports")
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer report_id;
    private Integer user_id;
    private String report_content;
    private Timestamp created_at;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Users> users;
}
