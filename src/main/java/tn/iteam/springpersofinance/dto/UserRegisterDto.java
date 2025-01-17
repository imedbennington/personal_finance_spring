package tn.iteam.springpersofinance.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
public class UserRegisterDto {

    @NotBlank
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private int zipCode;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private String address;

    @NotNull
    private LocalDate dateOfBirth;

    private MultipartFile profilePicture;
}
