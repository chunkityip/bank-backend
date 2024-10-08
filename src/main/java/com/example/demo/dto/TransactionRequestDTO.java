package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDTO {
    //private OperationType type; // DEPOSIT, WITHDRAWAL, TRANSFER
    private int type;
    private Long fromAccount;
    private String fromAccountSortCode;
    private Long toAccount;
    private String toAccountSortCode;
    //    private Long senderAccount;
//    private String senderAccountSortCode;
//    private Long recipientAccount;
//    private String recipientAccountSortCode;
    private BigDecimal amount;
//    private String description;
}

