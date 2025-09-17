package com.udemy.testing.spring_boot_testing.models;

import jakarta.persistence.*;

@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "transfers_total")
    private int transfersTotal;

    public Bank() {
    }

    public Bank(Long id, String name, int transfersTotal) {
        this.id = id;
        this.name = name;
        this.transfersTotal = transfersTotal;
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

    public int getTransfersTotal() {
        return transfersTotal;
    }

    public void setTransfersTotal(int transfersTotal) {
        this.transfersTotal = transfersTotal;
    }
}
