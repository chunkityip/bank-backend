package com.example.demo.controller;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200") // change it if you like to use our team ui
@CrossOrigin // change it if you like to use mallon-bank-ui
@RestController
@RequestMapping("/account")
@Validated
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/{id}")
//    public ResponseEntity<BankAccountDTO> getBankAccountById(@PathVariable Long id) {
//        try {
//            BankAccountDTO bankAccountDTO = bankAccountService.  getBankAccountById(id);
//            return new ResponseEntity<>(bankAccountDTO, HttpStatus.OK);
//        } catch (BankAccountNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    public ResponseEntity<BankAccountDTO> getBankAccountNumber(@PathVariable Long id) {
        try {
            BankAccountDTO bankAccountDTO = bankAccountService.getBankAccountNumber(id);
            return new ResponseEntity<>(bankAccountDTO, HttpStatus.OK);
        } catch (BankAccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<BankAccountDTO>> getBankAccountsByCustomerId(@PathVariable Long customerId) {
        List<BankAccountDTO> bankAccounts = bankAccountService.getBankAccountsByCustomerId(customerId);
        return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> createBankAccount(@RequestBody @Valid BankAccountDTO bankAccountDTO) {
        BankAccountDTO createdBankAccount = bankAccountService.createBankAccount(bankAccountDTO);
        return new ResponseEntity<>(createdBankAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Double> deleteBankAccount(@PathVariable Long id) {
        try {
            Double balance = bankAccountService.deleteBankAccount(id);
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (BankAccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}