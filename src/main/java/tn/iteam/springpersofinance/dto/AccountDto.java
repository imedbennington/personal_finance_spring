package tn.iteam.springpersofinance.dto;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import tn.iteam.springpersofinance.entities.User;

@Getter
@Setter
public class AccountDto {
    @NotBlank
    private Long id;
    @NotBlank
    private Long RIB;
    @NotBlank
    private String IBAN;
    @NotBlank
    private String bank_name;
    @NotBlank
    private double balance;
    private User user;
}
