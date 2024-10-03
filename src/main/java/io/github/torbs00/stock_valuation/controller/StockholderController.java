package io.github.torbs00.stock_valuation.controller;

import io.github.torbs00.stock_valuation.domain.UserStockholder;
import io.github.torbs00.stock_valuation.repository.UserStockholderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StockholderController {

    private final UserStockholderRepository userStockholderRepository;

    public StockholderController(UserStockholderRepository userStockholderRepository) {
        this.userStockholderRepository = userStockholderRepository;
    }

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username) {
        UserStockholder stockholder = userStockholderRepository.findByUsername(username);

        if(stockholder == null) {
            stockholder = new UserStockholder(username);
            userStockholderRepository.save(stockholder);
        }
        return "redirect:/portfolio/" + stockholder.getUsername();
    }
}
