package com.example.demo.dto;

import com.example.demo.enums.OperationType;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TransactionRecordDTO {
    private Long id;
    private Date operationDate;
    private BigDecimal amount;
    private OperationType type;
    private String description;
    // Add these fields:
    private Long fromAccount;
    private Long fromAccountSortCode;
    private Long toAccount;
    private Long toAccountSortCode;
}
