package com.example.demo.service.impl;

import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Customer;
import com.example.demo.entity.TransactionRecord;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.repo.BankAccountRepository;
import com.example.demo.repo.CustomerRepository;
import com.example.demo.repo.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demo.enums.AccountStatus.ACTIVATED;
import static com.example.demo.enums.OperationType.TRANSFER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Customer customer;
    private BankAccount toBankAccount;
    private BankAccount fromBankAccount;

    @BeforeEach
    void setUp() {
        customer = new Customer(10L ,
                "CK" ,
                Collections.emptyList());

        toBankAccount = new BankAccount(1L,
                1234L, null,
                "1234", "Checking",
                new BigDecimal(1000.0), new BigDecimal(1000.0), ACTIVATED, // Make sure balance is not null
                customer, null);


        fromBankAccount = new BankAccount(2L,
                4321L, null,
                "4321", "Checking",
                new BigDecimal(1000.0), new BigDecimal(1000.0), ACTIVATED, // Make sure balance is not null
                customer, null);

    }

    @Test
    void depositTransactionTest() throws BankAccountNotFoundException {
        // Mocking the repository call to find the bank account
        when(bankAccountRepository.findByAccountNumber(1234L)).thenReturn(Optional.of(toBankAccount));

        // Act: Simulate the deposit transaction with a deposit amount of 100
        BigDecimal depositAmount = new BigDecimal(100.0);
        TransactionResponseDTO result = transactionService.depositTransaction(1234L, "1234", depositAmount);

        // Assert: Ensure that the balance is updated correctly and the response DTO has correct values
        assertAll(
                () -> assertEquals(new BigDecimal(1100.0), toBankAccount.getBalance()),  // Initial balance 1000 + deposit 100 = 1100
                () -> assertNotNull(result),  // result should not be null
                () -> assertEquals(depositAmount, result.getAmount()),  // Deposit amount should be 100
                () -> assertEquals("1234", result.getToAccountSortCode()),  // Sort code should match
                () -> assertEquals(1234L, result.getToAccount())  // Account number should match
        );

        // Verify that the correct methods were called
        verify(bankAccountRepository, times(1)).findByAccountNumber(1234L);
        verify(bankAccountRepository, times(1)).save(toBankAccount);
        verify(transactionRepository, times(1)).save(any(TransactionRecord.class));
    }



    @Test
    void withdrawTransactionTest() throws BankAccountNotFoundException {
        // Mock the repository response for finding the bank account
        when(bankAccountRepository.findByAccountNumber(4321L)).thenReturn(Optional.of(fromBankAccount));

        // Act: Call the withdrawTransaction method
        BigDecimal withdrawAmount = new BigDecimal(100.0);
        TransactionResponseDTO response = transactionService.withdrawTransaction(4321L, "4321", withdrawAmount);

        // Assert: Check if the balance has been updated correctly
        assertEquals(new BigDecimal(900.0), fromBankAccount.getBalance());
        assertNotNull(response);
        assertEquals(withdrawAmount, response.getAmount());
        assertEquals("4321", response.getFromAccountSortCode());
        assertEquals(4321L, response.getFromAccount());

        // Verify the bank account and transaction were saved correctly
        verify(bankAccountRepository, times(1)).findByAccountNumber(4321L);
        verify(bankAccountRepository, times(1)).save(fromBankAccount);
        verify(transactionRepository, times(1)).save(any(TransactionRecord.class));
    }

    @Test
    void transferTransactionTest() throws BankAccountNotFoundException{
        // Mock
        //Stub
        when(bankAccountRepository.findByAccountNumber(1234L)).thenReturn(Optional.of(toBankAccount));
        when(bankAccountRepository.findByAccountNumber(4321L)).thenReturn(Optional.of(fromBankAccount));

        // Inject
        // Act
        BigDecimal transferAmount = new BigDecimal(200.0);
        TransactionResponseDTO responseDTO = transactionService.transferTransaction(4321L, "4321", 1234L, "1234", transferAmount);

        // Assert
        assertAll(
                () -> assertNotNull(responseDTO),
                () -> assertEquals(new BigDecimal(1200) , toBankAccount.getBalance()),
                () -> assertEquals(new BigDecimal(800) , fromBankAccount.getBalance())

                );
        // Verity
        verify(bankAccountRepository, times(1)).findByAccountNumber(4321L);
        verify(bankAccountRepository, times(1)).findByAccountNumber(1234L);
        verify(bankAccountRepository, times(1)).save(fromBankAccount);
        verify(bankAccountRepository, times(1)).save(toBankAccount);
        verify(transactionRepository , times(1)).save(any(TransactionRecord.class));
    }

    @Test
    void getTransactionsForAccountTest() {
        // Mocking a list of transaction records for a bank account
        TransactionRecord transaction1 = new TransactionRecord();
        transaction1.setFromAccount(4321L);
        transaction1.setToAccount(1234L);
        transaction1.setAmount(new BigDecimal(200.0));
        transaction1.setType(TRANSFER);
        transaction1.setBankAccount(fromBankAccount);

        TransactionRecord transaction2 = new TransactionRecord();
        transaction2.setFromAccount(4321L);
        transaction2.setToAccount(9876L);
        transaction2.setAmount(new BigDecimal(300.0));
        transaction2.setType(TRANSFER);
        transaction2.setBankAccount(fromBankAccount);

        // Mocking the repository to return a list of transactions
        when(transactionRepository.findByBankAccountId(4321L)).thenReturn(Arrays.asList(transaction1, transaction2));

        // Act: Fetch the transactions
        List<TransactionResponseDTO> result = transactionService.getTransactionsForAccount(4321L);

        // Assert: Verify the size and content of the result
        assertAll(
                () -> assertEquals(2, result.size()),  // There should be two transactions
                () -> assertEquals(new BigDecimal(200.0), result.get(0).getAmount()),  // Check the amount of the first transaction
                () -> assertEquals(new BigDecimal(300.0), result.get(1).getAmount()),  // Check the amount of the second transaction
                () -> assertEquals(4321L, result.get(0).getFromAccount()),  // Verify fromAccount of the first transaction
                () -> assertEquals(1234L, result.get(0).getToAccount()),  // Verify toAccount of the first transaction
                () -> assertEquals(9876L, result.get(1).getToAccount())  // Verify toAccount of the second transaction
        );

        // Verify that the repository method was called
        verify(transactionRepository, times(1)).findByBankAccountId(4321L);
    }

}