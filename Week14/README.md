# Week14 — Spring · MySQL 회원관리 CRUD

Lect14: Spring 회원관리 CRUD, Controller-Service-DAO, MySQL 연동.

- Lecture14.md: 6개 목차의 강의자료·평가문제·정답
- Workbook14.md: 실용 웹 주소록 전체 코드·순차 튜토리얼
- member-crud/: Eclipse Maven import 가능한 Java 21 Spring Boot 3.5.7 프로젝트
- database/: Workbench에서 실행하는 스키마·예제 데이터 SQL

실행: 관리자 계정으로 database/01_setup.sql, 02_seed.sql 실행 → Eclipse Maven import → Java Application 환경변수 DB_PASSWORD 설정 → MemberApplication 실행 → http://localhost:8080/members.

테스트: Maven test(H2), 실제 MySQL은 워크북 수동 확인표로 검증.

강의: https://app.notion.com/p/412b2e6bf807801d81e7e04ff452c9a2
Drive: https://drive.google.com/drive/folders/1TSu-orDUA43eTvtO_LnaRxClqt0Bpzy9
