package com.example.demo.controller;

import com.example.demo.dto.BankAccountDTO;
import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.RegisterCustomerDTO;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.service.BankAccountService;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        try {
            CustomerDTO customerDTO = customerService.getCustomerById(id);
            return new ResponseEntity<>(customerDTO, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerDTO>> searchCustomersByName(@RequestParam String keyword) {
        List<CustomerDTO> customers = customerService.searchCustomersByName(keyword);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    // Need to be fixed
    @PostMapping(produces = "application/json")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody String customerName) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerName);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
//        try {
//            customerService.deleteCustomer(id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (CustomerNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BigDecimal> deleteCustomer(@PathVariable Long id) {
        try {
            // Fetch the customer
            CustomerDTO customer = customerService.getCustomerById(id);

            // Calculate the total funds across all the customer's bank accounts
            BigDecimal totalFunds = bankAccountService.getBankAccountsByCustomerId(id).stream()
                    .map(BankAccountDTO::getBalance)  // Extract the balance from each account
                    .reduce(BigDecimal.ZERO, BigDecimal::add);  // Sum all the balances

            // Delete the customer and their bank accounts
            customerService.deleteCustomer(id);

            // Return the total funds as the response
            return new ResponseEntity<>(totalFunds, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}