package com.example.demo.controller;

import com.example.demo.dto.TransactionRecordDTO;
import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.exceptions.BalanceNotSufficientException;
import com.example.demo.exceptions.BankAccountNotFoundException;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionRecordDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        try {
            TransactionRecordDTO transactionRecordDTO = transactionService.createTransaction(transactionRequestDTO);
            return new ResponseEntity<>(transactionRecordDTO, HttpStatus.CREATED);
        } catch (BankAccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BalanceNotSufficientException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionRecordDTO>> getTransactionsForAccount(@PathVariable Long accountId) {
        List<TransactionRecordDTO> transactions = transactionService.getTransactionsForAccount(accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}