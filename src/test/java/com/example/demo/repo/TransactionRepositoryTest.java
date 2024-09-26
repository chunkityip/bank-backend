package com.example.demo.repo;

import com.example.demo.entity.BankAccount;
import com.example.demo.entity.Customer;
import com.example.demo.entity.TransactionRecord;
import com.example.demo.enums.BankType;
import com.example.demo.enums.OperationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TransactionRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private Long bankAccountId;

    @BeforeEach
    void setUp() {
        // Create and persist the Customer entity
        Customer customer = new Customer();
        customer.setName("CK");
        customerRepository.save(customer);

        // Create and persist the BankAccount entity
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setAccountNumber(123L);
        bankAccount1.setName("Checking");
        bankAccount1.setBalance(new BigDecimal(100.0));
        bankAccount1.setBankType(BankType.DEFAULT);
        bankAccount1.setCustomer(customer);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount1);

        // Store the bank account ID for later use in the test
        bankAccountId = savedBankAccount.getId();
        System.out.println("Saved Bank Account ID: " + bankAccountId);

        // Create and persist the TransactionRecord entity
        TransactionRecord transactionRecord = new TransactionRecord();
        transactionRecord.setType(OperationType.DEPOSIT);
        transactionRecord.setBankAccount(savedBankAccount);  // Link it with the saved bank account
        transactionRecord.setDescription("This is for CK");
        transactionRecord.setFromAccount(bankAccountId);  // Use the correct bank account ID
        transactionRecord.setAmount(new BigDecimal(55));
        transactionRepository.save(transactionRecord);
    }

    @Test
    void findByBankAccountId() {
        // Use the bankAccountId captured during setup
        List<TransactionRecord> transaction = transactionRepository.findByBankAccountId(bankAccountId);

        assertThat(transaction).hasSize(1);
        assertThat(transaction.get(0).getDescription()).isEqualTo("This is for CK");
    }
}
