package io.github.torbs00.stock_valuation.service;

import io.github.torbs00.stock_valuation.domain.AggregatedStockPosition;
import io.github.torbs00.stock_valuation.domain.StockPosition;
import io.github.torbs00.stock_valuation.domain.UserStockholder;
import io.github.torbs00.stock_valuation.repository.StockPositionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {

    private final StockPositionRepository stockPositionRepository;

    public PortfolioService(StockPositionRepository stockPositionRepository) {
        this.stockPositionRepository = stockPositionRepository;
    }

    public List<AggregatedStockPosition> getAggregatedPortfolio(UserStockholder stockholder) {
        List<StockPosition> positions = stockPositionRepository.findByStockholder(stockholder);

        Map<String, AggregatedStockPosition> aggregatedPositions = new HashMap<>();

        for (StockPosition position : positions) {
            String symbol = position.getSymbol();
            String name = position.getName();
            int shares = position.getShares();
            double totalPurchasePrice = position.getTotalPurchasePrice();

            if (aggregatedPositions.containsKey(symbol)) {
                AggregatedStockPosition existing = aggregatedPositions.get(symbol);
                existing.setTotalShares(existing.getTotalShares() + shares);
                existing.setTotalPurchasePrice(existing.getTotalPurchasePrice() + totalPurchasePrice);
                existing.setTotalCurrentValue(existing.getTotalPurchasePrice());
            } else {
                aggregatedPositions.put(symbol, new AggregatedStockPosition(symbol, name, shares, totalPurchasePrice, totalPurchasePrice));
            }
        }

        List<AggregatedStockPosition> aggregatedPortfolio = new ArrayList<>(aggregatedPositions.values());
        aggregatedPortfolio.sort(Comparator.comparing(AggregatedStockPosition::getSymbol));

        return aggregatedPortfolio;
    }

}
