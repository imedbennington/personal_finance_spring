package tn.iteam.springpersofinance.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "username", unique = true )
    private String username;
    @Column(nullable = false, name = "firstname")
    private String firstName;
    @Column(nullable = false, name = "lastname")
    private String lastName;
    @Column(nullable = false, name = "email", unique = true)
    private String email;
    @Column(nullable = false, name = "phone", unique = true)
    private String phoneNumber;
    @Column(nullable = false, name = "zip_code")
    private int zip_code;
    @Column(nullable = false, name = "country")
    private String Country;
    @Column(nullable = false, name = "city")
    private String City;
    @Column(nullable = false, name = "address")
    private String Address;
    @Column(nullable = false, name = "date_of_birth")
    private LocalDate Date_of_birth;
    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "profile_picture", nullable = true)
    private String profile_picture;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> budgets = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();
}
