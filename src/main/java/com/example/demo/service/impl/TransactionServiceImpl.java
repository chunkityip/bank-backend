package com.example.demo.service.impl;

import com.example.demo.dto.TransactionRecordDTO;
import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.TransactionRecord;
import com.example.demo.enums.OperationType;
import com.example.demo.exceptions.BalanceNotSufficientException;
import com.example.demo.exceptions.BankAccountNotFoundException;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.TransactionRecordRepository;
import com.example.demo.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    @Transactional
    public TransactionRecordDTO createTransaction(TransactionRequestDTO transactionRequestDTO)
            throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount fromAccount = bankAccountRepository.findById(transactionRequestDTO.getFromAccount())
                .orElseThrow(() -> new BankAccountNotFoundException("From bank account not found"));

        BankAccount toAccount = null;
        if (transactionRequestDTO.getToAccount() != null) {
            toAccount = bankAccountRepository.findById(transactionRequestDTO.getToAccount())
                    .orElseThrow(() -> new BankAccountNotFoundException("To bank account not found"));
        }

        BigDecimal amount = transactionRequestDTO.getAmount();

        if (transactionRequestDTO.getType() == OperationType.TRANSFER || transactionRequestDTO.getType() == OperationType.WITHDRAWAL) {
            if (fromAccount.getBalance().compareTo(amount) < 0) {
                throw new BalanceNotSufficientException("Insufficient balance");
            }
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            bankAccountRepository.save(fromAccount);
        }

        if (transactionRequestDTO.getType() == OperationType.TRANSFER || transactionRequestDTO.getType() == OperationType.DEPOSIT) {
            toAccount.setBalance(toAccount.getBalance().add(amount));
            bankAccountRepository.save(toAccount);
        }

        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setType(transactionRequestDTO.getType());
        transactionRecord.setBankAccount(fromAccount);  // Set the associated account
        transactionRecord.setDescription(transactionRequestDTO.getDescription());
        transactionRecord.setFromAccount(transactionRequestDTO.getFromAccount());
        transactionRecord.setFromAccountSortCode(transactionRequestDTO.getFromAccountSortCode());
        transactionRecord.setToAccount(transactionRequestDTO.getToAccount());
        transactionRecord.setToAccountSortCode(transactionRequestDTO.getToAccountSortCode());
        transactionRecord.setAmount(amount);

        transactionRecord = transactionRecordRepository.save(transactionRecord);

        return convertToDTO(transactionRecord);
    }

    @Override
    public List<TransactionRecordDTO> getTransactionsForAccount(Long accountId) {
        return transactionRecordRepository.findByBankAccountId(accountId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TransactionRecordDTO convertToDTO(TransactionRecord transactionRecord) {
        return new TransactionRecordDTO(
                transactionRecord.getId(),
                transactionRecord.getTransactionDate(),
                transactionRecord.getAmount(),
                transactionRecord.getType(),
                transactionRecord.getDescription(),
                transactionRecord.getFromAccount(),
                transactionRecord.getFromAccountSortCode(),
                transactionRecord.getToAccount(),
                transactionRecord.getToAccountSortCode()
        );
    }
}