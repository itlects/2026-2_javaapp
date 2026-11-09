# 검증 기록 — 2026-11-09

- Java 21 런타임, Maven 3.9.9, Spring Boot 3.5.7.
- MemberFlowTest: Tests run 2, Failures 0, Errors 0, Skipped 0.
- H2 MySQL 호환 모드로 MockMvc 요청부터 실제 DAO/SQL, Thymeleaf 렌더링까지 검사.
- CRUD, 빈 이름·잘못된 이메일·전화 검증, 중복 이메일, 없는 회원 404, GET 삭제 거부, HTML 이스케이프 검사 통과.
- 실행 호스트에는 JDK의 release 교차 컴파일 파일이 없어 Maven의 release 옵션을 비우고 source=21, target=21로 컴파일·검증함. 배포 pom.xml은 정상 JDK 21의 기본 release 설정을 유지함.
- 테스트 JVM의 에이전트 제약 영향을 줄이도록 src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker에 mock-maker-subclass 설정 포함. 이 테스트는 모의 DAO를 사용하지 않음.
- Windows 10/Eclipse 2025-09 및 실제 MySQL 8.0.46 서버에서의 실행은 미검증. Workbook14.md의 CRUD·Workbench·재실행 점검표로 확인 필요.
- ZIP에는 target, 실행 암호, 로컬 Maven 설정을 포함하지 않음. SQL의 수업용 계정 암호는 예시임.
