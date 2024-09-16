package com.example.demo.service.impl;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Customer;
import com.example.demo.enums.AccountStatus;
import com.example.demo.enums.BankType;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.repo.BankAccountRepository;
import com.example.demo.repo.CustomerRepository;
import com.example.demo.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static com.example.demo.enums.AccountStatus.ACTIVATED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    private Customer customer;
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        customer = new Customer(10L , "CK" , Collections.emptyList());

        bankAccount = new BankAccount(1L ,
                1234L , null ,
                null , "Checking" ,
                null , null , ACTIVATED ,
                customer , null);
    }

    @Test
    void getBankAccountByIdTest() throws BankAccountNotFoundException {
        // Mock
        // Stub
        when(bankAccountRepository.findById(10L)).thenReturn(Optional.of(bankAccount));

        // Inject
        // Act
        BankAccountDTO result = bankAccountService.getBankAccountById(10L);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(10L , result.getCustomerId()),
                () -> assertEquals("Checking" , result.getName())
        );

        verify(bankAccountRepository, times(1)).findById(10L);
    }

    @Test
    void getBankAccountByIdTestException() {

        when(bankAccountRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(BankAccountNotFoundException.class, () -> bankAccountService.getBankAccountById(2L));
    }


    @Test
    void getBankAccountNumberTest() throws BankAccountNotFoundException {
        // Mock
        // Stub
        when(bankAccountRepository.findByAccountNumber(1234L)).thenReturn(Optional.of(bankAccount));

        // Inject
        // Act
        BankAccountDTO result = bankAccountService.getBankAccountNumber(1234L);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(10L , result.getCustomerId())
        );
        //Verity
        verify(bankAccountRepository , times(1)).findByAccountNumber(1234L);
    }

    @Test
    void getBankAccountNumberTestException() {
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountService.getBankAccountNumber(4321L));
    }


    @Test
    void getBankAccountsByCustomerId() throws BankAccountNotFoundException {
        BankAccount bankAccount = new BankAccount(1L, 123456789L, BankType.DEFAULT, "sortCode", "Checking",
                BigDecimal.ZERO, BigDecimal.ZERO, AccountStatus.ACTIVATED, new Customer(), new Date());
        List<BankAccount> bankAccounts = Arrays.asList(bankAccount);

        // Stubbing the repository method to return a list of BankAccount
        when(bankAccountRepository.findByCustomerId(10L)).thenReturn(bankAccounts);

        // Act
        List<BankAccountDTO> result = bankAccountService.getBankAccountsByCustomerId(10L);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> assertEquals("Checking", result.get(0).getName())
        );

        // Verify that the repository method was called
        verify(bankAccountRepository, times(1)).findByCustomerId(10L);
    }

    @Test
    void createBankAccountTest() {
        // Mock
        when(bankAccountRepository.save(any(BankAccount.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // Return the same object that was saved

        // Stub
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setCustomerId(1L);
        bankAccountDTO.setAccountName("Checking Account");
        bankAccountDTO.setOpeningBalance(BigDecimal.valueOf(1000));

        Customer customer = new Customer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Act
        BankAccountDTO result = bankAccountService.createBankAccount(bankAccountDTO);

        // Assert
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("Checking Account" , result.getName()),
                () -> assertEquals(BigDecimal.valueOf(1000), result.getOpeningBalance())
        );

        // Verify
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void deleteBankAccountTest() throws BankAccountNotFoundException{
        // Mock
        // Stub
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setAccountNumber(1234L);
        bankAccount.setBankType(BankType.DEFAULT);
        bankAccount.setSortCode("sortCode");
        bankAccount.setName("Checking");
        bankAccount.setOpeningBalance(BigDecimal.valueOf(1000));
        bankAccount.setBalance(BigDecimal.valueOf(1000));
        bankAccount.setStatus(ACTIVATED);
        bankAccount.setCustomer(customer);
        bankAccount.setCreatedAt(new Date());


        when(bankAccountRepository.findByAccountNumber(1234L)).thenReturn(Optional.of(bankAccount));

        double result = bankAccountService.deleteBankAccount(1234L);
        // Inject

        // Assert
        assertEquals(1000.0 , result);

        // Verity
        verify(bankAccountRepository, times(1)).delete(bankAccount);
    }

    @Test
    void deleteBankAccountTestException() {
        assertThrows(BankAccountNotFoundException.class, () -> bankAccountService.deleteBankAccount(1L));
    }

}