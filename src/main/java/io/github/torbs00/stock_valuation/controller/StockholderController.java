package io.github.torbs00.stock_valuation.controller;

import io.github.torbs00.stock_valuation.domain.Stockholder;
import io.github.torbs00.stock_valuation.repository.StockholderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StockholderController {

    private final StockholderRepository stockholderRepository;

    public StockholderController(StockholderRepository stockholderRepository) {
        this.stockholderRepository = stockholderRepository;
    }

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username) {
        Stockholder stockholder = stockholderRepository.findByUsername(username);

        if(stockholder == null) {
            stockholder = new Stockholder(username);
            stockholderRepository.save(stockholder);
        }
        return "redirect:/portfolio/" + stockholder.getUsername();
    }
}
