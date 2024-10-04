package io.github.torbs00.stock_valuation.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Stockholder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private double totalReturn;

    @OneToMany(mappedBy = "stockholder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StockPosition> holdings;

    public Stockholder() {
    }

    public Stockholder(String username) {
        this.username = username;
        this.totalReturn = 0;
        this.holdings = new ArrayList<>();
    }

    public Stockholder(String username, double totalReturn, List<StockPosition> holdings) {
        this.username = username;
        this.totalReturn = totalReturn;
        this.holdings = holdings;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getTotalReturn() {
        return totalReturn;
    }

    public void setTotalReturn(double totalReturn) {
        this.totalReturn = totalReturn;
    }

    public List<StockPosition> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<StockPosition> holdings) {
        this.holdings = holdings;
    }
}
