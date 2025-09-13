package com.udemy.testing.spring_boot_testing.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Account {
    @Id
    private Long id;
    private String person;
    private BigDecimal balance;

    public Account() {
    }

    public Account(Long id, String person, BigDecimal balance) {
        this.id = id;
        this.person = person;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
