package com.example.demo.repo;

import com.example.demo.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {


    Optional<BankAccount> findByAccountNumber(Long accountNumber);


    List<BankAccount> findByCustomerId(Long customerId);


    @Query("SELECT a FROM BankAccount a WHERE a.customer.id = :customerId")
    List<BankAccount> findBankAccountsByCustomerId(@Param("customerId") Long customerId);
}