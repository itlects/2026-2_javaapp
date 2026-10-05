# Week06 GUI + JDBC 회원관리
5주차 JDBC 연결/조회 기초를 바탕으로 Swing JTable과 MySQL을 연결해 CRUD를 완성하는 6주차 프로젝트입니다.

## 실행
1. MySQL 8.0에서 database/member.sql 실행
2. MySQL Connector/J JAR을 lib/mysql-connector-j.jar로 추가하거나 Eclipse Build Path에 등록
3. DBConnection.java의 계정/비밀번호 확인
4. Eclipse에서 Existing Projects into Workspace로 Import
5. MemberManagementFrame 실행

## 학습 순서
조회(findAll) → 등록(insert) → 수정(update) → 삭제(delete) → JTable 갱신

> 비밀번호는 예제값이므로 자신의 실습 환경에 맞게 변경합니다. 실제 서비스에서는 소스코드에 비밀번호를 저장하지 않습니다.