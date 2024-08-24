package com.example.demo.service;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.dto.CreateAccountRequestDTO;
import com.example.demo.exceptions.BankAccountNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;

import java.math.BigDecimal;

public interface BankAccountService {
    BankAccountDTO createAccount(CreateAccountRequestDTO createAccountRequestDTO) throws CustomerNotFoundException;
    BankAccountDTO getAccount(String accountNumber) throws BankAccountNotFoundException;
    BigDecimal closeAccount(String accountNumber) throws BankAccountNotFoundException;
    Long getSortCode();
}
