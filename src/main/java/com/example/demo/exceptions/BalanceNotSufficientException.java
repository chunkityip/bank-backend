package com.example.demo.exceptions;

// Exception when balance is not sufficient for a transaction
public class BalanceNotSufficientException extends Exception {
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
