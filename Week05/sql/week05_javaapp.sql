CREATE DATABASE IF NOT EXISTS javaapp DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE javaapp;
CREATE TABLE IF NOT EXISTS member (
 id INT AUTO_INCREMENT PRIMARY KEY,
 user_id VARCHAR(30) NOT NULL UNIQUE,
 password VARCHAR(100) NOT NULL,
 name VARCHAR(30) NOT NULL,
 email VARCHAR(100),
 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
INSERT IGNORE INTO member(user_id,password,name,email) VALUES
('hong','1234','홍길동','hong@example.com'),
('kim','1234','김학생','kim@example.com');
SELECT * FROM member;
