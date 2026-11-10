CREATE DATABASE IF NOT EXISTS javaapp
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE javaapp;

CREATE TABLE IF NOT EXISTS product (
    product_id VARCHAR(10) PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    stock INT NOT NULL,
    maker VARCHAR(50)
);

INSERT IGNORE INTO product
(product_id, product_name, category, price, stock, maker)
VALUES
('P001', '무선마우스', '주변기기', 19000, 35, 'LogiTech'),
('P002', '기계식키보드', '주변기기', 69000, 18, 'KeyPro'),
('P003', '27인치모니터', '모니터', 249000, 12, 'ViewMax'),
('P004', 'USB-C허브', '주변기기', 39000, 24, 'HubWorks'),
('P005', '노트북스탠드', '액세서리', 32000, 20, 'DeskMate');

SELECT * FROM product ORDER BY product_id;
