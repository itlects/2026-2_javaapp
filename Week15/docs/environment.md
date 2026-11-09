## 개발환경 및 실행 기준

| 항목 | 수업 기준 |
| --- | --- |
| 운영체제 | Windows 10, UTF-8 파일 인코딩 |
| Java | JDK 21, Eclipse Installed JREs와 프로젝트 Compiler 모두 21 |
| IDE | Eclipse IDE for Enterprise Java and Web Developers 2025-09 |
| DB | MySQL Community Server 8.0.46, localhost:3306 |
| DB 도구 | MySQL Workbench 8.0 |
| Spring 워크북 | Spring Boot 3.5.7, Spring MVC, Thymeleaf, JDBC, Validation |
| JDBC 드라이버 | Connector/J 8.0.33, Maven에서 내려받음; 서버 버전과 번호가 같을 필요 없음 |
| 실행 서버 | Spring: 내장 Tomcat, 127.0.0.1:8080 / 기존 JSP: Tomcat 10.1, Jakarta Servlet 6 |
| 빌드 | Eclipse 내장 Maven 또는 Maven 3.9.x, 최초 의존성 다운로드에 인터넷 필요 |

Spring 워크북은 Java Application으로 실행하며 Eclipse Servers에 추가하지 않는다. 기존 JSP 프로젝트는 Tomcat 10.1에서 실행한다. 두 서버가 8080을 동시에 사용하면 하나를 중지하거나 Spring의 server.port를 8081로 바꾼다. 본 예제는 개인 PC 수업용이며 로그인·사용자별 권한 분리는 구현하지 않는다. 기존 프로젝트에 인증이 있다면 그대로 유지한다.
