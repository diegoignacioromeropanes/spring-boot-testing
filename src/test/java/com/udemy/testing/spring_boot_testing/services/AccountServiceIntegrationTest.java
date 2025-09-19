package com.udemy.testing.spring_boot_testing.services;

import com.udemy.testing.spring_boot_testing.models.Account;
import com.udemy.testing.spring_boot_testing.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AccountServiceIntegrationTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Test
    void testCreateAndGetAccount() {
        Optional<Account> createdOpt = accountService.createAccount("Diego", new BigDecimal("100.00"));
        assertTrue(createdOpt.isPresent());
        Account created = createdOpt.get();

        Optional<Account> accountOpt = accountService.getAccount(created.getId());
        assertTrue(accountOpt.isPresent());
        Account account = accountOpt.get();

        assertEquals("Diego", account.getPerson());
        assertEquals(new BigDecimal("100.00"), account.getBalance());
    }

    @Test
    void testUpdateAccount() {
        Optional<Account> updatedOpt = accountService.updateAccount(1L, "Alice Updated", new BigDecimal("200"));
        assertTrue(updatedOpt.isPresent());
        Account updated = updatedOpt.get();

        assertEquals("Alice Updated", updated.getPerson());
        assertEquals(new BigDecimal("200"), updated.getBalance());
    }

    @Test
    void testDeleteAccount() {
        assertTrue(accountService.deleteAccount(1L));
        assertTrue(accountService.getAccount(1L).isEmpty());
    }

    @Test
    void testTransferSuccess() {
        Account fromAccount = accountService.getAccount(1L).get();
        Account toAccount = accountService.getAccount(2L).get();
        accountService.transfer(fromAccount.getId(), toAccount.getId(), new BigDecimal("50"));

        fromAccount = accountService.getAccount(1L).get();
        toAccount = accountService.getAccount(2L).get();

        assertEquals(new BigDecimal("950.00"), fromAccount.getBalance());
        assertEquals(new BigDecimal("550.00"), toAccount.getBalance());
    }

    @Test
    void testTransferFailsWhenInsufficientFunds() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                accountService.transfer(1L, 2L, new BigDecimal("1500"))
        );
        assertEquals("Insufficient funds in the source account", ex.getMessage());
    }

    @Test
    void testTransferFailsWhenAccountNotFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () ->
                accountService.transfer(1L, 999L, new BigDecimal("50"))
        );
        assertEquals("One of the accounts does not exist", ex.getMessage());
    }
}