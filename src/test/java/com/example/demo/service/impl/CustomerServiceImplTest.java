package com.example.demo.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.RegisterCustomerDTO;
import com.example.demo.entity.Customer;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@SpringBootTest
//public class CustomerServiceImplTest {
//
//    @Mock
//    private CustomerRepository customerRepository;
//
//    @InjectMocks
//    private CustomerServiceImpl customerService;
//
//    private Customer customer;
//    private CustomerDTO customerDTO;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//
//        // Initialize test data
//        customer = new Customer(1L, "John Doe", "john@example.com", "password123", "CUST123", Collections.emptyList());
//        customerDTO = new CustomerDTO(1L, "John Doe", "john@example.com", Collections.emptyList());
//    }
//
//    // Behavior Tests
//
//    @Test
//    void testGetCustomerById_Success() throws CustomerNotFoundException {
//        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
//        CustomerDTO result = customerService.getCustomerById(1L);
//        assertEquals(customerDTO.getId(), result.getId());
//        assertEquals(customerDTO.getName(), result.getName());
//        assertEquals(customerDTO.getEmail(), result.getEmail());
//        verify(customerRepository, times(1)).findById(1L);
//    }
//
//    @Test
//    void testCreateCustomer_Success() {
//        RegisterCustomerDTO registerDTO = new RegisterCustomerDTO("Jane Doe", "jane@example.com", "password123");
//        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
//        CustomerDTO result = customerService.createCustomer(registerDTO);
//        assertEquals(customerDTO.getName(), result.getName());
//        assertEquals(customerDTO.getEmail(), result.getEmail());
//        verify(customerRepository, times(1)).save(any(Customer.class));
//    }
//
//    // Edge Case Tests
//
//    @Test
//    void testGetCustomerById_NotFound() {
//        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(1L));
//    }
//
//    @Test
//    void testSearchCustomersByName_EmptyResult() {
//        when(customerRepository.findByNameContaining("NonExistent")).thenReturn(Collections.emptyList());
//        List<CustomerDTO> result = customerService.searchCustomersByName("NonExistent");
//        assertTrue(result.isEmpty());
//    }
//
//    // Time Tests
//
//    @Test
//    void testDeleteCustomer_Success() throws CustomerNotFoundException {
//        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
//        customerService.deleteCustomer(1L);
//        verify(customerRepository, times(1)).delete(customer);
//    }
//
//    @Test
//    void testDeleteCustomer_NotFound() {
//        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
//        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(1L));
//        verify(customerRepository, never()).delete(any(Customer.class));
//    }
//}