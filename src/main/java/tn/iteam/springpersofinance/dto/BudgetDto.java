package tn.iteam.springpersofinance.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BudgetDto {
    @NotBlank
    private double amount;
    @NotBlank
    private String description;
    @NotBlank
    private LocalDate startDate;
    @NotBlank
    private LocalDate endDate;
    @NotBlank
    private String category;

}
