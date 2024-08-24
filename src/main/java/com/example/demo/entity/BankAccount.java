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
    private String accountNumber;  // Considered using UUID or custom format

    @Enumerated(EnumType.STRING)
    private BankType bankType;  // Enum for bank type

    @Column(nullable = false)
    private Long sortCode;  // Set by the bank type

    private String name;  // Account name
    private BigDecimal openingBalance;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;  // Enum for account status

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TransactionRecord> transactionRecords;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        if (accountNumber == null) {
            accountNumber = UUID.randomUUID().toString(); // Or generate in a custom numeric format
        }
    }

    // Set the bank type and automatically update the sort code
    public void setBankType(BankType bankType) {
        this.bankType = bankType;
        this.sortCode = bankType.getSortCode();
    }
}
