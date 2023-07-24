package com.example.challenge.service;

import com.example.challenge.models.BankAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class BankAccountServiceTest {

    private BankAccountService bankAccountService;
    private final Map<Long, BankAccount> accounts = new HashMap<>();

    @Before
    public void setUp() {
        bankAccountService = new BankAccountService();
        // Prepopulate accounts with the provided data
        accounts.put(1L, new BankAccount(1L, 1000.0));
        accounts.put(2L, new BankAccount(2L, 500.0));
        accounts.put(3L, new BankAccount(3L, 1500.0));
        ReflectionTestUtils.setField(bankAccountService, "accounts", accounts);
    }

    @Test
    public void testGetAccountByIdExisting() {
        BankAccount account = bankAccountService.getAccountById(2L);
        assertNotNull(account);
        assertEquals(2L, account.getId().longValue());
        assertEquals(500.0, account.getBalance(), 0.001);
    }

    @Test
    public void testGetAccountByIdNonExistent() {
        BankAccount account = bankAccountService.getAccountById(999L);
        assertNull(account);
    }

    @Test
    public void testStoreAccountForCustomer() {
        BankAccount newAccount = new BankAccount(4L, 200.0);
        BankAccount storedAccount = bankAccountService.storeAccountForCustomer(1L, newAccount);

        assertNotNull(storedAccount);
        assertEquals(4L, storedAccount.getId().longValue());
        assertEquals(200.0, storedAccount.getBalance(), 0.001);
        assertEquals(1L, storedAccount.getCustomerId().longValue());

        // Check if the account is stored in the accounts map
        BankAccount retrievedAccount = accounts.get(4L);
        assertNotNull(retrievedAccount);
        assertEquals(newAccount, retrievedAccount);
    }
}
