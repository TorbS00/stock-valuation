package io.github.torbs00.stock_valuation.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * @author TorbS00 on 24.09.2024.
 * @project stock-valuation.
 */
@Entity
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Embedded
    private Stock stock;

    private double purchasePrice;
    private int amount;
    private boolean isBuy;
    private LocalDateTime transactionTime;

    public StockTransaction() {}

    public StockTransaction(Portfolio portfolio, Stock stock, double purchasePrice, int amount, boolean isBuy, LocalDateTime transactionTime) {
        this.portfolio = portfolio;
        this.stock = stock;
        this.purchasePrice = purchasePrice;
        this.amount = amount;
        this.isBuy = isBuy;
        this.transactionTime = transactionTime;
    }

    public Long getId() {
        return id;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }
}
