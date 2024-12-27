package tn.iteam.springpersofinance.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class TransactionDto {
    private Long id;
    private String type; // "income" or "expense"
    private double amount;
    private Long categoryId;
    private LocalDate date;
    private String description;
    private Long userId;
}
