package io.github.torbs00.stock_valuation;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TorbS00 on 25.09.2024.
 * @project stock-valuation.
 */
@Configuration
public class DotenvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }

}
