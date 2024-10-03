package io.github.torbs00.stock_valuation.controller;

import io.github.torbs00.stock_valuation.domain.AggregatedStockPosition;
import io.github.torbs00.stock_valuation.domain.StockPosition;
import io.github.torbs00.stock_valuation.domain.UserStockholder;
import io.github.torbs00.stock_valuation.repository.StockPositionRepository;
import io.github.torbs00.stock_valuation.repository.UserStockholderRepository;
import io.github.torbs00.stock_valuation.service.PortfolioService;
import io.github.torbs00.stock_valuation.service.StockService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/portfolio")
public class StockPositionController {

    private final UserStockholderRepository userStockholderRepository;
    private final StockPositionRepository stockPositionRepository;

    private final StockService stockService;
    private final PortfolioService portfolioService;

    public StockPositionController(UserStockholderRepository userStockholderRepository, StockPositionRepository stockPositionRepository, StockService stockService, PortfolioService portfolioService) {
        this.userStockholderRepository = userStockholderRepository;
        this.stockPositionRepository = stockPositionRepository;
        this.stockService = stockService;
        this.portfolioService = portfolioService;
    }

    @PostMapping("/buy")
    public String buyStock(@RequestParam String username, @RequestParam String symbol,
                           @RequestParam int shares, RedirectAttributes redirectAttributes) {
        UserStockholder stockholder = userStockholderRepository.findByUsername(username);

        double singleSharePrice = 0;
        String stockName = "Unknown Company";

        try {
            singleSharePrice = stockService.getStockPrice(symbol);
            stockName = stockService.getStockName(symbol);
        } catch (RuntimeException swallowed) {
            redirectAttributes.addFlashAttribute("notfound", "Found no stock with the provided ticker value");
            return "redirect:/portfolio/" + stockholder.getUsername();
        }

        if (singleSharePrice > 0 || !stockName.equalsIgnoreCase("Unknown Company")) {
            StockPosition stockPosition = new StockPosition(stockholder, symbol.toUpperCase(), stockName, shares, singleSharePrice, singleSharePrice);
            stockPositionRepository.save(stockPosition);
        }
        return "redirect:/portfolio/" + stockholder.getUsername();
    }

    @PostMapping("/sell")
    public String sellStock(@RequestParam String username, @RequestParam String symbol,
                            @RequestParam int shares, RedirectAttributes redirectAttributes) {
        UserStockholder stockholder = userStockholderRepository.findByUsername(username);

        List<StockPosition> positions = stockPositionRepository.findByStockholderAndAndSymbol(stockholder, symbol.toUpperCase());

        if (positions.isEmpty()) {
            redirectAttributes.addFlashAttribute("notany", "You don't own any stocks with ticker " + symbol.toUpperCase());
            return "redirect:/portfolio/" + stockholder.getUsername();
        }

        int totalSharesOwned = positions.stream().mapToInt(StockPosition::getShares).sum();

        if (totalSharesOwned < shares) {
            redirectAttributes.addFlashAttribute("notenough", "Don't own enough shares for this stock");
            return "redirect:/portfolio/" + stockholder.getUsername();
        }


        double singleSharePrice = 0;

        try {
            singleSharePrice = stockService.getStockPrice(symbol);
        } catch (RuntimeException swallowed) {
            redirectAttributes.addFlashAttribute("notfound", "Found no stock with the provided ticker value");
            return "redirect:/portfolio/" + stockholder.getUsername();
        }

        Iterator<StockPosition> iterator = positions.iterator();
        while (iterator.hasNext() && shares > 0) {
            StockPosition position = iterator.next();
            if (position.getShares() <= shares) {
                shares -= position.getShares();
                stockPositionRepository.delete(position);
            } else {
                position.setShares(position.getShares() - shares);
                stockPositionRepository.save(position);
                shares = 0;
            }
        }

        return "redirect:/portfolio/" + stockholder.getUsername();
    }

    @GetMapping("/{username}")
    public String showPortfolioByName(@PathVariable String username, Model model) {
        UserStockholder stockholder = userStockholderRepository.findByUsername(username);
        if (stockholder == null) {
            return "redirect:/404";
        }

        List<AggregatedStockPosition> portfolio = portfolioService.getAggregatedPortfolio(stockholder);

        model.addAttribute("portfolio", portfolio);
        model.addAttribute("username", stockholder.getUsername());

        return "portfolio";
    }
}
