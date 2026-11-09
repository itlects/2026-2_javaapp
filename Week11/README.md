# Week11 · JSP/Servlet + MySQL 회원관리 CRUD

- [강의자료](docs/lecture.md)
- [전체 소스 튜토리얼 워크북](docs/workbook.md)
- [프로젝트](member-crud)
- [DB 생성과 가상 데이터](member-crud/sql/01_schema.sql)

## 개발환경 및 실행 준비

| 항목 | 수업 기준 |
|---|---|
| 운영체제 | Windows 10, 64비트 |
| Java | JDK 21 (Eclipse와 Tomcat 모두 동일 JDK) |
| IDE | Eclipse IDE for Enterprise Java and Web Developers 2025-09 (4.37) |
| DB 서버 | MySQL Community Server 8.0.46, localhost:3306 |
| DB 도구 | MySQL Workbench 8 |
| 웹 서버 | Apache Tomcat 10.1.x (Servlet 6.0 / JSP 3.1) |
| 프로젝트 | Maven WAR, member-crud, UTF-8 |
| 라이브러리 | Connector/J 9.4.0, Servlet API 6.0.0, JSTL API 3.0.0 / 구현 3.0.1 |

Tomcat 10.1에서는 `jakarta.servlet.*`를 사용합니다. `javax.servlet.*` 예제를 섞지 않습니다. JDBC는 Java SE API이므로 `java.sql.*`를 그대로 사용합니다. Maven이 pom.xml의 라이브러리를 받으므로 JAR를 수동으로 중복 추가하지 않습니다.

MySQL Server와 Workbench는 서로 다른 프로그램입니다. Workbench 설치만으로 DB 서버가 실행되지는 않습니다. Windows 서비스에서 MySQL80의 실행 상태를 확인하세요.

수업용 데이터는 가상 인물이며 로그인·비밀번호 기능은 포함하지 않습니다. 이 앱은 로컬 실습용입니다. 외부 서비스로 확장할 때 관리자 인증과 권한 검사를 추가하세요.
### 준비 1. JDK·Tomcat 등록

1. Eclipse를 실행하고 `Window → Preferences → Java → Installed JREs → Add → Standard VM`에서 JDK 21 폴더를 등록한 뒤 기본값으로 선택합니다.
2. `Java → Compiler`를 21로 설정합니다. `General → Workspace → Text file encoding`은 UTF-8로 지정합니다.
3. Tomcat 10.1 Windows ZIP을 내려받아 `C:\dev\apache-tomcat-10.1`에 풉니다. `Window → Preferences → Server → Runtime Environments → Add → Apache Tomcat v10.1`에서 해당 폴더와 JDK 21을 지정합니다.
4. `Window → Show View → Servers`를 엽니다. 서버가 없으면 새 Tomcat 10.1 서버를 만들고 Runtime을 선택합니다. Tomcat 항목이 없다면 Enterprise Java and Web Developers 배포판인지 확인합니다.

### 준비 2. 데이터베이스 생성

1. Workbench에서 로컬 관리자 연결을 열고 Server 버전이 8.0.46인지 확인합니다.
2. 프로젝트 `sql/01_schema.sql`을 열어 전체 실행합니다. 전용 DB `javaapp_week11`과 로컬 실습 계정 `javaapp11`이 만들어집니다. 다른 주차 DB는 삭제하지 않습니다.
3. 결과에서 초기 회원 김자바와 이서블릿을 확인합니다. 처음 실행한 DB에서는 2행입니다. 재실행 시 같은 이메일은 추가하지 않습니다.
4. `src/main/resources/db.properties`의 URL, 사용자명, 비밀번호를 SQL 계정과 맞춥니다. 기존 동명 계정이 있다면 관리자에게 비밀번호를 확인하세요. SQL 재실행만으로 기존 계정의 비밀번호가 바뀌지는 않습니다.

### 준비 3. Eclipse에서 프로젝트 가져오기

1. ZIP 압축을 풉니다. `File → Import → Maven → Existing Maven Projects`에서 `Week11/member-crud`를 선택합니다. pom.xml이 보여야 합니다.
2. 프로젝트 우클릭 `Maven → Update Project`를 실행합니다. 최초 실행에는 Maven Central 인터넷 연결이 필요합니다.
3. `Properties → Java Build Path`에서 JRE System Library가 JavaSE-21인지 확인합니다. `Targeted Runtimes`에서 Tomcat 10.1을 선택합니다.
4. `Project Facets`는 Java 21 / Dynamic Web Module 6.0으로 확인합니다. `Web Project Settings`에서 Context root를 `member-crud`로 지정합니다. 메뉴가 없으면 Maven Update 후 확인합니다.
5. 프로젝트 우클릭 `Run As → Run on Server`로 실행하고 `http://localhost:8080/member-crud/members`를 엽니다. Servers의 Add and Remove로 앱이 추가되었는지 확인합니다.
6. 목록에 2명이 보이면 DB 연결과 SELECT까지 성공한 것입니다. 수정 후 브라우저만 새로고침하지 말고 필요 시 Servers → Publish를 실행합니다.

명령행 Maven이 설치되어 있다면 프로젝트 폴더에서 `mvn clean package`로 WAR를 생성할 수도 있습니다. 생성된 `target/member-crud.war`를 Tomcat의 webapps에 넣는 방식과 Eclipse 서버 배포 방식 중 하나만 사용하세요.

## 검증 범위

Java 21 소스 컴파일과 Tomcat 10.1 입력 JSP/요청 검증은 verification.json에 기록합니다. 실행 호스트에는 MySQL 서버와 Connector/J가 없어 실제 DB CRUD는 미검증입니다. Maven Central DNS 접근 제한으로 Maven WAR 빌드도 완료하지 못했습니다. Windows 10/Eclipse 2025-09 실제 UI 실행은 미검증입니다. 제공한 SQL과 워크북의 수동 검증표로 교수자 환경에서 DB 통합 실행을 확인하세요.

JSP/Servlet 입력화면 검증은 기존 JSTL과 Servlet API로 수행했고 배포용 완성 WAR는 제공하지 않습니다. 사용자 환경에서는 pom.xml로 Connector/J를 포함한 의존성을 받아 빌드해야 합니다.
