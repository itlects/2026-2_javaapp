CREATE DATABASE IF NOT EXISTS javaapp
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE javaapp;

CREATE TABLE IF NOT EXISTS member (
    id INT PRIMARY KEY,
    user_id VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    department VARCHAR(50),
    grade INT
);

-- CSV Import 후 확인용 SQL
SELECT * FROM member;
SELECT user_id, name, email FROM member;
SELECT * FROM member ORDER BY id;
SELECT * FROM member WHERE department = '컴퓨터소프트웨어과';
SELECT * FROM member WHERE grade = 2;
SELECT * FROM member WHERE user_id = 'hong';
