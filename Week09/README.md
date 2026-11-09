# 9주차 — JSP 웹 개발환경과 로그인

공통 환경: Windows 10 64비트 / Eclipse Enterprise Java and Web Developers 2025-09 / JDK 21 / Tomcat 10.1.x / MySQL Community Server 8.0.46 / MySQL Workbench 8.0 / UTF-8.

1. docs/lecture.md 상단 공통 개발환경을 설정합니다.
2. Eclipse Dynamic Web Project jsp-login을 만듭니다 (Tomcat 10.1 / Web Module 6.0).
3. 제공 jsp-login의 내용물을 src/main/webapp에 복사합니다.
4. Context root를 jsp-login으로 지정하고 Run As → Run on Server로 실행합니다.
5. http://localhost:8080/jsp-login/ 에 접속합니다. 계정: student / Java1234!
6. docs/workbook.md를 따라 실습하고 docs/report-template.md에 기록합니다.

9주차는 고정 교육용 계정을 사용하므로 DB·테이블·JDBC 드라이버 없이 실행합니다. MySQL과 Workbench는 공통 환경으로 준비합니다. DB 연동 예제에서 Connector/J와 SQL·접속 설정을 따로 제시합니다.

실제 서비스는 비밀번호 해시·DB·HTTPS·CSRF 방어·시도 제한 등을 추가해야 합니다. 임의 사용자 입력의 HTML 출력에는 이스케이프가 필요합니다.
