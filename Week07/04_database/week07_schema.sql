CREATE DATABASE IF NOT EXISTS javaapp CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE javaapp;
DROP TABLE IF EXISTS products;
CREATE TABLE products (
 product_id INT PRIMARY KEY AUTO_INCREMENT,
 name VARCHAR(100) NOT NULL,
 category VARCHAR(50) NOT NULL,
 price INT NOT NULL,
 stock INT NOT NULL DEFAULT 0
);
INSERT INTO products(name,category,price,stock) VALUES
('무선 마우스','주변기기',25000,15),('기계식 키보드','주변기기',79000,8),('USB-C 허브','액세서리',39000,12);

DROP TABLE IF EXISTS reservations;
CREATE TABLE reservations (
 reservation_id INT PRIMARY KEY AUTO_INCREMENT,
 customer_name VARCHAR(50) NOT NULL,
 reservation_date DATE NOT NULL,
 item_name VARCHAR(100) NOT NULL,
 status VARCHAR(20) NOT NULL DEFAULT '예약'
);
INSERT INTO reservations(customer_name,reservation_date,item_name,status) VALUES
('김학생','2026-10-10','스터디룸 A','예약'),('이학생','2026-10-11','스터디룸 B','완료');
