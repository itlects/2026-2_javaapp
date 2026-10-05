-- =====================================================================
-- 6주차 회원관리 DB 준비
-- * 5주차 week05_javaapp.sql 의 member 테이블과 "같은 구조"를 사용한다.
-- * 5주차에 CSV를 이미 불러왔다면 기존 데이터는 그대로 두고 없는 행만 추가된다.
-- * 여러 번 실행해도 오류가 나지 않는다. (INSERT IGNORE: 이미 있는 번호/아이디는 건너뜀)
-- =====================================================================
CREATE DATABASE IF NOT EXISTS javaapp
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE javaapp;

-- ---------------------------------------------------------------------
-- [문제 해결] 아래 오류가 나면 이전에 다른 구조의 member 테이블이 만들어진 것이다.
--   Unknown column '...' in 'INSERT INTO'  또는  Column count doesn't match value count
-- 이때만 다음 한 줄의 주석(--)을 지우고 다시 실행한다. (member 데이터가 모두 삭제됨)
-- DROP TABLE IF EXISTS member;
-- ---------------------------------------------------------------------

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

-- 5주차 member_sample.csv 와 같은 데이터
INSERT IGNORE INTO member (id, user_id, password, name, email, phone, department, grade) VALUES
(1, 'hong', '1234', '홍길동', 'hong@example.com', '010-1111-1111', '컴퓨터소프트웨어과', 2),
(2, 'kim',  '1234', '김학생', 'kim@example.com',  '010-2222-2222', '컴퓨터소프트웨어과', 2),
(3, 'lee',  '1234', '이학생', 'lee@example.com',  '010-3333-3333', '컴퓨터소프트웨어과', 1),
(4, 'park', '1234', '박학생', 'park@example.com', '010-4444-4444', 'AI소프트웨어과', 2),
(5, 'choi', '1234', '최학생', 'choi@example.com', '010-5555-5555', 'AI소프트웨어과', 1),
(6, 'jung', '1234', '정학생', 'jung@example.com', '010-6666-6666', '컴퓨터소프트웨어과', 2),
(7, 'kang', '1234', '강학생', 'kang@example.com', '010-7777-7777', '컴퓨터소프트웨어과', 1),
(8, 'yoon', '1234', '윤학생', 'yoon@example.com', '010-8888-8888', 'AI소프트웨어과', 2);

-- 확인
SELECT * FROM member ORDER BY id;

-- 참고: 6주차 프로그램 실행 후 확인용 SQL
-- SELECT * FROM member WHERE id = 100;          -- 예제 2~4
-- SELECT COUNT(*) FROM member;                  -- 등록/삭제 후 행 수 비교
