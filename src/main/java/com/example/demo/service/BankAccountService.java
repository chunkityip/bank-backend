package com.example.demo.service;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.dto.CreateAccountRequestDTO;
import com.example.demo.exceptions.BankAccountNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountService {
    BankAccountDTO createAccount(CreateAccountRequestDTO createAccountRequestDTO) throws CustomerNotFoundException;
    BankAccountDTO getAccountById(Long accountId) throws BankAccountNotFoundException;
    List<BankAccountDTO> getAccountsByCustomerId(Long customerId) throws CustomerNotFoundException;
    void deleteAccount(Long accountId) throws BankAccountNotFoundException;
}
