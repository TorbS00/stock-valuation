package io.github.torbs00.stock_valuation.repository;

import io.github.torbs00.stock_valuation.domain.Portfolio;
import io.github.torbs00.stock_valuation.domain.PortfolioUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    /**
     * Gets the portfolio from the user.
     *
     * @param user
     * @return Portfolio of user
     */
    Portfolio findByUser(PortfolioUser user);
}
