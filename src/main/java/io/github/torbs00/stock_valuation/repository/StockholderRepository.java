package io.github.torbs00.stock_valuation.repository;

import io.github.torbs00.stock_valuation.domain.Stockholder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockholderRepository extends JpaRepository<Stockholder, Long> {

    /**
     * Gets the UserStockholder by username
     *
     * @param username
     * @return UserStockholder
     */
    Stockholder findByUsername(String username);



}
