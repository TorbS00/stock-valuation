package io.github.torbs00.stock_valuation.service;

import io.github.torbs00.stock_valuation.domain.AggregatedStockPosition;
import io.github.torbs00.stock_valuation.domain.StockPosition;
import io.github.torbs00.stock_valuation.domain.Stockholder;
import io.github.torbs00.stock_valuation.repository.StockPositionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {

    private final StockPositionRepository stockPositionRepository;
    private final StockService stockService;

    public PortfolioService(StockPositionRepository stockPositionRepository, StockService stockService) {
        this.stockPositionRepository = stockPositionRepository;
        this.stockService = stockService;
    }

    public List<AggregatedStockPosition> getAggregatedPortfolio(Stockholder stockholder) {
        List<StockPosition> positions = stockPositionRepository.findByStockholder(stockholder);

        Map<String, AggregatedStockPosition> aggregatedPositions = new HashMap<>();

        for (StockPosition position : positions) {

            String symbol = position.getSymbol();
            String name = position.getName();
            int shares = position.getShares();
            double totalPurchasePrice = position.getTotalPurchasePrice();
            double currentValue = 0;
            try {
                currentValue = stockService.getStockPrice(symbol);
            } catch (RuntimeException ex) {
                throw new RuntimeException("Stock is not listed anymore", ex);
            }
            double totalCurrentValue = shares * currentValue;

            if (aggregatedPositions.containsKey(symbol)) {
                AggregatedStockPosition existing = aggregatedPositions.get(symbol);
                existing.setTotalShares(existing.getTotalShares() + shares);
                existing.setTotalPurchasePrice(existing.getTotalPurchasePrice() + totalPurchasePrice);
                existing.setTotalCurrentValue(existing.getTotalCurrentValue() + totalCurrentValue);
            } else {
                aggregatedPositions.put(symbol, new AggregatedStockPosition(symbol, name, shares, totalPurchasePrice, totalCurrentValue));
            }
        }

        List<AggregatedStockPosition> aggregatedPortfolio = new ArrayList<>(aggregatedPositions.values());
        aggregatedPortfolio.sort(Comparator.comparing(AggregatedStockPosition::getSymbol));

        return aggregatedPortfolio;
    }

}
