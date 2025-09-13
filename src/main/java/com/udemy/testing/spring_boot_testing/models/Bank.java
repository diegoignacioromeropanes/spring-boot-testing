package com.udemy.testing.spring_boot_testing.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Bank {
    @Id
    private Long id;
    private String name;
    private int transferTotal;

    public Bank() {
    }

    public Bank(Long id, String name, int transferTotal) {
        this.id = id;
        this.name = name;
        this.transferTotal = transferTotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTransferTotal() {
        return transferTotal;
    }

    public void setTransferTotal(int transferTotal) {
        this.transferTotal = transferTotal;
    }
}
