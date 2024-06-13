package com.falright.falright.model;

import com.falright.falright.repository.ValidationGroups;
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

    @NotBlank(message = "Podaj E-mail!", groups = ValidationGroups.Register.class)
    @Email(message = "Podaj poprawny adres E-mail!", groups = ValidationGroups.Register.class)
    private String email;

    @NotBlank(message = "Podaj nazwę użytkownika!", groups = ValidationGroups.Register.class)
    private String username;

    @NotBlank(message = "Podaj hasło!", groups = {ValidationGroups.Register.class, ValidationGroups.ChangePassword.class})
    @Length(min = 5, message = "Hasło musi mieć co najmniej 5 znaków!")
    private String password;

    @NotBlank(message = "Powtórz hasło!", groups = ValidationGroups.Register.class)
    @Transient
    private String rpassword;

    @Transient
    @NotBlank(message = "Podaj nowe hasło!", groups = ValidationGroups.ChangePassword.class)
    private String newPassword;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role { USER, EMPLOYEE, ADMIN }

}
