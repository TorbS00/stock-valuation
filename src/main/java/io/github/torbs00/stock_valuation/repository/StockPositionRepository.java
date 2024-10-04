package io.github.torbs00.stock_valuation.repository;

import io.github.torbs00.stock_valuation.domain.StockPosition;
import io.github.torbs00.stock_valuation.domain.Stockholder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockPositionRepository extends JpaRepository<StockPosition, Long> {

    List<StockPosition> findByStockholderAndAndSymbol(Stockholder stockholder, String symbol);

    List<StockPosition> findByStockholder(Stockholder stockholder);

}
