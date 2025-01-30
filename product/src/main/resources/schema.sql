CREATE TABLE tbl_product (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             price BIGINT NOT NULL,
                             quantity INT NOT NULL,
                             created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO `tbl_product` VALUES (1, '치킨', 10000, 10, '2007-03-01');
INSERT INTO `tbl_product` VALUES (2, '피자', 15000, 8, '2007-03-01');
INSERT INTO `tbl_product` VALUES (3, '닭발', 12000, 15, '2007-03-01');
INSERT INTO `tbl_product` VALUES (4, '짜장면', 8000, 20, '2007-03-01');
INSERT INTO `tbl_product` VALUES (5, '짬뽕', 9000, 20, '2007-03-01');
