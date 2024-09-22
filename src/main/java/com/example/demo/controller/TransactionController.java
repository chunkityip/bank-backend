package com.example.demo.controller;

import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.entity.TransactionRecord;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transaction")
//@CrossOrigin(origins = "http://localhost:4200") // change it if you like to use our team ui
@CrossOrigin // change it if you like to use mallon-bank-ui
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping(produces = "application/json")
    public ResponseEntity<TransactionResponseDTO> processTransaction(@RequestBody TransactionRequestDTO request) throws BankAccountNotFoundException {
        TransactionResponseDTO response;
        switch (request.getType()) {
            case 2 -> response = transactionService.depositTransaction(
                    request.getToAccount(),
                    request.getToAccountSortCode(),
                    request.getAmount());

            case 1 -> response = transactionService.withdrawTransaction(
                    request.getFromAccount(),
                    request.getFromAccountSortCode(),
                    request.getAmount());
            case 0 -> response = transactionService.transferTransaction(
                    request.getFromAccount(),
                    request.getFromAccountSortCode(),
                    request.getToAccount(),
                    request.getToAccountSortCode(),
                    request.getAmount());
            default -> response = null;
        }
        return ResponseEntity.ok(response);
    }
}
