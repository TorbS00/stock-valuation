package io.github.torbs00.stock_valuation.repository;

import io.github.torbs00.stock_valuation.domain.PortfolioUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioUserRepository extends JpaRepository<PortfolioUser, Long> {

    /**
     * Gets the PortfolioUser by username
     *
     * @param name
     * @return PortfolioUser
     */
    PortfolioUser findByName(String name);
}
