package io.github.torbs00.stock_valuation.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author TorbS00 on 27.09.2024.
 * @project stock-valuation.
 */
@Controller
public class PageNotFoundController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "404";
    }

    public String getErrorPath() {
        return "/error";
    }
}