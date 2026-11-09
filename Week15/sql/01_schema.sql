-- Workbench에서 root로 실행. 기존 DB와 자료는 삭제하지 않는다.
CREATE DATABASE IF NOT EXISTS javaapp15
    CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE javaapp15;
CREATE TABLE IF NOT EXISTS tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    status VARCHAR(10) NOT NULL DEFAULT 'TODO',
    CONSTRAINT ck_tasks_status CHECK (status IN ('TODO', 'DOING', 'DONE'))
);
-- 아래 비밀번호 예시는 수업용이며 실제 개인 비밀번호로 바꾼다.
CREATE USER IF NOT EXISTS 'javaapp15'@'localhost' IDENTIFIED BY 'ChangeMe15!';
GRANT SELECT, INSERT, UPDATE, DELETE ON javaapp15.* TO 'javaapp15'@'localhost';
-- 계정이 이미 있다면 CREATE USER IF NOT EXISTS는 비밀번호를 바꾸지 않는다.
