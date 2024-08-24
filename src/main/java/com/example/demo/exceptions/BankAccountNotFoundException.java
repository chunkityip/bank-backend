package com.example.demo.exceptions;

// Exception when a bank account is not found
public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
