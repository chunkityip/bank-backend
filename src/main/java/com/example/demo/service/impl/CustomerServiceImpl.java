package com.example.demo.service.impl;


import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.Customer;
import com.example.demo.entity.BankAccount;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repo.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    // Constructor injection for easier testing
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        // Map the list of account numbers
        List<Long> accountNumbers = customer.getBankAccounts().stream()
                .map(BankAccount::getAccountNumber)
                .collect(Collectors.toList());

        // Return the DTO with the account numbers
        return new CustomerDTO(customer.getId(), customer.getName(), accountNumbers);
    }


    @Override
    public List<CustomerDTO> searchCustomersByName(String keyword) {
        return customerRepository.findCustomerByName(keyword)
                .stream()
                .map(customer -> new CustomerDTO(customer.getId(), customer.getName(),
                        customer.getBankAccounts().stream().map(BankAccount::getAccountNumber).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }


    @Override
    public CustomerDTO createCustomer(String customerName) {
        Customer customer = new Customer();
        customer.setName(customerName);
        customer = customerRepository.save(customer);
        return new CustomerDTO(customer.getId(), customer.getName(), Collections.emptyList());
    }

    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }

}
