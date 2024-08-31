package com.example.demo.repo;

import com.example.demo.entity.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CK: Example of how to find:
 * findByBankAccountId : finding account base on id
 * findByBankAccountIdOrderByOperationDateDesc : finding account base on data ascending order
 */

@Repository
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord,Long> {
//    List<TransactionRecord> findByBankAccountId(String accountId);
//    Page<TransactionRecord> findByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);
}
