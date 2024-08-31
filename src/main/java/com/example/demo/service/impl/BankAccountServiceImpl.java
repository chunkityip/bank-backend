package com.example.demo.service.impl;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Customer;
import com.example.demo.enums.BankType;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.repo.BankAccountRepository;
import com.example.demo.repo.CustomerRepository;
import com.example.demo.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public BankAccountDTO getBankAccountById(Long id) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(id)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        return mapToDTO(bankAccount);
    }

    @Override
    public BankAccountDTO getBankAccountNumber(Long id) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(id)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        return mapToDTO(bankAccount);
    }

    @Override
    public List<BankAccountDTO> getBankAccountsByCustomerId(Long customerId) {
        return bankAccountRepository.findByCustomerId(customerId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO createBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new BankAccount();


        Customer customer = customerRepository.findById(bankAccountDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        bankAccount.setCustomer(customer);


        bankAccount.setName(bankAccountDTO.getAccountName());
        bankAccount.setOpeningBalance(bankAccountDTO.getOpeningBalance());
        bankAccount.setBalance(bankAccountDTO.getOpeningBalance()); // Assuming current balance starts as the opening balance
        bankAccount.setBankType(BankType.DEFAULT);

        //test test test
        System.out.println("Saving bank account: " + bankAccount);


        bankAccount = bankAccountRepository.save(bankAccount);

        return mapToDTO(bankAccount);
    }
    @Override
    public Double deleteBankAccount(Long accountNumber) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found"));
        Double balance = bankAccount.getBalance().doubleValue();
        bankAccountRepository.delete(bankAccount);
        return balance;

    }

    private BankAccountDTO mapToDTO(BankAccount bankAccount) {
        return new BankAccountDTO(
                bankAccount.getCustomer().getId(),
                bankAccount.getName(),
                bankAccount.getBalance(),
                bankAccount.getAccountNumber(),
                bankAccount.getSortCode()

        );
    }
}
