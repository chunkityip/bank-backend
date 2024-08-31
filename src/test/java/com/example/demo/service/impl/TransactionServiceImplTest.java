package com.example.demo.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.entity.BankAccount;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.repo.BankAccountRepository;
import com.example.demo.repo.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;


    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount();
        bankAccount.setId(1L);
        bankAccount.setBalance(BigDecimal.valueOf(1000));
    }

    @Test
    void testDepositTransaction() throws BankAccountNotFoundException {
        Long accountId = 1L;
        String sortCode = "12345";
        BigDecimal amount = BigDecimal.valueOf(500);

        // When
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.of(bankAccount));

        // Then
        TransactionResponseDTO response = transactionService.depositTransaction(accountId, sortCode, amount);

        // Result
        assertNotNull(response);
        assertEquals(accountId, response.getToAccount());
        assertEquals(sortCode, response.getToAccountSortCode());
        assertEquals(amount, response.getAmount());

        verify(bankAccountRepository).findById(accountId);
        verify(bankAccountRepository).save(bankAccount);
    }


    @Test
    public void testDepositTransactionByBankAccountNotFoundException() {
        Long accountId = 1L;
        String sortCode = "12345";
        BigDecimal amount = BigDecimal.valueOf(500);

        // When
        when(bankAccountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Result
        assertThrows(BankAccountNotFoundException.class, () -> {
            transactionService.depositTransaction(accountId, sortCode , amount);
        });

        verify(bankAccountRepository).findById(accountId);
    }
}
