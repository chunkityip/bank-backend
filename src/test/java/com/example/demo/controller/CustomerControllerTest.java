package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.RegisterCustomerDTO;
import com.example.demo.service.CustomerService;
import com.example.demo.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;

@SpringBootTest
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        // Initialize test data
        customerDTO = new CustomerDTO(1L, "CK",  Collections.emptyList());
    }

    // Behavior
//    @Test
//    void testGetCustomerById() throws Exception {
//        // When
//        when(customerService.getCustomerById(anyLong())).thenReturn(customerDTO);
//
//        // Result
//        mockMvc.perform(get("/customer/1").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("CK"));
//    }

}