package com.example.demo.entity;

import com.example.demo.enums.AccountStatus;
import com.example.demo.enums.BankType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long accountNumber;

    @Enumerated(EnumType.STRING)
    private BankType bankType;  // Will be set automatically

    @Column(nullable = false)
    private String sortCode;  // Will be set automatically

    private String name;  // Account name

    private BigDecimal openingBalance;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;  // Will be set automatically

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @PrePersist
    protected void onCreate() {
        System.out.println("onCreate method called");
        createdAt = new Date();
        if (accountNumber == null) {
            UUID uuid = UUID.randomUUID();  // Generate a UUID

            // Get the most significant bits and ensure it's positive
            long mostSigBits = uuid.getMostSignificantBits();
            long positiveBits = mostSigBits & Long.MAX_VALUE;  // Convert to positive if necessary

            // Optionally, reduce the range of values if needed
            // For instance, by using modulo to ensure it fits a specific range
            accountNumber = positiveBits % 1000000000L; // Example: Limiting to a billion (10 digits)

            System.out.println("Generated accountNumber: " + accountNumber);  // Debugging
        }
        if (bankType != null) {
            sortCode = bankType.getSortCode();  // Set sortCode based on bankType
        } else {
            throw new IllegalStateException("BankType must be set before persisting.");
        }
        if (status == null) {
            status = AccountStatus.CREATED; // Default status
        }
    }

    // Use this method to set bankType and ensure sortCode is updated
    public void setBankType(BankType bankType) {
        this.bankType = bankType;
        if (bankType != null) {
            this.sortCode = bankType.getSortCode();
        }
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", accountNumber=" + accountNumber +
                ", bankType=" + bankType +
                ", sortCode='" + sortCode + '\'' +
                ", name='" + name + '\'' +
                ", openingBalance=" + openingBalance +
                ", balance=" + balance +
                ", status=" + status +
                ", customer=" + customer +
                ", createdAt=" + createdAt +
                '}';
    }
}
