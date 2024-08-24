package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDTO {
    private Long fromAccount;
    private Long fromAccountSortCode;
    private Long toAccount;
    private Long toAccountSortCode;
    private BigDecimal amount;
    private String description;
}
