package com.example.challenge.controllers;

import com.example.challenge.models.BankAccount;
import com.example.challenge.models.Transaction;
import com.example.challenge.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;

    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) {
        bankAccountService.storeAccountForCustomer(bankAccount.getId(), bankAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getBankAccount(@PathVariable Long id) {
        BankAccount account = bankAccountService.getAccountById(id);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable Long id) {
        BankAccount account = bankAccountService.getAccountById(id);
        if (account != null) {
            return ResponseEntity.ok(account.getBalance());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable Long id) {
        BankAccount account = bankAccountService.getAccountById(id);
        if (account != null) {
            return ResponseEntity.ok(account.getTransactionHistory());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
