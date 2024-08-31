package com.example.demo.service;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.exception.BankAccountNotFoundException;

import java.util.List;

public interface BankAccountService {
    BankAccountDTO getBankAccountById(Long id) throws BankAccountNotFoundException;
    BankAccountDTO getBankAccountNumber(Long id) throws BankAccountNotFoundException;
    List<BankAccountDTO> getBankAccountsByCustomerId(Long customerId);
    BankAccountDTO createBankAccount(BankAccountDTO bankAccountDTO);
    Double deleteBankAccount(Long accountNumber) throws BankAccountNotFoundException;
}
