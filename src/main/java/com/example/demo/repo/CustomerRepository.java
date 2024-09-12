package com.example.demo.repo;

import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CK: Searching Customer base on kw as keyword
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findCustomerByName(String customerName);
}

