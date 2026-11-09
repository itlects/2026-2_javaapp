# Week12 개발자 안내

Windows 10 / JDK 21 / Eclipse IDE for Enterprise Java and Web Developers 2025-09 / MySQL Community Server 8.0.46 / MySQL Workbench 8 / Tomcat 10.1.x.
Maven WAR, Servlet 6.0 (jakarta.servlet), JSP 3.1, JSTL 3.0, Connector/J 8.0.33을 사용한다. pom.xml에 버전을 고정했다.

1. database/01_schema.sql에서 실습용 비밀번호를 변경하고 Workbench root 연결로 실행한다.
2. database/02_seed.sql을 빈 테이블에서 한 번 실행한다. 총 23건이다.
3. mvc-board/src/main/resources/db.properties에 같은 비밀번호를 입력한다. 파일은 로컬에서만 수정하고 실제 자격 증명을 업로드하지 않는다.
4. Eclipse → Window → Preferences → Java → Installed JREs에서 JDK 21을 추가하고 기본값으로 선택한다.
5. Server → Runtime Environments → Add → Apache Tomcat v10.1 → 압축 해제한 Tomcat 경로 및 JDK 21을 선택한다.
6. File → Import → Maven → Existing Maven Projects → mvc-board 폴더를 선택한다. pom.xml을 인식한 뒤 Finish.
7. 프로젝트 우클릭 → Maven → Update Project. 의존성 다운로드 완료 후 Project → Clean.
8. Project Properties → Project Facets: Java 21, Dynamic Web Module 6.0. Targeted Runtimes: Tomcat 10.1.
9. Run As → Run on Server → Tomcat 10.1 → 프로젝트 추가 → Finish.
10. http://localhost:8080/mvc-board/board 접속. Eclipse의 Web Project Settings에서 Context root를 mvc-board로 맞춘다.

서버 런타임 항목이 없으면 Enterprise Java and Web Developers 배포판 또는 Eclipse Web Tools가 필요하다.
처음에는 Maven 저장소 접속이 필요하다. Servlet API는 provided이므로 WAR에 포함되지 않으며 Tomcat이 제공한다.
CLI Maven이 있다면 mvc-board에서 `mvn clean package` 후 target/mvc-board.war를 Tomcat webapps에 배포할 수도 있다.

## 범위

목록·제목 검색·페이지 이동·상세·신규 등록을 완성한다. 수정/삭제 및 로그인 권한은 확장 과제다.
로컬 교실 예제이며 인터넷 공개 운영은 범위에 포함하지 않는다. 작성자 이름 입력은 인증이 아니다.
DAO는 요청별 Connection/PreparedStatement/ResultSet을 열고 try-with-resources로 닫는다.

## 참고

- https://tomcat.apache.org/tomcat-10.1-doc/
- https://tomcat.apache.org/migration-10.1.html
- https://dev.mysql.com/doc/connector-j/en/connector-j-installing-maven.html
