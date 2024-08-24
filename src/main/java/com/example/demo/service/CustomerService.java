package com.example.demo.service;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.RegisterCustomerDTO;
import com.example.demo.exceptions.CustomerNotFoundException;

import java.math.BigDecimal;

public interface CustomerService {
    CustomerDTO registerCustomer(RegisterCustomerDTO registerCustomerDTO);
    CustomerDTO getCustomer(Long customerNumber) throws CustomerNotFoundException;
    BigDecimal deleteCustomer(Long customerNumber) throws CustomerNotFoundException;
}
