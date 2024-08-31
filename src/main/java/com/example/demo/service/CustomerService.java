package com.example.demo.service;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.RegisterCustomerDTO;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.exception.CustomerNotFoundException;

import java.util.List;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.RegisterCustomerDTO;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    CustomerDTO getCustomerById(Long id) throws CustomerNotFoundException;
    List<CustomerDTO> searchCustomersByName(String customerName);
    CustomerDTO createCustomer(String customerName);
    void deleteCustomer(Long id) throws CustomerNotFoundException;

    interface BankAccountService {
        BankAccountDTO getBankAccountById(Long id) throws BankAccountNotFoundException;
        List<BankAccountDTO> getBankAccountsByCustomerId(Long customerId);
        BankAccountDTO createBankAccount(BankAccountDTO bankAccountDTO);
        void deleteBankAccount(Long id) throws BankAccountNotFoundException;
    }
}
