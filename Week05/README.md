# Week05 JDBC 조회 기초

Java 응용 5주차는 **SQL 기초 + JDBC 연결 + SELECT 조회**에 집중합니다.

## 5주차 범위
- MySQL DB/테이블 확인
- CSV 회원 데이터 Import
- SELECT 문 기초
- JDBC 연결
- PreparedStatement
- ResultSet
- 전체 회원 조회
- 조건 회원 조회

## 6주차로 넘기는 내용
- INSERT / UPDATE / DELETE를 Java에서 구현
- 회원 등록/수정/삭제
- CRUD 기반 회원관리 입출력 프로그램

## 실행 순서
1. data/member_sample.csv 확인
2. MySQL Workbench에서 javaapp DB와 member 테이블 생성
3. Table Data Import Wizard로 CSV 로딩
4. SELECT SQL로 데이터 확인
5. Connector/J를 Eclipse Build Path에 추가
6. DBConnection.java 계정/비밀번호 수정
7. JdbcConnectionTest 실행
8. SelectMember / SelectMemberByUserId 실행
