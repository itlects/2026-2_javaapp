# Week13 Spring Boot MVC 회원목록

- 개발환경: Windows 10 / JDK 21 / Eclipse 2025-09 / MySQL 8.0.46 / Workbench 8
- Spring Boot 3.5.7, Maven, Thymeleaf, JDBC, 내장 Tomcat
- lesson.md: 강의자료 6개 목차와 전체 코드
- workbook.md: 단계별 회원 검색·상세 웹사이트 완성
- assessment.md: 객관식 10개·단답형 5개, 정답·해설
- sql/01_schema_seed.sql → sql/02_user.sql 순서로 root Workbench에서 실행
- Eclipse Existing Maven Projects로 spring-members 폴더 가져오기
- 실행 구성 Environment의 DB_PASSWORD 설정 후 MemberApplication 실행
- URL: http://localhost:8080/members
- 테스트: Eclipse Maven build Goals test 또는 별도 설치 Maven의 mvn test
- 자동 테스트는 H2 사용, 실제 실행은 MySQL 사용
- 상세 절차·예상 결과·검증 한계는 lesson.md와 workbook.md 참고
