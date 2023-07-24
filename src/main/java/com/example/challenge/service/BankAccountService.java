package com.example.challenge.service;

import com.example.challenge.models.BankAccount;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BankAccountService {
    private final Map<Long, BankAccount> accounts = new HashMap<>();

    // Method to get an account by account ID
    public BankAccount getAccountById(Long accountId) {
        return accounts.get(accountId);
    }

    // Method to store accounts for a customer
    public BankAccount storeAccountForCustomer(Long customerId, BankAccount bankAccount) {
        bankAccount.setCustomerId(customerId);
        accounts.put(bankAccount.getId(), bankAccount);
        return bankAccount;
    }
}
