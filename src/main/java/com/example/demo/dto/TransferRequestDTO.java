package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO {
    private Long fromAccount;
    private Long fromAccountSortCode;
    private Long toAccount;
    private Long toAccountSortCode;
    private BigDecimal amount;
    private String description;
}
