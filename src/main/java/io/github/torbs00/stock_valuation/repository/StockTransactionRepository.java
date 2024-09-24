package io.github.torbs00.stock_valuation.repository;

/**
 * @author TorbS00 on 24.09.2024.
 * @project StockMaster.
 */
import io.github.torbs00.stock_valuation.domain.Portfolio;
import io.github.torbs00.stock_valuation.domain.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

    List<StockTransaction> findByPortfolioAndStockSymbol(Portfolio portfolio, String stockSymbol);

    List<StockTransaction> findByPortfolioAndStockSymbolAndIsBuyTrue(Portfolio portfolio, String stockSymbol);
}
