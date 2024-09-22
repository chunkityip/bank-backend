package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.enums.BankType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BankInfoController.class)
public class BankInfoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankType bankType;

    @InjectMocks
    private BankInfoController bankInfoController;

    @Test
    void testGetSortCode() throws Exception {
        String sortCode = "3435436";
        when(bankType.getSortCode()).thenReturn(sortCode);

        mockMvc.perform(get("/sortCode"))
                .andExpect(status().isOk())
                .andExpect(content().string(sortCode));

    }




}