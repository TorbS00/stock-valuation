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
 * @author TorbS00 on 24.09.2024.
 * @project StockMaster.
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

    // Endpoint to create a new user and portfolio
    @GetMapping("/create-user")
    public String createUser(@RequestParam String username) {
        PortfolioUser user = new PortfolioUser(username);
        portfolioUserRepository.save(user);

        // Create and save portfolio for the user
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolioRepository.save(portfolio);

        return "User " + username + " and portfolio created!";
    }

    // Endpoint to add a stock transaction (buy stock)
    @GetMapping("/buy-stock")
    public String buyStock(@RequestParam Long userId, @RequestParam String stockSymbol, @RequestParam int amount) {
        // Fetch the user and portfolio
        PortfolioUser user = portfolioUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Portfolio portfolio = portfolioRepository.findByUser(user);

        // Fetch real-time stock price using StockService
        double price = stockService.getStockPrice(stockSymbol);

        // Create and save the stock transaction
        Stock stock = new Stock(stockSymbol, "Test Stock", price);  // Example name, you can replace with API data
        StockTransaction transaction = new StockTransaction(portfolio, stock, price, amount, true, LocalDateTime.now());
        stockTransactionRepository.save(transaction);

        return "Bought " + amount + " shares of " + stockSymbol + " at $" + price;
    }
}
