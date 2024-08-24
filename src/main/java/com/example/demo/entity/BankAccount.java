package com.example.demo.entity;

import com.example.demo.enums.AccountStatus;
import com.example.demo.enums.BankType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private BankType bankType;

    @Column(nullable = false)
    private Long sortCode;

    private String name;
    private BigDecimal openingBalance;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TransactionRecord> transactionRecords;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        if (accountNumber == null) {
            accountNumber = UUID.randomUUID().toString(); // Or generate in a custom numeric format
        }
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    public void setBankType(BankType bankType) {
        this.bankType = bankType;
        this.sortCode = bankType.getSortCode();
    }
}

