package io.github.torbs00.stock_valuation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * @author TorbS00 on 24.09.2024.
 * @project StockMaster.
 */
@Entity
public class PortfolioUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Constructors, Getters, Setters
    public PortfolioUser() {}

    public PortfolioUser(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}