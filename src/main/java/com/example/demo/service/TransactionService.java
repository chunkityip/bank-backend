package com.example.demo.service;

import com.example.demo.dto.TransactionRecordDTO;
import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.exceptions.BalanceNotSufficientException;
import com.example.demo.exceptions.BankAccountNotFoundException;

public interface TransactionService {
    TransactionRecordDTO processTransaction(TransactionRequestDTO transactionRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException;
}
