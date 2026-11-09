CREATE DATABASE IF NOT EXISTS javaapp13
    CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE javaapp13;
CREATE TABLE IF NOT EXISTS members (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE
);
INSERT INTO members (id, name, email) VALUES
    (1, '김민수', 'minsu@example.com'),
    (2, '이서연', 'seoyeon@example.com'),
    (3, '박지훈', 'jihun@example.com'),
    (4, '김하늘', 'haneul@example.com'),
    (5, '최유진', 'yujin@example.com')
ON DUPLICATE KEY UPDATE name = VALUES(name), email = VALUES(email);
SELECT id, name, email FROM members ORDER BY id;
