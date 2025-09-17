package com.udemy.testing.spring_boot_testing.repositories;

import com.udemy.testing.spring_boot_testing.models.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}

