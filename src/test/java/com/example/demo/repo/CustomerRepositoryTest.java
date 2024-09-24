package com.example.demo.repo;

import com.example.demo.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Use this to avoid replacing your PostgreSQL with an in-memory database
@ActiveProfiles("test") // Use the test profile to load application-test.properties
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindCustomerByName() {
        // Given
        Customer customer = new Customer();
        customer.setName("John Doe");
        customerRepository.save(customer);

        // When
        List<Customer> foundCustomers = customerRepository.findCustomerByName("John Doe");

        // Then
        assertThat(foundCustomers).hasSize(1);
        assertThat(foundCustomers.get(0).getName()).isEqualTo("John Doe");
    }


}