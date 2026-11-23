# 검증 기록 — 2026-11-09

- 검증 환경: Linux / OpenJDK 21.0.13 / ECJ 3.38.0(Java 21 모드) / Tomcat 10.1.61.
- 두 프로젝트의 Java 전체 파일 컴파일 성공. Tomcat의 JSP 컴파일 성공.
- 기본 예제 HTTP 검증: 보호 URL, 성공·실패 로그인, 세션 ID 교체, 한글 GET/POST, Forward/Redirect, CSRF 거부, GET 로그아웃 405, WEB-INF 직접 요청 404, HTML 이스케이프, 로그아웃·재접근 차단 통과.
- 워크북: 로그인 JSP와 DB 환경변수 누락 시 503 응답을 확인.
- 실제 MySQL 8.0.46 접속·SQL 실행, Windows 10/Eclipse UI, Maven clean package와 실제 20분 만료 대기는 이 환경에서 미검증. 교재에 해당 환경의 재현 및 점검 절차를 제공함.
- 워크북 화면 이미지는 JSP/CSS 구성을 바탕으로 그린 설계 예시이며 실제 브라우저 캡처가 아님. 두 프로젝트는 동일 JSP/CSS를 사용함.

HTTP 세부 결과는 verification-results.json을 참조합니다. 실제 MySQL 검증을 대신하는 모의 DB 결과를 사용하지 않았습니다.

기본 예제명을 w10으로 변경한 뒤 Java 21 컴파일과 /w10 컨텍스트의 로그인·로그아웃·한글 GET/POST·Forward/Redirect·보호 URL·출력 이스케이프 등 16개 HTTP 점검을 통과했습니다. Eclipse UI 자체는 이 환경에서 미검증입니다.

## 노션 본문 전체 코드 재구성 점검

노션 본문에서 추출한 15개 파일로 JDK 21과 Maven 3.9.9 clean package를 수행하여 성공했습니다. 생성한 WAR의 Tomcat 실행 19개 항목이 통과했습니다. [상세 점검 결과](w10-notion-audit.md)를 확인하세요. Windows/Eclipse GUI 직접 검증과 DB 확장은 별도 범위입니다.
