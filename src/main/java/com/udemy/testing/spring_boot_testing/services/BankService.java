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

    public Optional<Bank> createBank(String name, int transferTotal) {
        Bank bank = new Bank(name, transferTotal);
        return Optional.of(bankRepository.save(bank));
    }

    public Optional<Bank> getBank(Long id) {
        return bankRepository.findById(id);
    }

    public Optional<Bank> updateBank(Long id, String name, int transferTotal) {
        Optional<Bank> optionalBank = bankRepository.findById(id);
        if (optionalBank.isPresent()) {
            Bank bank = optionalBank.get();
            bank.setName(name);
            bank.setTransfersTotal(transferTotal);
            return Optional.of(bankRepository.save(bank));
        }
        return Optional.empty();
    }

    public Optional<Bank> deleteBank(Long id) {
        Optional<Bank> optionalBank = bankRepository.findById(id);
        if (optionalBank.isPresent()) {
            bankRepository.deleteById(id);
            return optionalBank;
        }
        return Optional.empty();
    }
}
