package io.github.torbs00.stock_valuation.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserStockholder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private double totalReturn;

    @OneToMany(mappedBy = "stockholder", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StockPosition> holdings;

    public UserStockholder() {
    }

    public UserStockholder(String username) {
        this.username = username;
        this.totalReturn = 0;
        this.holdings = new ArrayList<>();
    }

    public UserStockholder(String username, double totalReturn, List<StockPosition> holdings) {
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

    public List<StockPosition> getHoldings() {
        return holdings;
    }

    public void setHoldings(List<StockPosition> holdings) {
        this.holdings = holdings;
    }
}
