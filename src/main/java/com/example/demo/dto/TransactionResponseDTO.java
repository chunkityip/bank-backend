package com.example.demo.dto;

import com.example.demo.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * FG: Create TransactionDTO to get transaction info from database : bank
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {
    private Date operationDate;
    private OperationType type;
    private Long fromAccount;
    private String fromAccountSortCode;
    private Long toAccount;
    private String toAccountSortCode;
    private BigDecimal amount;
}

