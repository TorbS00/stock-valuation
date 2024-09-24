package io.github.torbs00.stock_valuation.repository;

import io.github.torbs00.stock_valuation.domain.Portfolio;
import io.github.torbs00.stock_valuation.domain.PortfolioUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author TorbS00 on 24.09.2024.
 * @project stock-valuation.
 */
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Portfolio findByUser(PortfolioUser user);
}
