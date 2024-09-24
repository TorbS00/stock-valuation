package io.github.torbs00.stock_valuation.domain;

/**
 * @author TorbS00 on 24.09.2024.
 * @project StockMaster.
 */

import jakarta.persistence.*;

@Embeddable
public class Stock {

    private String symbol;  // Stock symbol (e.g., AAPL)
    private String name;    // Company name
    private double currentPrice;  // Real-time price

    // Constructors, Getters, Setters
    public Stock() {}

    public Stock(String symbol, String name, double currentPrice) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
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

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }
}
