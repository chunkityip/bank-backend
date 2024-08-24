package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateAccountRequestDTO {
    private Long customerId;
    private String accountName;
    private BigDecimal openingBalance;
}
