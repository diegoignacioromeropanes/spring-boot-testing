package com.udemy.testing.spring_boot_testing.repositories;

import com.udemy.testing.spring_boot_testing.models.Bank;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends CrudRepository<Bank, Long> {
}

