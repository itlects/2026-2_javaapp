CREATE DATABASE IF NOT EXISTS week16_book_catalog CHARACTER SET utf8mb4;
USE week16_book_catalog;
CREATE TABLE IF NOT EXISTS items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  owner VARCHAR(60) NOT NULL,
  status VARCHAR(20) NOT NULL,
  CONSTRAINT chk_status CHECK (status IN ('전공','교양','기타'))
);
