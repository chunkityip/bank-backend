package com.example.demo.service.impl;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.dto.CreateAccountRequestDTO;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Customer;
import com.example.demo.enums.BankType;
import com.example.demo.exceptions.BankAccountNotFoundException;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public BankAccountDTO createAccount(CreateAccountRequestDTO createAccountRequestDTO) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(createAccountRequestDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        BankAccount bankAccount = new BankAccount();
        bankAccount.setCustomer(customer);
        bankAccount.setName(createAccountRequestDTO.getAccountName());
        bankAccount.setOpeningBalance(createAccountRequestDTO.getOpeningBalance());
        bankAccount.setBalance(createAccountRequestDTO.getOpeningBalance());
        bankAccount.setBankType(BankType.CITI);  // Assuming a default BankType

        bankAccount = bankAccountRepository.save(bankAccount);
        return new BankAccountDTO(bankAccount.getAccountNumber(), bankAccount.getSortCode(), bankAccount.getName(),
                bankAccount.getBalance(), bankAccount.getCustomer().getId(), Collections.emptyList());
    }

    @Override
    public BankAccountDTO getAccountById(Long accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        return new BankAccountDTO(bankAccount.getAccountNumber(), bankAccount.getSortCode(), bankAccount.getName(),
                bankAccount.getBalance(), bankAccount.getCustomer().getId(), Collections.emptyList());
    }

    @Override
    public List<BankAccountDTO> getAccountsByCustomerId(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return customer.getBankAccounts().stream()
                .map(bankAccount -> new BankAccountDTO(bankAccount.getAccountNumber(), bankAccount.getSortCode(),
                        bankAccount.getName(), bankAccount.getBalance(), customer.getId(), Collections.emptyList()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        bankAccountRepository.delete(bankAccount);
    }
}
