package io.github.torbs00.stock_valuation.repository;

import io.github.torbs00.stock_valuation.domain.Portfolio;
import io.github.torbs00.stock_valuation.domain.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

    /**
     * Gets all StockTransactions from Portfolio with same ticker.
     *
     * @param portfolio
     * @param stockSymbol
     * @return List of StockTransaction's
     */
    List<StockTransaction> findByPortfolioAndStockSymbol(Portfolio portfolio, String stockSymbol);

    /**
     * Gets all buy StockTransactions from Portfolio with same ticker
     *
     * @param portfolio
     * @param stockSymbol
     * @return List of StockTransaction's
     */
    List<StockTransaction> findByPortfolioAndStockSymbolAndIsBuyTrue(Portfolio portfolio, String stockSymbol);
}
