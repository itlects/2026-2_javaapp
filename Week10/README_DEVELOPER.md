# 개발자 실행 안내

환경·Eclipse 메뉴·프로젝트별 파일 전체는 docs/lecture.md와 docs/workbook.md에 있습니다.

JDK 21과 Maven 3.9가 설치된 터미널에서는 각 프로젝트 폴더에서 mvn clean package를 실행합니다. 생성된 target/servlet-login.war 또는 target/learning-portal.war를 Tomcat 10.1 webapps에 배포합니다. Eclipse 실행과 수동 Tomcat 실행을 중복하지 않습니다.

Java 소스는 src/main/java, JSP·CSS·web.xml은 src/main/webapp입니다. Servlet API는 provided, JSTL과 JDBC는 WAR에 포함됩니다. 의존성 버전은 pom.xml로 고정합니다.

실제 비밀번호는 환경변수로 전달하며 Git에 넣지 않습니다. 교육 DB 계정과 데이터는 data/setup.sql로 생성합니다. target과 Eclipse 자동 생성 메타데이터는 Git에서 제외합니다.
