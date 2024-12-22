package tn.iteam.springpersofinance.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Budget {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount")
    private double amount;
    @Column(name = "description")
    private String description;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "category_name")
    private String categoryName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Establishes the relationship with User
    private User user;
}
