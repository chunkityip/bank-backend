package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.RegisterCustomerDTO;
import com.example.demo.entity.Customer;
import com.example.demo.service.BankAccountService;
import com.example.demo.service.CustomerService;
import com.example.demo.exception.CustomerNotFoundException;
import jdk.dynalink.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private BankAccountService bankAccountService;

    @InjectMocks
    private CustomerController customerController;


    @Test
    void testGetCustomerByIDWith200Status() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("CK");

        when(customerService.getCustomerById(1L)).thenReturn(customerDTO);

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("CK"));
    }

    @Test
    void testGetCustomerByIdException() throws Exception {
        when(customerService.getCustomerById(anyLong()))
                .thenThrow(new CustomerNotFoundException("Customer not found"));

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSearchCustomersByName() throws Exception {
        //Stub
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("CK");

        when(customerService.searchCustomersByName(anyString())).thenReturn(Collections.singletonList(customerDTO));

        mockMvc.perform(get("/customer/search").param("keyword" , "CK"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("CK"));
    }


    @Test
    void testCreateCustomerTest() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("CK");

        when(customerService.createCustomer(anyString())).thenReturn(customerDTO);

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("CK")))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("CK"));

    }



    @Test
    public void testDeleteCustomerReturnsTotalFunds() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("CK");

        BankAccountDTO bankAccount1 = new BankAccountDTO();
        bankAccount1.setBalance(new BigDecimal("100.00"));

        BankAccountDTO bankAccount2 = new BankAccountDTO();
        bankAccount2.setBalance(new BigDecimal("50.00"));

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);
        when(bankAccountService.getBankAccountsByCustomerId(anyLong())).thenReturn(Arrays.asList(bankAccount1, bankAccount2));

        mockMvc.perform(delete("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(new BigDecimal("150.0")));


        verify(customerService).deleteCustomer(anyLong());

    }


    @Test
    public void testDeleteCustomerReturnsNotFound() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenThrow(new CustomerNotFoundException("Customer not found"));

        mockMvc.perform(delete("/customer/1"))
                .andExpect(status().isNotFound());
    }
















}
