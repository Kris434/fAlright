package com.falright.falright.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReservationForm {

    @NotBlank(message = "Imię nie może być puste!")
    private String firstName;

    @NotBlank(message = "Nazwisko nie może być puste!")
    private String lastName;

    @NotBlank(message = "Numer telefonu nie może być pusty!")
    private String phoneNumber;

    @NotBlank(message = "Email nie może być pusty!")
    @Email(message = "Podaj poprawny adres E-mail!")
    private String email;

    @NotBlank(message = "Miasto nie może być puste!")
    private String city;

    @NotBlank(message = "Kod pocztowy nie może być pusty!")
    private String postCode;

    @NotBlank(message = "Adres nie może być pusty!")
    private String address;
    private String baggage;

}
