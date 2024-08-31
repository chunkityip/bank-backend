package com.example.demo.repo;

import com.example.demo.entity.TransactionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FG: Searching Transaction base on kw as keyword
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionRecord,Long> {
//    @Query("select t from Transaction where transactionid like :id")
//    List<TransactionRecord> searchTransaction(@Param("id") Long transactionId);

    // We can do that instead in line 17 and 18
    List<TransactionRecord> findByBankAccountId(Long accountId);
    //List<TransactionRecord> findByBankAccountIdOrderByTransactionDateDesc(Long accountId, Pageable pageable);
}
