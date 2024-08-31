package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.enums.BankType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class BankInfoControllerTest {

    @InjectMocks
    private BankInfoController bankInfoController;

    @Mock
    private BankType bankType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSortCode() {
        // It needs to match to DEFAULT("3435436") from enum BankType
        // When
        String sortCode = "3435436";
        when(bankType.getSortCode()).thenReturn(sortCode);

        // Then
        ResponseEntity<String> response = bankInfoController.getSortCode();

        // Result
        assertEquals(sortCode, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }
}