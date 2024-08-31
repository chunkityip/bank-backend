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
    /**
     @Query("select c from Customer where id like :id")
     List<Customer> searchCustomer(@Param("id") Long id);

     msz: my correction like is for strings, i think it should be = since id is long
     I'm just testing this query with h2. it can be uncommented tomorrow

     @Query("select c from Customer c where c.id = :id")
     List<Customer> searchCustomer(@Param("id") Long id);
     */

    List<Customer> findCustomerByName(String customerName);


}

