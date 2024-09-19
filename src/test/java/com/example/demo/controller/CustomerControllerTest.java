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
import org.mockito.ArgumentMatchers;
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

    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setName("CK");
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);

        mockMvc.perform(get("/customer/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(".id").value(1))
                .andExpect(jsonPath(".name").value("CK"));
    }

    @Test
    void testGetCustomerByIdWithException() throws Exception {
        when(customerService.getCustomerById(anyLong()))
                .thenThrow(new CustomerNotFoundException("Customer not found"));

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isNotFound());

    }


    @Test
    void testSearchCustomersByName() throws Exception {
        when(customerService.searchCustomersByName(anyString()))
                .thenReturn(Collections.singletonList(customerDTO));

        mockMvc.perform(get("/customer/search")
                        .param("keyword" , "CK"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("CK"));
    }

    @Test
    void testCreateCustomer() throws Exception{
        when(customerService.createCustomer(anyString()))
                .thenReturn(customerDTO);

        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("CK")))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath(".id").value(1))
                    .andExpect(jsonPath(".name").value("CK"));

    }


    @Test
    void testDeleteCustomer() throws Exception {
        BankAccountDTO bankAccountDTO1 = new BankAccountDTO();
        bankAccountDTO1.setBalance(new BigDecimal("100.0"));

        BankAccountDTO bankAccountDTO2 = new BankAccountDTO();
        bankAccountDTO2.setBalance(new BigDecimal("200.0"));

        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);
        when(bankAccountService.getBankAccountsByCustomerId(anyLong())).thenReturn(Arrays
                .asList(bankAccountDTO1 , bankAccountDTO2));


        mockMvc.perform(delete("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").value(new BigDecimal("300.0")));


        verify(customerService).deleteCustomer(anyLong());
    }

    @Test
    void testDeleteCustomerWithException() throws Exception {
        when(customerService.getCustomerById(anyLong()))
                .thenThrow(new CustomerNotFoundException("Customer not found"));


        mockMvc.perform(delete("/customer/1"))
                .andExpect(status().isNotFound());
    }

}
