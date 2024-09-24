package io.github.torbs00.stock_valuation.domain;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author TorbS00 on 24.09.2024.
 * @project StockMaster.
 */
@Entity
public class Portfolio {

    @Id
    private Long id;  // Manually set the same ID as PortfolioUser

    @OneToOne
    @MapsId  // Maps the same ID as the PortfolioUser's ID
    private PortfolioUser user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<StockTransaction> transactions;  // List of stock transactions for the portfolio

    // Constructors, Getters, Setters
    public Portfolio() {}

    public Portfolio(PortfolioUser user) {
        this.user = user;
        this.id = user.getId();  // Set the ID to be the same as PortfolioUser
    }

    public Long getId() {
        return id;
    }

    public PortfolioUser getUser() {
        return user;
    }

    public void setUser(PortfolioUser user) {
        this.user = user;
    }

    public List<StockTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<StockTransaction> transactions) {
        this.transactions = transactions;
    }
}
