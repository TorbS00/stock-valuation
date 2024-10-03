package io.github.torbs00.stock_valuation.domain;

import jakarta.persistence.*;

@Entity
public class StockPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "stockholder_id")
    private UserStockholder stockholder;

    private String symbol;
    private String name;
    private int shares;
    private double singleSharePrice;
    private double currentPrice;

    public StockPosition() {
    }

    public StockPosition(UserStockholder stockholder, String symbol, String name, int shares, double singleSharePrice, double currentPrice) {
        this.stockholder = stockholder;
        this.symbol = symbol;
        this.name = name;
        this.shares = shares;
        this.singleSharePrice = singleSharePrice;
        this.currentPrice = currentPrice;
    }

    public UserStockholder getStockholder() {
        return stockholder;
    }

    public void setStockholder(UserStockholder stockholder) {
        this.stockholder = stockholder;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public double getSingleSharePrice() {
        return singleSharePrice;
    }

    public void setSingleSharePrice(double singleStockPrice) {
        this.singleSharePrice = singleStockPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getTotalPurchasePrice() {
        return shares * singleSharePrice;
    }
}
