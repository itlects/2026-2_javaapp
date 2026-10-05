CREATE DATABASE IF NOT EXISTS javaapp CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE javaapp;
CREATE TABLE IF NOT EXISTS member(
 member_id VARCHAR(20) PRIMARY KEY,
 name VARCHAR(50) NOT NULL,
 phone VARCHAR(20),
 email VARCHAR(100)
);
INSERT INTO member(member_id,name,phone,email) VALUES
('M001','김민수','010-1111-1111','minsu@example.com'),
('M002','이서연','010-2222-2222','seoyeon@example.com'),
('M003','박준호','010-3333-3333','junho@example.com')
ON DUPLICATE KEY UPDATE name=VALUES(name),phone=VALUES(phone),email=VALUES(email);