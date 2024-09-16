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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    // Mock
    @Mock
    private CustomerRepository customerRepository;

    // Inject
    @InjectMocks
    private CustomerServiceImpl customerService;

    /** getCustomerById
     *
     * @throws CustomerNotFoundException
     */
    @Test
    public void getCustomerByIdWithEmptyAccount() throws CustomerNotFoundException{
        // Mock

        // Stub
        Customer customer = new Customer(1L, "CK" , Collections.emptyList());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Inject

        // Act
        CustomerDTO result = customerService.getCustomerById(customer.getId());

        // Assert
        assertEquals("CK" , result.getName());
        assertEquals(1L , result.getId());
        assertEquals(new ArrayList<>() , result.getAccounts());

        // Verity
        verify(customerRepository , times(1)).findById(1L);
    }

    @Test
    public void getCustomerByIdWithAccount() throws CustomerNotFoundException{
        // Mock

        // Stub
        BankAccount account = new BankAccount(100L, 111L, null, "sortCode1", "Account1", null, null, null, null, null);
        Customer customer = new Customer(1L, "CK" , Arrays.asList(account));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Inject

        // Act
        CustomerDTO result = customerService.getCustomerById(customer.getId());

        // Assert
        assertAll(
                () -> assertEquals(1L , result.getId()),
                () -> assertEquals(1 , result.getAccounts().size()),
                () -> assertEquals(111L , result.getAccounts().get(0))
        );

        // Verity
        verify(customerRepository , times(1)).findById(1L);
    }

    @Test
    public void getCustomerByIdWithMultipleAccounts() throws CustomerNotFoundException{
        // Mock

        // Stub
        BankAccount account1 = new BankAccount(1L, 111L, null, "sortCode1", "Account1", null, null, null, null, null);
        BankAccount account2 = new BankAccount(2L, 111L, null, "sortCode1", "Account1", null, null, null, null, null);

        Customer customer = new Customer(1L, "CK" , Arrays.asList(account1 , account2));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Inject

        // Act
        CustomerDTO result = customerService.getCustomerById(customer.getId());


        // Assert
        assertAll(
                () -> assertEquals("CK" , result.getName()),
                () -> assertEquals(1L , result.getId()),
                () -> assertEquals(2 , result.getAccounts().size()),
                () -> assertEquals(account1.getAccountNumber(), result.getAccounts().get(0)),
                () -> assertEquals(account2.getAccountNumber(), result.getAccounts().get(1))
        );



        // Verity
        verify(customerRepository , times(1)).findById(1L);
    }

    @Test
    public void getCustomerByIdWithNullIDExceptionTest() {
        // Mock

        // Stub
        Customer customer = new Customer(1L, "CK" , Collections.emptyList());

        // Assert
        assertThrows(CustomerNotFoundException.class , () -> customerService.getCustomerById(2L));
    }

    /** searchCustomersByName
     *
     * @return nothing else
     */


    private static Stream<Arguments> searchCustomersByNameTest() {
        // Mock

        // Stub
        Customer customer1 = new Customer(1L , "CK" , Collections.emptyList());
        Customer customer2 = new Customer(2L , "Lawrence" , Collections.emptyList());

        // Arguments
        return Stream.of(
                // String searchKey , Customer customer , int exceptSize , String exceptName
                Arguments.of("CK" , customer1 , 1 , "CK"),
                Arguments.of("Lawrence" , customer2 , 1 , "Lawrence"),
                Arguments.of("UserNotFound" , null , 0 , null)
        );
    }

    @ParameterizedTest
    @MethodSource("searchCustomersByNameTest")
    void searchCustomersByNameParameterizedTest(String searchKey , Customer customer , int exceptSize , String exceptName) {
        // Mock

        // Stub
        when(customerRepository.findCustomerByName(searchKey)).thenReturn(customer == null ? Collections.emptyList() : Arrays.asList(customer));

        // Act
        List<CustomerDTO> result = customerService.searchCustomersByName(searchKey);

        // Assert
        assertAll(
                () -> assertEquals(exceptSize , result.size()),
                () -> {
                    if (exceptSize > 0) {
                        assertEquals(exceptName , result.get(0).getName());
                    }
                }
        );
    }


    /** createCustomerTest */
    // Helper method to provide test data (customer names and expected results)
    static Stream<Arguments> provideCustomerNames() {
        return Stream.of(
                Arguments.of("John Doe", 1L),
                Arguments.of("Jane Smith", 2L),
                Arguments.of("Alex Brown", 3L)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCustomerNames")
    public void createCustomerParameterizedTest(String customerName, Long expectedId) {
        // Arrange
        Customer savedCustomer = new Customer(expectedId, customerName, Collections.emptyList());
        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        // Act
        CustomerDTO result = customerService.createCustomer(customerName);

        // Assert
        assertAll(
                () -> assertEquals(expectedId, result.getId()),
                () -> assertEquals(customerName, result.getName()),
                () -> assertTrue(result.getAccounts().isEmpty())
        );

        // Verify the repository interaction
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void deleteCustomerTest() throws CustomerNotFoundException {
        // Mock
        // Stub
        Customer customer1 = new Customer(1L , "CK" , Collections.emptyList());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        // Inject
        // Act
        customerService.deleteCustomer(1L);

        // Vertiy
        verify(customerRepository , times(1)).delete(customer1);

    }

    @Test
    public void deleteCustomerException() throws CustomerNotFoundException {
        Customer customer = new Customer(1L , "CK" , Collections.emptyList());
        when(customerRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(2L));
    }

}

