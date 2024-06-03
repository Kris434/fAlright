package com.falright.falright.model;

import com.falright.falright.controller.RegisterController;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.groups.Default;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

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
