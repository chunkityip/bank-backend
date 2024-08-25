package com.example.demo.service.impl;


import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.RegisterCustomerDTO;
import com.example.demo.entity.Customer;
import com.example.demo.exceptions.CustomerNotFoundException;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDTO getCustomerById(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(),
                customer.getBankAccounts().stream().map(BankAccount::getAccountNumber).collect(Collectors.toList()));
    }

    @Override
    public List<CustomerDTO> searchCustomersByName(String keyword) {
        return customerRepository.findByNameContaining(keyword)
                .stream()
                .map(customer -> new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(),
                        customer.getBankAccounts().stream().map(BankAccount::getAccountNumber).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO createCustomer(RegisterCustomerDTO registerCustomerDTO) {
        Customer customer = new Customer();
        customer.setName(registerCustomerDTO.getName());
        customer.setEmail(registerCustomerDTO.getEmail());
        customer.setPassword(registerCustomerDTO.getPassword());
        customer.setCustomerIdentificationNumber(UUID.randomUUID().toString());
        customer = customerRepository.save(customer);
        return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(), Collections.emptyList());
    }

    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }
}
