package com.falright.falright.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @NotBlank(message = "Podaj E-mail!")
    @Email(message = "Podaj poprawny adres E-mail!")
    private String email;

    @NotBlank(message = "Podaj nazwę użytkownika!")
    private String username;

    @NotBlank(message = "Podaj hasło!")
    @Length(min = 5, message = "Hasło musi mieć co najmniej 5 znaków!")
    private String password;

    @NotBlank(message = "Powtórz hasło!")
    @Transient
    private String rpassword;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role { USER, EMPLOYEE, ADMIN }

}
