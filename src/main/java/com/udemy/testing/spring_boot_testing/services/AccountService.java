package com.udemy.testing.spring_boot_testing.services;

import com.udemy.testing.spring_boot_testing.models.Account;
import com.udemy.testing.spring_boot_testing.repositories.AccountRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> createAccount(String person, BigDecimal balance) {
        Account account = new Account(person, balance);
        return Optional.of(accountRepository.save(account));
    }

    public Optional<Account> getAccount(Long id) {
        return accountRepository.findById(id);
    }

    public Optional<Account> updateAccount(Long id, String person, BigDecimal balance) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setPerson(person);
            account.setBalance(balance);
            return Optional.of(accountRepository.save(account));
        }
        return Optional.empty();
    }

    public Optional<Account> deleteAccount(Long id) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            accountRepository.deleteById(id);
            return optionalAccount;
        }
        return Optional.empty();
    }

    // Additional method to transfer money between accounts
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Optional<Account> fromAccountOpt = getAccount(fromAccountId);
        Optional<Account> toAccountOpt = getAccount(toAccountId);
        if (!fromAccountOpt.isPresent() || !toAccountOpt.isPresent()) {
            throw new IllegalArgumentException("One of the accounts does not exist");
        }
        Account fromAccount = fromAccountOpt.get();
        Account toAccount = toAccountOpt.get();
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in the source account");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
