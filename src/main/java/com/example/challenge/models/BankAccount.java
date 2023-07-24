package com.example.challenge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    private Long id;
    private Long customerId;
    private double balance;
    private List<Transaction> transactionHistory = new ArrayList<>();

    public BankAccount(long id, long customerId, double balance) {
        this.id = id;
        this.customerId = customerId;
        this.balance = balance;
    }

    public BankAccount(long id, double balance) {
        this.id = id;
        this.balance = balance;
    }
}
