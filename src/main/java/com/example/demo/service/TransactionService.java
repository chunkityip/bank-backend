package com.example.demo.service;

import com.example.demo.dto.TransactionRecordDTO;
import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.exceptions.BalanceNotSufficientException;
import com.example.demo.exceptions.BankAccountNotFoundException;

import java.util.List;

public interface TransactionService {
    TransactionRecordDTO createTransaction(TransactionRequestDTO transactionRequestDTO)
            throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<TransactionRecordDTO> getTransactionsForAccount(Long accountId);
}