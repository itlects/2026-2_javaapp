CREATE DATABASE IF NOT EXISTS javaapp CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE javaapp;
CREATE TABLE IF NOT EXISTS member (member_id VARCHAR(20) PRIMARY KEY, name VARCHAR(30) NOT NULL, phone VARCHAR(20), email VARCHAR(80));
INSERT INTO member VALUES ('M001','김민준','010-1111-1111','minjun@example.com'),('M002','이서연','010-2222-2222','seoyeon@example.com'),('M003','박지훈','010-3333-3333','jihun@example.com');