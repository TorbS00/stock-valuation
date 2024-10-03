CREATE TABLE IF NOT EXISTS user_stockholder (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(255) NOT NULL UNIQUE
    );
CREATE TABLE stock_position (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        stockholder_id BIGINT NOT NULL,
        symbol VARCHAR(10) NOT NULL,
        name VARCHAR(255) NOT NULL,
        shares INT NOT NULL,
        single_stock_price DECIMAL(10, 2) NOT NULL,
        FOREIGN KEY (stockholder_id) REFERENCES user_stockholder(id) ON DELETE CASCADE
);