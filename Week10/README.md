# 10주차 — Servlet과 Session

Windows 10 / Eclipse Enterprise 2025-09 / JDK 21 / Tomcat 10.1 / MySQL 8.0.46 / Workbench 8.0.

- docs/lecture.md: 요청한 6개 목차, 전체 예제 코드, 객관식 10·단답형 5문항과 해설.
- docs/workbook.md: MySQL 회원 인증 학습 포털을 파일별로 만드는 튜토리얼.
- servlet-login: DB 없는 GET/POST·Parameter·Forward/Redirect·로그인 예제.
- learning-portal: MySQL 회원 인증으로 확장한 독립 실행 프로젝트.
- data/setup.sql / test-cases.csv: 초기 DB와 14개 검증 시나리오.
- docs/report-template.md: 학습자 제출 양식.

Eclipse: Import → Maven → Existing Maven Projects → 둘 중 프로젝트 폴더 선택 → Maven Update → Tomcat 10.1 지정 → Run on Server. Context root를 각각 servlet-login / learning-portal로 설정합니다.

기본 URL: http://localhost:8080/servlet-login/login
워크북 URL: http://localhost:8080/learning-portal/login
교육 계정: student / Java1234!

워크북 DB 환경변수·SQL 실행은 docs/workbook.md를 먼저 확인합니다. SHA-256 단독 해시는 수업용입니다. 회원가입·게시물 CRUD는 이번 구현 범위에 포함되지 않습니다.
