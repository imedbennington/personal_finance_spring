package tn.iteam.springpersofinance.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long RIB;
    @Column(nullable = false)
    private String IBAN;
    @Column(nullable = false)
    private String bank_name;
    @Column(nullable = false)
    private double balance;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Establishes the relationship with User
    private User user;
}
