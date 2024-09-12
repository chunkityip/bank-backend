package com.example.demo.service.impl;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Customer;
import com.example.demo.enums.AccountStatus;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repo.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
     public void getCustomerByIdTestWithEmptyAccount() throws CustomerNotFoundException {
        // Mock

        // Stub
        Customer customer = new Customer(1L , "CK" , Collections.emptyList());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Inject

        // Act
        CustomerDTO result = customerService.getCustomerById(1L);

        // Assert
        assertEquals(1L , result.getId());
        assertEquals("CK" , result.getName());
        assertEquals(Collections.emptyList() , result.getAccounts());

        verify(customerRepository , times(1)).findById(1L);
    }

    @Test
    public void getCustomerByIdNotFoundException() {
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class , () -> customerService.getCustomerById(2L));
    }

    @Test
    public void getCustomerByIdTestWithAccount() throws CustomerNotFoundException {
        // Mock

        // Stub
        BankAccount account = new BankAccount(1L, 111L, null, "sortCode1", "Account1", null, null, null, null, null);
        Customer customer = new Customer(1L , "CK", Arrays.asList(account));
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        // Inject

        // Act
        CustomerDTO result = customerService.getCustomerById(customer.getId());

        // Assert
        assertEquals("CK" , result.getName());

        // Verity
        verify(customerRepository , times(1)).findById(1L);
    }

    @Test
    void searchCustomersByNameTestWithEmptyAccount() {
        // Mock

        // Stub
        Customer customer = new Customer(1L , "CK" , Collections.emptyList());
        when(customerRepository.findCustomerByName(customer.getName())).thenReturn(Arrays.asList(customer));

        // Inject

        // Act
        List<CustomerDTO> result = customerService.searchCustomersByName(customer.getName());

        // Assert
        assertEquals("CK" , result.get(0).getName());

        // Verity
        verify(customerRepository , times(1)).findCustomerByName("CK");
    }

    @Test
    void searchCustomersByNameTestWithMulitpleAccount() {
        // Mock

        // Stub
        BankAccount account1 = new BankAccount(1L, 111L, null, "sortCode1", "Account1", null, null, null, null, null);
        BankAccount account2 = new BankAccount(2L, 112L, null, "sortCode1", "Account1", null, null, null, null, null);
        Customer customer = new Customer(1L, "CK", Arrays.asList(account1 , account2));
        when(customerRepository.findCustomerByName(customer.getName())).thenReturn(Arrays.asList(customer));

        // Inject

        // Act
        List<Customer> result = customerRepository.findCustomerByName(customer.getName());

        // Assert
        assertEquals(2 ,result.get(0).getBankAccounts().size());
        assertEquals("CK" , result.get(0).getName());

        // Verity
        verify(customerRepository , times(1)).findCustomerByName("CK");
    }

    @Test
    void createCustomerTest() {
    }

    @Test
    void deleteCustomerTest() {
    }
}