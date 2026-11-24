# w10 JAR 검증 결과

2026-11-24 (Asia/Seoul). Linux OpenJDK 21, Maven 3.9.9, embedded Tomcat 10.1.62.

두 Maven 프로젝트 clean package 성공. 최종 target/w10.jar를 실제 java -jar로 실행했습니다. 임시 포트 18080에서 17개 HTTP/실행 점검 통과. Windows 10 및 배치 파일 직접 실행은 이 환경에서 검증하지 않았습니다.

- PASS: Login JSP and JSTL renders
- PASS: Anonymous home redirected
- PASS: Wrong password forward
- PASS: Missing CSRF rejected
- PASS: Login PRG
- PASS: Session ID rotated
- PASS: Authenticated home JSP
- PASS: Korean GET
- PASS: Korean POST
- PASS: Forward request attribute
- PASS: Redirect request attribute cleared
- PASS: CSS resource
- PASS: WEB-INF direct access blocked
- PASS: GET logout disallowed
- PASS: Logout redirect
- PASS: Logged out home blocked
- PASS: Ready message

20분 세션 만료 대기와 MySQL 확장 워크북은 이번 범위에 포함하지 않습니다.
