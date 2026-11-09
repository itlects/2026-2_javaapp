# Week15 — 보강주간: JSP/Spring 프로젝트 완성

- docs/lecture.md: 요청한 여섯 목차, JSP/Spring 보완 예제, 객관식 10·단답형 5문항 및 해설.
- docs/workbook.md: 학습 과제 관리 웹 전체 소스와 SQL을 단계별로 만드는 튜토리얼.
- taskboard: Java 21 / Spring Boot 3.5.7 / MySQL 실습 프로젝트.
- sql: 01_schema.sql → 02_seed.sql(최초 1회).
- data: 결함 기록, 검증 시나리오, 제출 점검표.

Windows 10 / Eclipse Enterprise 2025-09 / Java 21 / MySQL 8.0.46 / Workbench 8.0.

실행: SQL 실행 → Eclipse Existing Maven Projects로 taskboard 가져오기 → JDK 21 → DB_USER 및 DB_PASSWORD 환경변수 설정 → Application.java 실행 → http://localhost:8080/tasks.

명령행: taskboard에서 mvn clean test package 후 java -jar target/taskboard-1.0.0.jar.

기존 JSP는 Tomcat 10.1의 Jakarta Servlet 프로젝트를 유지하고 공통 점검표를 적용한다. 워크북은 Thymeleaf이며 JSP를 혼합하지 않는다. 교육용 로컬 앱이며 인증·개인별 권한은 포함하지 않는다.
