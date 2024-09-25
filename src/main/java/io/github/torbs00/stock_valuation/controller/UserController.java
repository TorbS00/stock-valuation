package io.github.torbs00.stock_valuation.controller;

import io.github.torbs00.stock_valuation.domain.Portfolio;
import io.github.torbs00.stock_valuation.domain.PortfolioUser;
import io.github.torbs00.stock_valuation.repository.PortfolioRepository;
import io.github.torbs00.stock_valuation.repository.PortfolioUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author TorbS00 on 25.09.2024.
 * @project stock-valuation.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PortfolioUserRepository portfolioUserRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @PostMapping("/login")
    public String loginUser(@RequestParam String username) {
        PortfolioUser user = portfolioUserRepository.findByName(username);
        if (user == null) {
            user = new PortfolioUser(username);
            portfolioUserRepository.save(user);

            Portfolio portfolio = new Portfolio(user);
            portfolioRepository.save(portfolio);

            return "New user " + username + " created!";
        } else {
            Portfolio portfolio = portfolioRepository.findByUser(user);
            return "User " + username + " loaded with portfolio.";
        }
    }

    @GetMapping("/{id}")
    public PortfolioUser getUserById(@PathVariable Long id) {
        return portfolioUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/by-username")
    public PortfolioUser getUserByUsername(@RequestParam String username) {
        return portfolioUserRepository.findByName(username);
    }
}
