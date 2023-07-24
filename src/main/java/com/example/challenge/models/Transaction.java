package com.example.challenge.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {
    private Long id;
    private Long fromAccountId;
    private Long toAccountId;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(long id, long fromAccountId, long toAccountId, double amount) {
        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }
    public Transaction(long fromAccountId, long toAccountId, double amount) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
    }
}
