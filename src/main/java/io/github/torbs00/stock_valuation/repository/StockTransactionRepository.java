package io.github.torbs00.stock_valuation.repository;

import io.github.torbs00.stock_valuation.domain.Portfolio;
import io.github.torbs00.stock_valuation.domain.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(value = "SELECT SUM(st.amount * st.purchasePrice) AS totalPurchaseCost " +
            "FROM PortfolioUser u " +
            "JOIN Portfolio p ON u.id = p.user_id " +
            "JOIN StockTransaction st ON p.id = st.portfolio_id " +
            "WHERE u.username = :username AND st.stock.symbol = :symbol " +
            "AND st.amount > 0", nativeQuery = true)
    Double getTotalPurchaseCostForUserAndStock(@Param("username") String username, @Param("symbol") String symbol);

    @Query(value = "SELECT SUM(st.amount) AS totalAmountOwned " +
            "FROM StockTransaction st " +
            "WHERE st.stock.symbol = :symbol " +
            "AND st.isBuy = true", nativeQuery = true)
    Integer getTotalAmountOwnedByStock(@Param("symbol") String symbol);

    @Query(value = "SELECT SUM(st.amount) AS totalAmountOwned " +
            "FROM PortfolioUser u " +
            "JOIN Portfolio p ON u.id = p.user_id " +
            "JOIN StockTransaction st ON p.id = st.portfolio_id " +
            "WHERE u.username = :username " +
            "AND st.stock.symbol = :symbol " +
            "AND st.isBuy = true", nativeQuery = true)
    Integer getTotalAmountOwnedByUserAndStock(@Param("username") String username, @Param("symbol") String symbol);

    @Query(value = "SELECT SUM(CASE WHEN st.isBuy = true THEN st.amount ELSE -st.amount END) AS netAmountOwned " +
            "FROM PortfolioUser u " +
            "JOIN Portfolio p ON u.id = p.user_id " +
            "JOIN StockTransaction st ON p.id = st.portfolio_id " +
            "WHERE u.username = :username " +
            "AND st.stock.symbol = :symbol", nativeQuery = true)
    Integer getNetAmountOwnedByUserAndStock(@Param("username") String username, @Param("symbol") String symbol);

}
