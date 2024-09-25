package io.github.torbs00.stock_valuation.controller;

import io.github.torbs00.stock_valuation.domain.Portfolio;
import io.github.torbs00.stock_valuation.domain.PortfolioUser;
import io.github.torbs00.stock_valuation.domain.Stock;
import io.github.torbs00.stock_valuation.domain.StockTransaction;
import io.github.torbs00.stock_valuation.repository.PortfolioRepository;
import io.github.torbs00.stock_valuation.repository.PortfolioUserRepository;
import io.github.torbs00.stock_valuation.repository.StockTransactionRepository;
import io.github.torbs00.stock_valuation.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author TorbS00 on 25.09.2024.
 * @project stock-valuation.
 */
@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioUserRepository portfolioUserRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Autowired
    private StockService stockService;

    @PostMapping("/buy")
    public String buyStock(@RequestParam Long userId, @RequestParam String stockSymbol, @RequestParam int amount) {
        PortfolioUser user = portfolioUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Portfolio portfolio = portfolioRepository.findByUser(user);

        String name = stockService.getStockName(stockSymbol);
        double price = stockService.getStockPrice(stockSymbol);

        Stock stock = new Stock(stockSymbol, name, price);
        StockTransaction transaction = new StockTransaction(portfolio, stock, price, amount, true, LocalDateTime.now());

        stockTransactionRepository.save(transaction);
        return "Bought " + amount + " shares of " + stockSymbol + " at $" + price;
    }

    @PostMapping("/sell")
    public String sellStock(@RequestParam Long userId, @RequestParam String stockSymbol, @RequestParam int amountToSell) {
        PortfolioUser user = portfolioUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Portfolio portfolio = portfolioRepository.findByUser(user);

        List<StockTransaction> transactions = stockTransactionRepository.findByPortfolioAndStockSymbolAndIsBuyTrue(portfolio, stockSymbol);
        int remainingToSell = amountToSell;

        for (StockTransaction transaction : transactions) {
            if (remainingToSell <= 0) break;

            int availableStock = transaction.getAmount();
            if (availableStock <= remainingToSell) {
                remainingToSell -= availableStock;
                transaction.setAmount(0);
            } else {
                transaction.setAmount(availableStock - remainingToSell);
                remainingToSell = 0;
            }
            stockTransactionRepository.save(transaction);
        }

        return "Sold " + amountToSell + " shares of " + stockSymbol;
    }

    @GetMapping("/{userId}")
    public Portfolio viewPortfolio(@PathVariable Long userId) {
        PortfolioUser user = portfolioUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return portfolioRepository.findByUser(user);
    }
}
