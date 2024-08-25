package com.example.demo.controller;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.dto.CreateAccountRequestDTO;
import com.example.demo.exceptions.BankAccountNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping
    public ResponseEntity<BankAccountDTO> createAccount(@RequestBody CreateAccountRequestDTO createAccountRequestDTO) {
        try {
            BankAccountDTO createdAccount = bankAccountService.createAccount(createAccountRequestDTO);
            return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<BankAccountDTO> getAccountById(@PathVariable Long accountId) {
        try {
            BankAccountDTO accountDTO = bankAccountService.getAccountById(accountId);
            return new ResponseEntity<>(accountDTO, HttpStatus.OK);
        } catch (BankAccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<BankAccountDTO>> getAccountsByCustomerId(@PathVariable Long customerId) {
        try {
            List<BankAccountDTO> accounts = bankAccountService.getAccountsByCustomerId(customerId);
            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        try {
            bankAccountService.deleteAccount(accountId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BankAccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
