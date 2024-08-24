package com.example.demo.enums;

public enum BankType {
    CHASE(1234L),
    CITI(43210L);

    private final Long sortCode;

    BankType(Long sortCode) {
        this.sortCode = sortCode;
    }

    public Long getSortCode() {
        return sortCode;
    }
}
