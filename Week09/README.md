# 9주차 — JSP 웹 개발환경과 로그인

JDK 21 + Apache Tomcat 10.1.x / UTF-8. 외부 라이브러리, Maven, DB 없이 실행합니다.

1. `docs/lecture.md` 순서대로 학습합니다.
2. `docs/workbook.md`를 보고 빈 `jsp-login` 폴더에서 파일을 하나씩 작성합니다.
3. 바로 실행하려면 완성된 `jsp-login` 전체를 Tomcat의 `webapps`에 복사합니다.
4. Tomcat `bin/startup.bat`(Windows) 또는 `bin/startup.sh`(Linux)를 실행합니다.
5. http://localhost:8080/jsp-login/ 에 접속합니다. 로그인은 student / Java1234! 입니다.
6. 실습 결과는 `docs/report-template.md`에 기록합니다. 검증 데이터는 `data/test-cases.csv`입니다.

Eclipse 경로로 배포할 경우 docs/workbook.md의 대안 절차를 사용하세요. 수동 배포와 동시에 사용하지 않습니다.

교육용 고정 계정입니다. 실제 회원 서비스는 비밀번호 해시, DB, HTTPS, 로그인 요청 CSRF 방어, 시도 제한 등을 추가해야 합니다. 현재 로그아웃에는 CSRF 토큰 검사를 포함합니다. 임의 입력을 화면에 넣을 때는 HTML 이스케이프가 필요합니다.
