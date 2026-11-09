-- Workbench: 로컬 관리자 계정으로 실행합니다. 기존 DB는 삭제하지 않습니다.
CREATE DATABASE IF NOT EXISTS javaapp_week10 CHARACTER SET utf8mb4;
USE javaapp_week10;
CREATE TABLE IF NOT EXISTS members (
    user_id VARCHAR(40) PRIMARY KEY,
    display_name VARCHAR(50) NOT NULL,
    password_hash CHAR(64) NOT NULL
);
-- 중복 실행 시 기존 회원정보를 덮어쓰지 않습니다.
INSERT IGNORE INTO members (user_id, display_name, password_hash)
VALUES ('student', '실습학생', SHA2('Java1234!', 256));
CREATE USER IF NOT EXISTS 'week10_app'@'localhost' IDENTIFIED BY 'Week10_db!2026';
GRANT SELECT ON javaapp_week10.members TO 'week10_app'@'localhost';
SELECT user_id, display_name FROM members;
-- SHA-256 단독 해시는 수업용입니다. 운영 서비스는 salt 포함 Argon2/bcrypt/PBKDF2 등을 사용합니다.
