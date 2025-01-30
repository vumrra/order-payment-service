CREATE TABLE IF NOT EXISTS tbl_account (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             balance BIGINT NOT NULL DEFAULT 0,
                             created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);