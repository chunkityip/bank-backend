package com.example.demo.service;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.RegisterCustomerDTO;
import com.example.demo.exceptions.CustomerNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {
    CustomerDTO getCustomerById(Long id) throws CustomerNotFoundException;
    List<CustomerDTO> searchCustomersByName(String keyword);
    CustomerDTO createCustomer(RegisterCustomerDTO registerCustomerDTO);
    void deleteCustomer(Long id) throws CustomerNotFoundException;
}
