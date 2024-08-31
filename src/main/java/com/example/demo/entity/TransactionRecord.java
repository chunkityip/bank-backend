package com.example.demo.entity;

import com.example.demo.enums.OperationType;
import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.util.Date;

/**
 * FG : Create Transaction entity
 */

@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
public class TransactionRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    @ManyToOne
    @JoinColumn(name = "account_id",nullable = false)
    private BankAccount bankAccount;

    private String description;
    //this is the from account
    private Long fromAccount;
    private String fromAccountSortCode;
//    private Long senderAccount;
//    private String senderAccountSortCode;

    //this is the to account
    private Long toAccount;
    private String toAccountSortCode;
//    private Long recipientAccount;
//    private String recipientAccountSortCode;

    private BigDecimal amount;

    //may have conflict with this type of Date
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date transactionDate;

    @PrePersist
    protected void onCreate() {
        transactionDate = new Date();
    }
}
