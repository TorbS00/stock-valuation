package io.github.torbs00.stock_valuation.domain;

public class AggregatedStockPosition {

    private String symbol;
    private String name;
    private int totalShares;
    private double totalPurchasePrice;
    private double totalCurrentValue;

    public AggregatedStockPosition(String symbol, String name, int totalShares, double totalPurchasePrice, double totalCurrentValue) {
        this.symbol = symbol;
        this.name = name;
        this.totalShares = totalShares;
        this.totalPurchasePrice = totalPurchasePrice;
        this.totalCurrentValue = totalCurrentValue;
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

    public double getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public void setTotalPurchasePrice(double totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
    }

    public int getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(int totalShares) {
        this.totalShares = totalShares;
    }

    public double getTotalCurrentValue() {
        return totalCurrentValue;
    }

    public void setTotalCurrentValue(double totalCurrentValue) {
        this.totalCurrentValue = totalCurrentValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AggregatedStockPosition that = (AggregatedStockPosition) o;

        if (Double.compare(totalPurchasePrice, that.totalPurchasePrice) != 0) return false;
        if (totalShares != that.totalShares) return false;
        if (Double.compare(totalCurrentValue, that.totalCurrentValue) != 0) return false;
        if (!symbol.equals(that.symbol)) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = symbol.hashCode();
        result = 31 * result + name.hashCode();
        temp = Double.doubleToLongBits(totalPurchasePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + totalShares;
        temp = Double.doubleToLongBits(totalCurrentValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
