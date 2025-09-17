package com.udemy.testing.spring_boot_testing.services;

import com.udemy.testing.spring_boot_testing.models.Account;
import com.udemy.testing.spring_boot_testing.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;

    public Account createAccount(String person, BigDecimal balance) {
        Account account = new Account(person, balance);
        return accountRepository.save(account);
    }

    public Account getAccount(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.orElse(null);
    }

    public Account updateAccount(Long id, String person, BigDecimal balance) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setPerson(person);
            account.setBalance(balance);
            return accountRepository.save(account);
        }
        return null;
    }

    public Account deleteAccount(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            accountRepository.deleteById(id);
            return optionalAccount.get();
        }
        return null;
    }

    // Additional method to transfer money between accounts
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Account fromAccount = getAccount(fromAccountId);
        Account toAccount = getAccount(toAccountId);
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("One of the accounts does not exist");
        }

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in the source account");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
