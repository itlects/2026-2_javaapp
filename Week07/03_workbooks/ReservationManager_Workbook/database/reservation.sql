CREATE DATABASE IF NOT EXISTS javaapp CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE javaapp;

DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation (
    reservation_id   VARCHAR(10) PRIMARY KEY,
    customer_name    VARCHAR(50) NOT NULL,
    reservation_date DATE NOT NULL,
    reservation_time TIME NOT NULL,
    status           VARCHAR(20) NOT NULL
);

INSERT INTO reservation (reservation_id, customer_name, reservation_date, reservation_time, status) VALUES
('R001', '홍길동', '2026-11-20', '14:00:00', '예약완료'),
('R002', '김민지', '2026-11-21', '10:30:00', '대기'),
('R003', '이현우', '2026-11-22', '16:00:00', '예약완료');

SELECT * FROM reservation ORDER BY reservation_id;
