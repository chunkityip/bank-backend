package com.example.demo.service;

import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.entity.TransactionRecord;
import com.example.demo.enums.OperationType;
import com.example.demo.exception.BankAccountNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    List<TransactionResponseDTO> getTransactionsForAccount(Long accountId);

//    TransactionRecordDTO getTransactionById(Long id) throws TransactionNotFoundException;

//    TransactionRequestDTO createTransaction(TransactionRecord transactionRecord);
//    List<TransactionResponseDTO> getTransactionsbyType(Long accountId, OperationType operationType);

    TransactionResponseDTO depositTransaction ( Long toAccount, String toAccountSortCode, BigDecimal amount) throws BankAccountNotFoundException;

    TransactionResponseDTO withdrawTransaction( Long fromAccount, String fromAccountSortCode, BigDecimal amount) throws BankAccountNotFoundException;

    TransactionResponseDTO transferTransaction(
            Long fromAccount,
            String fromAccountSortCode,
            Long toAccount,
            String toAccountSortCode,
            BigDecimal amount) throws BankAccountNotFoundException;
}
