package com.example.demo.service.impl;


import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.RegisterCustomerDTO;
import com.example.demo.entity.Customer;
import com.example.demo.entity.BankAccount;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repo.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
//@Service
//public class CustomerServiceImpl implements CustomerService{
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Override
//    public CustomerDTO getCustomerById(Long id) throws CustomerNotFoundException {
//        Customer customer = customerRepository.findById(id)
//                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
//
//        // Map the list of account numbers
//        List<String> accountNumbers = customer.getBankAccounts().stream()
//                .map(BankAccount::getAccountNumber)
//                .collect(Collectors.toList());
//
//        // Return the DTO with the account numbers
//        return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(), accountNumbers);
//    }


//    @Override
//    public List<CustomerDTO> searchCustomersByName(String keyword) {
//        return customerRepository.findCustomerByName(keyword)
//                .stream()
//                .map(customer -> new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(),
//                        customer.getBankAccounts().stream().map(BankAccount::getAccountNumber).collect(Collectors.toList())))
//                .collect(Collectors.toList());
//    }
//
//    /**
//     * This is for
//     * @Override
//     * @param registerCustomerDTO
//     * @return
//     *     public CustomerDTO createCustomer(RegisterCustomerDTO registerCustomerDTO) {
//     *         Customer customer = new Customer();
//     *         customer.setName(registerCustomerDTO.getName());
//     *         customer.setEmail(registerCustomerDTO.getEmail());
//     *         customer.setPassword(registerCustomerDTO.getPassword());
//     *         customer.setCustomerIdentificationNumber(UUID.randomUUID().toString());
//     *         customer = customerRepository.save(customer);
//     *         return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(), Collections.emptyList());
//     *     }
//     */
//
//    @Override
//    public CustomerDTO createCustomer(RegisterCustomerDTO registerCustomerDTO) {
//        Customer customer = new Customer();
//        customer.setName(registerCustomerDTO.getName());
//        customer.setEmail(registerCustomerDTO.getEmail());
//        customer.setPassword(registerCustomerDTO.getPassword());
//        customer.setCustomerIdentificationNumber(UUID.randomUUID().toString());
//        customer = customerRepository.save(customer);
//        return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(), Collections.emptyList());
//    }
//
//    @Override
//    public void deleteCustomer(Long id) throws CustomerNotFoundException {
//        Customer customer = customerRepository.findById(id)
//                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
//        customerRepository.delete(customer);
//    }
//}

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerDTO getCustomerById(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        // Map the list of account numbers
        List<Long> accountNumbers = customer.getBankAccounts().stream()
                .map(BankAccount::getAccountNumber)
                .collect(Collectors.toList());

        // Return the DTO with the account numbers
        return new CustomerDTO(customer.getId(), customer.getName(), accountNumbers);
    }


    @Override
    public List<CustomerDTO> searchCustomersByName(String keyword) {
        return customerRepository.findCustomerByName(keyword)
                .stream()
                .map(customer -> new CustomerDTO(customer.getId(), customer.getName(),
                        customer.getBankAccounts().stream().map(BankAccount::getAccountNumber).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    /**
     * This is for
     * @Override
     * @param registerCustomerDTO
     * @return
     *     public CustomerDTO createCustomer(RegisterCustomerDTO registerCustomerDTO) {
     *         Customer customer = new Customer();
     *         customer.setName(registerCustomerDTO.getName());
     *         customer.setEmail(registerCustomerDTO.getEmail());
     *         customer.setPassword(registerCustomerDTO.getPassword());
     *         customer.setCustomerIdentificationNumber(UUID.randomUUID().toString());
     *         customer = customerRepository.save(customer);
     *         return new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(), Collections.emptyList());
     *     }
     */

    @Override
    public CustomerDTO createCustomer(String customerName) {
        Customer customer = new Customer();
        customer.setName(customerName);
        customer = customerRepository.save(customer);
        return new CustomerDTO(customer.getId(), customer.getName(), Collections.emptyList());
    }

    @Override
    public void deleteCustomer(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerRepository.delete(customer);
    }

    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<BigDecimal> deleteCustomer(@PathVariable Long id) {
        try {
            // Fetch the customer and calculate the total funds
            CustomerDTO customer = customerService.getCustomerById(id);
            BigDecimal totalFunds = customer.getAccounts().stream()
                    .map(accountNumber -> bankAccountService.getBankAccountById(accountNumber))
                    .map(BankAccountDTO::getBalance)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // Delete all accounts and the customer
            customerService.deleteCustomer(id);

            // Return the total funds as the response
            return new ResponseEntity<>(totalFunds, HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
     */
}
