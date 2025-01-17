package tn.iteam.springpersofinance.dto;

import lombok.Getter;
import lombok.Setter;
import tn.iteam.springpersofinance.enums.TransactionType;

import java.time.LocalDate;
@Getter
@Setter
public class TransactionDto {
    private Long id;
    private TransactionType type; // "income" or "expense"
    private double amount;
    private Long categoryId;
    private LocalDate date;
    private String description;
    private Long sourceAccountId;
    private Long targetAccountId;
    private Long userId;
}
