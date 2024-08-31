package com.example.demo.service.impl;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.RegisterCustomerDTO;
import com.example.demo.entity.Customer;
import com.example.demo.exception.*;
import com.example.demo.repo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1L , "CK Yip", Collections.emptyList());
        customerDTO = new CustomerDTO(1L, "CK Yip" , Collections.emptyList());
    }

    // Behavior
    @Test
    void testCustomerById() throws CustomerNotFoundException {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDTO result = customerService.getCustomerById(1L);
        assertEquals(customerDTO.getName(), result.getName());
        verify(customerRepository , times(1)).findById(1L);
    }

    @Test
    void testCreateCustomer() {
        // When
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Then
        CustomerDTO result = customerService.createCustomer(customer.getName());

        // Result
        assertEquals(customerDTO.getName() , result.getName());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void testSearchCustomersByName() {
        // When
        String keyword = "ck";
        when(customerRepository.findCustomerByName(keyword)).thenReturn(Arrays.asList(customer));

        // Then
        List<CustomerDTO> result = customerService.searchCustomersByName(keyword);

        // Result
        assertEquals(1, result.size());
        assertEquals("CK Yip", result.get(0).getName());
    }

    @Test
    public void testSearchCustomersWithUserNotFound() {
        // When
        String keyword = "Lawrence";
        when(customerRepository.findCustomerByName(keyword)).thenReturn(Arrays.asList(customer));

        // Then
        List<CustomerDTO> result = customerService.searchCustomersByName(keyword);

        // Result
        assertNotEquals("Lawrence", result.get(0).getName());
    }


    // Edge
    @Test
    void testGetCustomerByIdNotFound() {
        // Then
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        // Result
        assertThrows(CustomerNotFoundException.class , () -> customerService.getCustomerById(1L));
    }

    @Test

    void testSearchCustomersByNameWithEmptyResult() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> {
            CustomerDTO result = customerService.getCustomerById(1L);
        });
    }

    @Test
    void deleteCustomer() throws CustomerNotFoundException {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        customerService.deleteCustomer(1L);
        verify(customerRepository, times(1)).delete(customer);
    }

    @Test
    void deleteCustomerWithNoSuchCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(1L));
        verify(customerRepository, never()).delete(any());
    }
}
