USE javaapp;

CREATE TABLE IF NOT EXISTS product (
    product_id VARCHAR(10) PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    stock INT NOT NULL,
    maker VARCHAR(50)
);

-- CSV Import 후 확인
SELECT * FROM product;
SELECT product_id, product_name, price FROM product;
SELECT * FROM product ORDER BY price;
SELECT * FROM product WHERE category = '주변기기';
SELECT * FROM product WHERE price >= 50000;
SELECT * FROM product WHERE stock < 15;
