package com.example.demo.controller;

import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.enums.OperationType;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    void testProcessTransaction_Deposit() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setType(2);
        request.setToAccount(12345678L);
        request.setToAccountSortCode("123456");
        request.setAmount(new BigDecimal("100.00"));

        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setOperationDate(new Date());
        response.setType(OperationType.DEPOSIT);
        response.setToAccount(12345678L);
        response.setToAccountSortCode("123456");
        response.setAmount(new BigDecimal("100.00"));

        when(transactionService.depositTransaction(any(), any(), any())).thenReturn(response);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationDate").exists())
                .andExpect(jsonPath("$.type").value("DEPOSIT"))
                .andExpect(jsonPath("$.toAccount").value(12345678L))
                .andExpect(jsonPath("$.amount").value(100.00));
    }

    @Test
    void testProcessTransaction_Withdraw() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setType(1);
        request.setFromAccount(87654321L);
        request.setFromAccountSortCode("654321");
        request.setAmount(new BigDecimal("50.00"));

        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setOperationDate(new Date());
        response.setType(OperationType.WITHDRAWAL);
        response.setFromAccount(87654321L);
        response.setFromAccountSortCode("654321");
        response.setAmount(new BigDecimal("50.00"));

        when(transactionService.withdrawTransaction(any(), any(), any())).thenReturn(response);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationDate").exists())
                .andExpect(jsonPath("$.type").value("WITHDRAWAL"))
                .andExpect(jsonPath("$.fromAccount").value(87654321L))
                .andExpect(jsonPath("$.amount").value(50.00));
    }

    @Test
    void testProcessTransaction_Transfer() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setType(0);
        request.setFromAccount(87654321L);
        request.setFromAccountSortCode("654321");
        request.setToAccount(12345678L);
        request.setToAccountSortCode("123456");
        request.setAmount(new BigDecimal("200.00"));

        TransactionResponseDTO response = new TransactionResponseDTO();
        response.setOperationDate(new Date());
        response.setType(OperationType.TRANSFER);
        response.setFromAccount(87654321L);
        response.setFromAccountSortCode("654321");
        response.setToAccount(12345678L);
        response.setToAccountSortCode("123456");
        response.setAmount(new BigDecimal("200.00"));

        when(transactionService.transferTransaction(any(), any(), any(), any(), any())).thenReturn(response);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationDate").exists())
                .andExpect(jsonPath("$.type").value("TRANSFER"))
                .andExpect(jsonPath("$.fromAccount").value(87654321L))
                .andExpect(jsonPath("$.toAccount").value(12345678L))
                .andExpect(jsonPath("$.amount").value(200.00));
    }

    @Test
    void testProcessTransaction_InvalidType() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setType(99);  // Invalid transaction type

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(""));  // Expecting null response body
    }
}
