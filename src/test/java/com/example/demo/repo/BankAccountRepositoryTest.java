package com.example.demo.repo;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Customer;
import com.example.demo.enums.BankType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class BankAccountRepositoryTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("CK");
        Customer saveCustomer = customerRepository.save(customer);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(1234L);
        bankAccount.setAccountNumber(123L); // Ensure the account number matches
        bankAccount.setName("Checking");
        bankAccount.setBalance(new BigDecimal(100.0));
        bankAccount.setBankType(BankType.DEFAULT);
        bankAccount.setCustomer(saveCustomer);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
    }

    @Test
    void findByAccountNumberTest() {
        // Act
        Optional<BankAccount> customerAccount = bankAccountRepository.findByAccountNumber(123L);

        // Assert
        assertThat(customerAccount).isPresent();
        assertThat(customerAccount.get().getName()).isEqualTo("Checking");
    }


    @Test
    void findBankAccountsByCustomerId() {
        List<BankAccount> customerAccount = bankAccountRepository
                .findBankAccountsByCustomerId(1L);

        assertThat(customerAccount).hasSize(1);
        assertThat(customerAccount.get(0).getAccountNumber()).isEqualTo(123L);
    }
}