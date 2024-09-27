package io.github.torbs00.stock_valuation.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Portfolio {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private PortfolioUser user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StockTransaction> transactions;

    public Portfolio() {}

    public Portfolio(PortfolioUser user) {
        this.user = user;
        this.id = user.getId();
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
