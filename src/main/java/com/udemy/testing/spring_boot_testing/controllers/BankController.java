package com.udemy.testing.spring_boot_testing.controllers;

import com.udemy.testing.spring_boot_testing.models.Bank;
import com.udemy.testing.spring_boot_testing.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banks")
public class BankController {
    @Autowired
    private BankService bankService;

    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBank(@PathVariable Long id) {
        Bank bank = bankService.getBank(id);
        if (bank == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bank);
    }

    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        Bank created = bankService.createBank(bank.getId(), bank.getName(), bank.getTransferTotal());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable Long id, @RequestBody Bank bank) {
        Bank updated = bankService.updateBank(id, bank.getName(), bank.getTransferTotal());
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Bank> deleteBank(@PathVariable Long id) {
        Bank deleted = bankService.deleteBank(id);
        if (deleted == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(deleted);
    }
}

