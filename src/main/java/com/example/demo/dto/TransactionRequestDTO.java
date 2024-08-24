package com.example.demo.dto;

import com.example.demo.enums.OperationType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionRequestDTO {
    private OperationType type; // DEPOSIT, WITHDRAWAL, TRANSFER
    private Long fromAccount;
    private Long fromAccountSortCode;
    private Long toAccount;
    private Long toAccountSortCode;
    private BigDecimal amount;
}
