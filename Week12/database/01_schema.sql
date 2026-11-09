-- Workbench: root 연결에서 1회 실행. 기존 테이블을 삭제하지 않습니다.
CREATE DATABASE IF NOT EXISTS javaapp12
  CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE javaapp12;
CREATE TABLE IF NOT EXISTS posts (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  author VARCHAR(30) NOT NULL,
  content TEXT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- 아래 비밀번호와 db.properties의 비밀번호를 동일하게 바꾸세요.
CREATE USER IF NOT EXISTS 'javaapp12'@'localhost' IDENTIFIED BY 'CHANGE_ME_LOCAL';
ALTER USER 'javaapp12'@'localhost' IDENTIFIED BY 'CHANGE_ME_LOCAL';
GRANT SELECT, INSERT ON javaapp12.* TO 'javaapp12'@'localhost';
