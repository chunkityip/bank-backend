package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {
    private Long customerId;
    private String accountName;
    private String name;
    private BigDecimal openingBalance;
    private BigDecimal balance;
    private Long number;
    private String sortCode;

    public BankAccountDTO(Long customerId, String accountName, BigDecimal balance, Long number, String sortCode) {
        this.customerId = customerId;
        this.accountName = accountName;
        this.name = accountName;
        this.openingBalance = balance;
        this.balance = balance;
        this.number = number;
        this.sortCode = sortCode;

    }
}