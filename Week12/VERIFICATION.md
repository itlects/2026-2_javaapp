# 검증 결과 및 범위

- Java 21 환경에서 Eclipse Compiler 3.38로 전체 Java 소스 컴파일 통과(직렬화 UID 경고 1건).
- PageTest: 누락/음수/문자/과대/빈 결과/정확한 배수/페이지 블록 경계 통과.
- Tomcat 10.1.61에서 실제 form.jsp 렌더링 통과.
- 임시 테스트 데이터로 list.jsp/detail.jsp 컴파일·렌더링 및 HTML 이스케이프 통과. 테스트용 진입점은 배포 프로젝트에 포함하지 않음.
- 실제 BoardServlet의 CSRF 누락 403 및 잘못된 id 400 응답 확인.
- MySQL 8.0.46 서버 및 Windows/Eclipse GUI에서의 전체 실행은 이 환경에서 수행하지 못함. SQL 초기화·실제 JDBC 조회·등록·페이지 전환은 README_OPERATOR의 검증표로 수업 환경에서 확인해야 함.
- 화면 그림은 설계 예시이며 실제 DB 실행 캡처가 아님.
