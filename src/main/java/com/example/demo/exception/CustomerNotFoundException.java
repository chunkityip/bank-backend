package com.example.demo.exception;

// Exception when a customer is not found
public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
