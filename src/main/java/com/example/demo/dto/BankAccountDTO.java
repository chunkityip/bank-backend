package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BankAccountDTO {
    private String number;  // Changed from Long to String to match UUID or custom format
    private Long sortCode;
    private String name;
    private BigDecimal balance;
    private Long customerId;
    private List<TransactionRecordDTO> transactions;  // Transaction details
}
