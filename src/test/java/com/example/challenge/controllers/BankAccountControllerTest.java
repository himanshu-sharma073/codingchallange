package com.example.challenge.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.challenge.models.BankAccount;
import com.example.challenge.models.Transaction;
import com.example.challenge.service.BankAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(BankAccountController.class)
public class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService bankAccountService;

    @Test
    public void testCreateBankAccount() throws Exception {
        BankAccount bankAccount = new BankAccount(101L, 1L, 500.0);

        given(bankAccountService.storeAccountForCustomer(anyLong(), any(BankAccount.class))).willReturn(bankAccount);

        String requestBody = "{\"id\": 101, \"customerId\": 1, \"balance\": 500.0}";

        mockMvc.perform(post("/accounts")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.balance").value(500.0));
    }

    @Test
    public void testGetBankAccount() throws Exception {
        BankAccount bankAccount = new BankAccount(101L, 1L, 500.0);

        given(bankAccountService.getAccountById(101L)).willReturn(bankAccount);

        mockMvc.perform(get("/accounts/{id}", 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(101))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.balance").value(500.0));
    }

    @Test
    public void testGetNonExistentAccount() throws Exception {
        given(bankAccountService.getAccountById(999L)).willReturn(null);

        mockMvc.perform(get("/accounts/{id}", 999))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetBalance() throws Exception {
        BankAccount bankAccount = new BankAccount(101L, 1L, 500.0);

        given(bankAccountService.getAccountById(101L)).willReturn(bankAccount);

        mockMvc.perform(get("/accounts/{id}/balance", 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(500.0));
    }

    @Test
    public void testGetTransactionHistory() throws Exception {
        BankAccount bankAccount = new BankAccount(101L, 1L, 500.0);
        Transaction transaction1 = new Transaction(1L, 101L, 102L, 100.0);
        Transaction transaction2 = new Transaction(2L, 101L, 103L, 200.0);
        bankAccount.getTransactionHistory().add(transaction1);
        bankAccount.getTransactionHistory().add(transaction2);

        given(bankAccountService.getAccountById(101L)).willReturn(bankAccount);

        mockMvc.perform(get("/accounts/{id}/transactions", 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].fromAccountId").value(101))
                .andExpect(jsonPath("$[0].toAccountId").value(102))
                .andExpect(jsonPath("$[0].amount").value(100.0))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].fromAccountId").value(101))
                .andExpect(jsonPath("$[1].toAccountId").value(103))
                .andExpect(jsonPath("$[1].amount").value(200.0));
    }
}
