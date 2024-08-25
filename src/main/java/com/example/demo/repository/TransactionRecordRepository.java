package com.example.demo.repository;

import com.example.demo.entity.TransactionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, Long> {
    List<TransactionRecord> findByBankAccountId(Long accountId);
    Page<TransactionRecord> findByBankAccountIdOrderByTransactionDateDesc(Long accountId, Pageable pageable); // Updated field name
}