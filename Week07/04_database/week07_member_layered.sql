CREATE DATABASE IF NOT EXISTS javaapp CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE javaapp;
DROP TABLE IF EXISTS members;
CREATE TABLE members(
 member_id INT AUTO_INCREMENT PRIMARY KEY,
 name VARCHAR(40) NOT NULL,
 phone VARCHAR(20),
 email VARCHAR(100) NOT NULL,
 grade VARCHAR(20) DEFAULT 'BASIC'
);
INSERT INTO members(name,phone,email,grade) VALUES
('김민준','010-1111-1001','minjun@example.com','VIP'),
('이서연','010-2222-1002','seoyeon@example.com','BASIC'),
('박지후','010-3333-1003','jihoo@example.com','VIP'),
('최하은','010-4444-1004','haeun@example.com','BASIC');
