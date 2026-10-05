# Week07 - 계층화 DB 응용프로그램 / 회원관리 프로젝트

6주차에서 완성한 Swing + JDBC CRUD를 7주차에서는 **DTO/VO - DAO - Service - UI** 계층으로 분리합니다.

## 수업 프로젝트
- 01_examples/MemberLayered_Complete : 계층 분리 회원관리 완성 예제
- 02_practice/MemberLayered_Practice : TODO 1~10 단계별 실습
- 03_workbooks/Workbook01_MemberLayered : 독립 구현 워크북
- 03_workbooks/Workbook01_MemberLayered_test : 8주차 중간시험 대비 평가형 복사본
- 04_database/week07_member_layered.sql : 회원 테이블/샘플 데이터
- 05_submission/MIDTERM_PREP_CHECKLIST.md : 중간평가 대비 자기점검표

## 핵심 구조
UI -> Service -> DAO -> DB
      DTO/VO

- DTO/VO: DB 한 행의 데이터를 Java 객체로 전달
- DAO: SQL과 JDBC 처리 전담
- Service: 입력 검증과 업무 흐름
- UI: Swing 이벤트와 화면 표시

Eclipse: File > Import > Existing Projects into Workspace
MySQL Connector/J는 각 PC에서 Build Path에 추가합니다.
