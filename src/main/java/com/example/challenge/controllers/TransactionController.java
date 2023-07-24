package com.example.challenge.controllers;

import com.example.challenge.models.BankAccount;
import com.example.challenge.models.Transaction;
import com.example.challenge.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    BankAccountService bankAccountService;

    private final List<Transaction> transactions = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Transaction> transferAmount(@RequestBody Transaction transaction) {
        // Check if both accounts exist
        BankAccount fromAccount = bankAccountService.getAccountById(transaction.getFromAccountId());
        BankAccount toAccount = bankAccountService.getAccountById(transaction.getToAccountId());
        if (fromAccount == null || toAccount == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if there are sufficient funds in the from account
        if (fromAccount.getBalance() >= transaction.getAmount()) {
            // Perform the transfer
            fromAccount.setBalance(fromAccount.getBalance() - transaction.getAmount());
            toAccount.setBalance(toAccount.getBalance() + transaction.getAmount());

            // Update transaction history
            transaction.setTimestamp(LocalDateTime.now());
            transactions.add(transaction);

            return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactions);
    }
}
