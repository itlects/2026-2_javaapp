CREATE DATABASE IF NOT EXISTS week16_study_planner CHARACTER SET utf8mb4;
USE week16_study_planner;
CREATE TABLE IF NOT EXISTS items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  owner VARCHAR(60) NOT NULL,
  status VARCHAR(20) NOT NULL,
  CONSTRAINT chk_status CHECK (status IN ('예정','진행','완료'))
);
