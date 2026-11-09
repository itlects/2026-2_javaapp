-- MySQL Workbench: 관리자 연결에서 한 번 실행합니다. 기존 DB는 삭제하지 않습니다.
CREATE DATABASE IF NOT EXISTS javaapp_week11
    CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
CREATE USER IF NOT EXISTS 'javaapp11'@'localhost' IDENTIFIED BY 'Local11!Change';
GRANT SELECT, INSERT, UPDATE, DELETE ON javaapp_week11.* TO 'javaapp11'@'localhost';
USE javaapp_week11;
CREATE TABLE IF NOT EXISTS members (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL DEFAULT '',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO members (name, email, phone)
SELECT '김자바', 'java@example.com', '010-1111-2222'
WHERE NOT EXISTS (SELECT 1 FROM members WHERE email='java@example.com');
INSERT INTO members (name, email, phone)
SELECT '이서블릿', 'servlet@example.com', '010-3333-4444'
WHERE NOT EXISTS (SELECT 1 FROM members WHERE email='servlet@example.com');
SELECT * FROM members ORDER BY id;
-- 이미 존재하는 계정의 비밀번호는 CREATE USER IF NOT EXISTS로 변경되지 않습니다.
