package com.example.demo.enums;

import lombok.Getter;

@Getter
public enum BankType {
    CHASE("1234"),
    CITI("43210"),
    DEFAULT("3435436");  // Default sort code or a special value indicating a default type

    private final String sortCode;

    BankType(String sortCode) {
        this.sortCode = sortCode;
    }


    public static BankType getDefault() {
        return DEFAULT;
    }
}
