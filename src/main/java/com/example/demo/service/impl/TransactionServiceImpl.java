package com.example.demo.service.impl;

import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.TransactionRecord;
import com.example.demo.enums.OperationType;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.repo.BankAccountRepository;
import com.example.demo.repo.CustomerRepository;
import com.example.demo.repo.TransactionRepository;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.enums.OperationType.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public TransactionResponseDTO depositTransaction (Long toAccount,
                                                      String toAccountSortCode,
                                                      BigDecimal amount) throws BankAccountNotFoundException {
        //find the bank account

//        BankAccount bankAccount = bankAccountRepository.findById(toAccount).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));

        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(toAccount).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
//        System.out.println(bankAccount.getAccountNumber());

        //update it's balance
        BigDecimal tempAmt = bankAccount.getBalance().add(amount);
        bankAccount.setBalance(tempAmt);

        //update the bank account's amount in the database
        bankAccountRepository.save(bankAccount);
        // create the new transaction
        TransactionRecord transaction = new TransactionRecord();
        transaction.setToAccount(toAccount);
        transaction.setToAccountSortCode(toAccountSortCode);
        transaction.setAmount(amount);
        transaction.setType(DEPOSIT);
        transaction.setBankAccount(bankAccount);

        //add the transaction to the transaction table
        transactionRepository.save(transaction);

        //return the transaction DTO back to the controller
        TransactionResponseDTO transactionResponse = converToDTO(transaction);
        return transactionResponse;

    };

    @Override
    public TransactionResponseDTO withdrawTransaction( Long fromAccount,
                                                       String fromAccountSortCode, BigDecimal amount) throws BankAccountNotFoundException {
        //find the bank account
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(fromAccount).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        //update it's balance
        BigDecimal tempAmt = bankAccount.getBalance().subtract(amount);
        bankAccount.setBalance(tempAmt);

        //update the bank account's amount in the database
        bankAccountRepository.save(bankAccount);
        // create the new transaction
        TransactionRecord transaction = new TransactionRecord();
        transaction.setFromAccount(fromAccount);
        transaction.setFromAccountSortCode(fromAccountSortCode);
        transaction.setAmount(amount);
        transaction.setType(WITHDRAWAL);
        transaction.setBankAccount(bankAccount);

        //add the transaction to the transaction table
        transactionRepository.save(transaction);

        //return the transaction DTO back to the controller
        TransactionResponseDTO transactionResponse = converToDTO(transaction);
        return transactionResponse;
    }

    @Override
    public TransactionResponseDTO transferTransaction(Long fromAccount,
                                                      String fromAccountSortCode,
                                                      Long toAccount,
                                                      String toAccountSortCode,
                                                      BigDecimal amount) throws BankAccountNotFoundException {
        //find bank accounts
        //sender bank account
        BankAccount fromBankAccount = bankAccountRepository.findByAccountNumber(fromAccount).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));

        //recipient bank account
        BankAccount toBankAccount = bankAccountRepository.findByAccountNumber(toAccount).orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));


        //update balances
        //recipient
        BigDecimal tempAmt = toBankAccount.getBalance().add(amount);
        toBankAccount.setBalance(tempAmt);

        //sender
        tempAmt = fromBankAccount.getBalance().subtract(amount);
        fromBankAccount.setBalance(tempAmt);


        //update the bank account's amount in the database
        bankAccountRepository.save(toBankAccount);
        bankAccountRepository.save(fromBankAccount);

        // create the new transaction
        TransactionRecord transaction = new TransactionRecord();
        transaction.setToAccount(toAccount);
        transaction.setToAccountSortCode(toAccountSortCode);
        transaction.setFromAccount(fromAccount);
        transaction.setFromAccountSortCode(fromAccountSortCode);
        transaction.setAmount(amount);
        transaction.setType(TRANSFER);
        transaction.setBankAccount(fromBankAccount);

        //add the transaction to the transaction table
        transactionRepository.save(transaction);

        //return the transaction DTO back to the controller
        TransactionResponseDTO transactionResponse = converToDTO(transaction);
        return transactionResponse;
    }


    @Override
    public List<TransactionResponseDTO> getTransactionsForAccount(Long accountId) {
        return transactionRepository.findByBankAccountId(accountId).stream()
                .map(this::converToDTO)
                .collect(Collectors.toList());
    }



    private TransactionResponseDTO converToDTO(TransactionRecord transactionRecord) {
        return new TransactionResponseDTO(
                transactionRecord.getTransactionDate(),
                transactionRecord.getType(),
                transactionRecord.getFromAccount(),
                transactionRecord.getFromAccountSortCode(),
                transactionRecord.getToAccount(),
                transactionRecord.getToAccountSortCode(),
                transactionRecord.getAmount()
        );
    }
}
