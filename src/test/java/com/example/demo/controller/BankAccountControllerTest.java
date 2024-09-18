package com.example.demo.controller;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.entity.Customer;
import com.example.demo.exception.BankAccountNotFoundException;
import com.example.demo.service.BankAccountService;
import com.example.demo.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private BankAccountController bankAccountController;

    private BankAccountDTO bankAccountDTO;

    @BeforeEach
    void setUp() {
       bankAccountDTO = new BankAccountDTO();
       bankAccountDTO.setBalance(new BigDecimal("100.0"));
       bankAccountDTO.setNumber(1234L);
    }

    @Test
    void testGetBankAccountNumber() throws Exception {
        // Mock the service method to return the BankAccountDTO object
        when(bankAccountService.getBankAccountNumber(anyLong()))
                .thenReturn(bankAccountDTO);

        // Perform the GET request to the correct URL with an ID path variable
        mockMvc.perform(get("/account/1234")) // Include the ID path variable in the URL
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.balance").value("100.0")) // Correct JSON path for 'balance'
                .andExpect(jsonPath("$.number").value(1234)); // Verify the 'number' field too
    }

    @Test
    void testGetBankAccountNumberWithException() throws Exception {
        when(bankAccountService.getBankAccountNumber(anyLong()))
                .thenThrow(new BankAccountNotFoundException("Account not found"));

        mockMvc.perform(get("/account/1234"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetBankAccountsByCustomerId() throws Exception {
        // Mock the service method to return a list with the BankAccountDTO object
        when(bankAccountService.getBankAccountsByCustomerId(anyLong()))
                .thenReturn(Collections.singletonList(bankAccountDTO));

        // Perform the GET request to the correct URL with an ID path variable
        mockMvc.perform(get("/account/customer/1234"))  // Correct URL
                .andExpect(status().isOk())  // Expect 200 OK status
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].balance").value("100.0"))
                .andExpect(jsonPath("$[0].number").value(1234));
    }

    @Test
    void createBankAccount() {
    }

    @Test
    void deleteBankAccount() {
    }
}