-- =====================================================================
-- 6주차 워크북 2 — 상품관리 DB 준비
-- * 5주차 product_catalog.sql 의 product 테이블과 "같은 구조"를 사용한다.
-- * 여러 번 실행해도 오류가 나지 않는다. (INSERT IGNORE)
-- =====================================================================
CREATE DATABASE IF NOT EXISTS javaapp
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE javaapp;

-- [문제 해결] Unknown column 'category' 등의 오류가 나면 다음 줄 주석을 지우고 다시 실행 (product 데이터 삭제됨)
-- DROP TABLE IF EXISTS product;

CREATE TABLE IF NOT EXISTS product (
    product_id VARCHAR(10) PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price INT NOT NULL,
    stock INT NOT NULL,
    maker VARCHAR(50)
);

-- 5주차 product_sample.csv 와 같은 데이터
INSERT IGNORE INTO product (product_id, product_name, category, price, stock, maker) VALUES
('P001', '무선마우스',     '주변기기', 19000,  35, 'LogiTech'),
('P002', '기계식키보드',   '주변기기', 69000,  18, 'KeyPro'),
('P003', '27인치모니터',   '모니터',   249000, 12, 'ViewMax'),
('P004', 'USB-C허브',      '주변기기', 39000,  24, 'HubWorks'),
('P005', '노트북스탠드',   '액세서리', 32000,  20, 'DeskMate'),
('P006', '웹캠',           '영상장비', 59000,  15, 'CamPlus'),
('P007', 'USB마이크',      '음향장비', 89000,   9, 'SoundLab'),
('P008', '외장SSD1TB',     '저장장치', 129000, 14, 'FastDisk'),
('P009', '블루투스스피커', '음향장비', 49000,  22, 'SoundLab'),
('P010', '노트북파우치',   '액세서리', 25000,  40, 'CarryOn');

SELECT * FROM product ORDER BY product_id;
