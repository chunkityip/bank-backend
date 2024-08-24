package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BankAccountDTO {
    private String number;  // Changed from Long to String
    private Long sortCode;
    private String name;
    // private BigDecimal openingBalance; // Since it will only show during account creation so not needed in DTO?
    private BigDecimal balance;  // Removed openingBalance for consistency
    private Long customerId;
    private List<TransactionRecordDTO> transactions;
}
