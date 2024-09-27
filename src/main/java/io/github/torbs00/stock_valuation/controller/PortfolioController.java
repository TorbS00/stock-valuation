package io.github.torbs00.stock_valuation.controller;

import io.github.torbs00.stock_valuation.domain.Portfolio;
import io.github.torbs00.stock_valuation.domain.PortfolioUser;
import io.github.torbs00.stock_valuation.domain.Stock;
import io.github.torbs00.stock_valuation.domain.StockTransaction;
import io.github.torbs00.stock_valuation.repository.PortfolioRepository;
import io.github.torbs00.stock_valuation.repository.PortfolioUserRepository;
import io.github.torbs00.stock_valuation.repository.StockTransactionRepository;
import io.github.torbs00.stock_valuation.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * @author TorbS00 on 25.09.2024.
 * @project stock-valuation.
 */
@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioUserRepository portfolioUserRepository;
    private final StockTransactionRepository stockTransactionRepository;
    private final StockService stockService;

    public PortfolioController(PortfolioRepository portfolioRepository, PortfolioUserRepository portfolioUserRepository, StockTransactionRepository stockTransactionRepository, StockService stockService) {
        this.portfolioRepository = portfolioRepository;
        this.portfolioUserRepository = portfolioUserRepository;
        this.stockTransactionRepository = stockTransactionRepository;
        this.stockService = stockService;
    }

    @PostMapping("/buy")
    public String buyStock(@RequestParam String username, @RequestParam String stockSymbol,
                           @RequestParam int amount, Model model, RedirectAttributes redirectAttributes) {
        PortfolioUser user = portfolioUserRepository.findByName(username);
        Portfolio portfolio = portfolioRepository.findByUser(user);
        model.addAttribute("notfound", null);
        try {
            double price = stockService.getStockPrice(stockSymbol);
            String stockName = stockService.getStockName(stockSymbol);
            StockTransaction transaction = new StockTransaction(portfolio, new Stock(stockSymbol, stockName, price), price, amount, true, LocalDateTime.now());
            stockTransactionRepository.save(transaction);
        } catch (Exception swallowed) {
            redirectAttributes.addFlashAttribute("notfound", "Found no stock with the provided ticker value");
            return "redirect:/portfolio/" + user.getName();
        }
        return "redirect:/portfolio/" + username;
    }

    @PostMapping("/sell")
    public String sellStock(@RequestParam String username, @RequestParam String stockSymbol,
                            @RequestParam int amountToSell, Model model) {
        PortfolioUser user = portfolioUserRepository.findByName(username);
        Portfolio portfolio = portfolioRepository.findByUser(user);

        int[] remainingToSell = { amountToSell };

        stockTransactionRepository.findByPortfolioAndStockSymbolAndIsBuyTrue(portfolio, stockSymbol)
                .forEach(transaction -> {
                    if (remainingToSell[0] > 0) {
                        int available = transaction.getAmount();
                        if (available <= remainingToSell[0]) {
                            transaction.setAmount(0);
                            remainingToSell[0] -= available;
                        } else {
                            transaction.setAmount(available - remainingToSell[0]);
                            remainingToSell[0] = 0;
                        }
                        stockTransactionRepository.save(transaction);
                    }
                });

        model.addAttribute("portfolio", portfolio);
        return "redirect:/portfolio/" + username;
    }

    @GetMapping("/{username}")
    public String showPortfolioByName(@PathVariable String username, Model model) {
        PortfolioUser user = portfolioUserRepository.findByName(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Portfolio portfolio = portfolioRepository.findByUser(user);

        if (portfolio.getTransactions() == null) {
            portfolio.setTransactions(new ArrayList<>());
        }

        model.addAttribute("portfolio", portfolio);
        model.addAttribute("username", username);

        return "portfolio";
    }

}
