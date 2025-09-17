package com.udemy.testing.spring_boot_testing.services;

import com.udemy.testing.spring_boot_testing.models.Account;
import com.udemy.testing.spring_boot_testing.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        Account account = new Account(1L, "Alice", new BigDecimal("100"));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.createAccount(1L, "Alice", new BigDecimal("100"));

        assertNotNull(result);
        assertEquals("Alice", result.getPerson());
        assertEquals(new BigDecimal("100"), result.getBalance());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void testGetAccountFound() {
        Account account = new Account(1L, "Alice", new BigDecimal("100"));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountService.getAccount(1L);

        assertNotNull(result);
        assertEquals("Alice", result.getPerson());
    }

    @Test
    void testGetAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Account result = accountService.getAccount(1L);

        assertNull(result);
    }

    @Test
    void testUpdateAccountFound() {
        Account account = new Account(1L, "Alice", new BigDecimal("100"));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account result = accountService.updateAccount(1L, "Alice Updated", new BigDecimal("200"));

        assertNotNull(result);
        assertEquals("Alice Updated", result.getPerson());
        assertEquals(new BigDecimal("200"), result.getBalance());
        verify(accountRepository).save(account);
    }

    @Test
    void testUpdateAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Account result = accountService.updateAccount(1L, "Alice Updated", new BigDecimal("200"));

        assertNull(result);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void testDeleteAccountFound() {
        Account account = new Account(1L, "Alice", new BigDecimal("100"));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Account result = accountService.deleteAccount(1L);

        assertNotNull(result);
        assertEquals("Alice", result.getPerson());
        verify(accountRepository).deleteById(1L);
    }

    @Test
    void testDeleteAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Account result = accountService.deleteAccount(1L);

        assertNull(result);
        verify(accountRepository, never()).deleteById(anyLong());
    }

    @Test
    void testTransferSuccess() {
        Account from = new Account(1L, "Alice", new BigDecimal("100"));
        Account to = new Account(2L, "Bob", new BigDecimal("200"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(from));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(to));

        accountService.transfer(1L, 2L, new BigDecimal("50"));

        assertEquals(new BigDecimal("50"), from.getBalance());
        assertEquals(new BigDecimal("250"), to.getBalance());
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @Test
    void testTransferFailsWhenInsufficientFunds() {
        Account from = new Account(1L, "Alice", new BigDecimal("10"));
        Account to = new Account(2L, "Bob", new BigDecimal("200"));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(from));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(to));

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> accountService.transfer(1L, 2L, new BigDecimal("50"))
        );

        assertEquals("Insufficient funds in the source account", ex.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void testTransferFailsWhenAccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> accountService.transfer(1L, 2L, new BigDecimal("50"))
        );

        assertEquals("One of the accounts does not exist", ex.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }
}