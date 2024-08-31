package com.example.demo.exception;

// Exception when balance is not sufficient for a transaction
public class BalanceNotSufficientException extends Exception {
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
