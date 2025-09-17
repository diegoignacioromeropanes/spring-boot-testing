package com.udemy.testing.spring_boot_testing.repositories;

import com.udemy.testing.spring_boot_testing.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByPerson(String person);
}
