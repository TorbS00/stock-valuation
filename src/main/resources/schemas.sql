CREATE TABLE IF NOT EXISTS PortfolioUser (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(255) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS Portfolio (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_id BIGINT,
        FOREIGN KEY (user_id) REFERENCES PortfolioUser(id)
    );

CREATE TABLE IF NOT EXISTS StockTransaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    portfolio_id BIGINT,
    stock_symbol VARCHAR(10),
    amount INT,
    purchase_price DECIMAL(10, 2),
    is_buy BOOLEAN,
    transaction_time TIMESTAMP,
    FOREIGN KEY (portfolio_id) REFERENCES Portfolio(id)
    );