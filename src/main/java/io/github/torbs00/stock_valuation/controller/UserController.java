package io.github.torbs00.stock_valuation.controller;

import io.github.torbs00.stock_valuation.domain.Portfolio;
import io.github.torbs00.stock_valuation.domain.PortfolioUser;
import io.github.torbs00.stock_valuation.repository.PortfolioRepository;
import io.github.torbs00.stock_valuation.repository.PortfolioUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author TorbS00 on 25.09.2024.
 * @project stock-valuation.
 */
@Controller
public class UserController {

    @Autowired
    private PortfolioUserRepository portfolioUserRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, Model model) {
        PortfolioUser user = portfolioUserRepository.findByName(username);
        Portfolio portfolio;

        if (user == null) {
            user = new PortfolioUser(username);
            portfolioUserRepository.save(user);
            portfolio = new Portfolio(user);
            portfolioRepository.save(portfolio);
            model.addAttribute("message", "New portfolio created for " + username);
        } else {
            portfolio = portfolioRepository.findByUser(user);
            model.addAttribute("message", "Welcome back, " + username);
        }

        model.addAttribute("portfolio", portfolio);
        return "redirect:/portfolio/" + user.getName();
    }

    @GetMapping("/{id}")
    public PortfolioUser getUserById(@PathVariable Long id) {
        return portfolioUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping("/by-username")
    public PortfolioUser getUserByUsername(@RequestParam String username) {
        return portfolioUserRepository.findByName(username);
    }
}
