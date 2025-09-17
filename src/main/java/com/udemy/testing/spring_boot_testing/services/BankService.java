package com.udemy.testing.spring_boot_testing.services;

import com.udemy.testing.spring_boot_testing.models.Bank;
import com.udemy.testing.spring_boot_testing.repositories.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BankService {
    @Autowired
    BankRepository bankRepository;

    public Bank createBank(Long id, String name, int transferTotal) {
        Bank bank = new Bank(id, name, transferTotal);
        return bankRepository.save(bank);
    }

    public Bank getBank(Long id) {
        Optional<Bank> bank = bankRepository.findById(id);
        return bank.orElse(null);
    }

    public Bank updateBank(Long id, String name, int transferTotal) {
        Optional<Bank> optionalBank = bankRepository.findById(id);
        if (optionalBank.isPresent()) {
            Bank bank = optionalBank.get();
            bank.setName(name);
            bank.setTransferTotal(transferTotal);
            return bankRepository.save(bank);
        }
        return null;
    }

    public Bank deleteBank(Long id) {
        Optional<Bank> optionalBank = bankRepository.findById(id);
        if (optionalBank.isPresent()) {
            bankRepository.deleteById(id);
            return optionalBank.get();
        }
        return null;
    }
}
