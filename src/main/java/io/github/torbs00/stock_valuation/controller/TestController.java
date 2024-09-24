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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author TorbS00 on 25.09.2024.
 * @project stock-valuation.
 */
@RestController
public class TestController {

    @Autowired
    private PortfolioUserRepository portfolioUserRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Autowired
    private StockService stockService;

    @GetMapping("/create-user")
    public String createUser(@RequestParam String username) {
        PortfolioUser user = new PortfolioUser(username);
        portfolioUserRepository.save(user);

        Portfolio portfolio = new Portfolio(user);
        portfolioRepository.save(portfolio);

        return "User " + username + " and portfolio created!";
    }

    @GetMapping("/buy-stock")
    public String buyStock(@RequestParam Long userId, @RequestParam String stockSymbol, @RequestParam int amount) {
        PortfolioUser user = portfolioUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Portfolio portfolio = portfolioRepository.findByUser(user);

        double price = stockService.getStockPrice(stockSymbol);

        Stock stock = new Stock(stockSymbol, "Test Stock", price);  // Example stock name, can replace with actual API data
        StockTransaction transaction = new StockTransaction(portfolio, stock, price, amount, true, LocalDateTime.now());
        stockTransactionRepository.save(transaction);

        return "Bought " + amount + " shares of " + stockSymbol + " at $" + price;
    }

    @GetMapping("/get-portfolio")
    public Portfolio getPortfolio(@RequestParam Long userId) {
        PortfolioUser user = portfolioUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return portfolioRepository.findByUser(user);
    }
}
