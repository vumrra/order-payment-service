CREATE TABLE tbl_account (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             balance BIGINT NOT NULL DEFAULT 0,
                             created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO `tbl_account` VALUES (1, 1, 100000, '2007-03-01');