package com.example.demo.controller;

import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.enums.OperationType;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.service.TransactionService;
import org.apache.tomcat.Jar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc; // Autowire MockMvc

    @MockBean
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testDepositTransaction() throws Exception {
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

        String depositResponse = objectMapper.writeValueAsString(request);

        when(transactionService.depositTransaction(any(), any(), any())).thenReturn(response);

        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(depositResponse))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationDate").exists())
                .andExpect(jsonPath("$.amount").value("100.0"));

    }


    @Test
    void testWithdrawTransaction() throws Exception {
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

        String withdrawDeposit = objectMapper.writeValueAsString(request);

        when(transactionService.withdrawTransaction(any() , any() , any()))
                .thenReturn(response);


        mockMvc.perform(post("/transaction")
                .contentType(MediaType.APPLICATION_JSON)
                .content(withdrawDeposit))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromAccount").value(87654321))
                .andExpect(jsonPath("$.amount").value(50.0))
                .andExpect(jsonPath("$.type").value("WITHDRAWAL"))
                .andExpect(jsonPath("$.operationDate").exists());
    }

    @Test
    void testTransferTransaction() throws Exception {
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


    }

    @Test
    void testProcessTransaction_InvalidType() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO();
        request.setType(99);  // Invalid transaction type

        String withdrawDeposit = objectMapper.writeValueAsString(request);


        mockMvc.perform(post("/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withdrawDeposit))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

    }
}
