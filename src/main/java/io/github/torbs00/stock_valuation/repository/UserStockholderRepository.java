package io.github.torbs00.stock_valuation.repository;

import io.github.torbs00.stock_valuation.domain.UserStockholder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStockholderRepository extends JpaRepository<UserStockholder, Long> {

    /**
     * Gets the UserStockholder by username
     *
     * @param username
     * @return UserStockholder
     */
    UserStockholder findByUsername(String username);



}
