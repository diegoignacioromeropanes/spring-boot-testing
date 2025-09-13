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
    private AccountRepository accountRepository;

    public Account createAccount(Long id, String person, BigDecimal balance) {
        Account account = new Account(id, person, balance);
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
}
