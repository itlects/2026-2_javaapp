-- Workbench 관리자 연결에서 한 번 실행. 기존 데이터 삭제 없음.
CREATE DATABASE IF NOT EXISTS javaapp14 CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
CREATE USER IF NOT EXISTS 'javaapp14'@'localhost' IDENTIFIED BY 'Java14!LocalOnly';
GRANT SELECT, INSERT, UPDATE, DELETE ON javaapp14.* TO 'javaapp14'@'localhost';
USE javaapp14;
CREATE TABLE IF NOT EXISTS members (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- 이미 같은 계정이 있다면 암호는 바뀌지 않는다. 기존 암호를 DB_PASSWORD에 지정한다.
