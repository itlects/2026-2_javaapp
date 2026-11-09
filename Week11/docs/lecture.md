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
<table-of-contents />

## 1. 학습목표

이번 주차는 강의계획의 **Lect11: JSP/Servlet과 MySQL을 연동하여 CRUD 프로그램을 구현**에 대응합니다. 웹 회원관리 CRUD, JDBC, DAO/DTO, Servlet, JSP, MySQL을 하나의 프로그램으로 연결합니다.

수업을 마치면 다음을 수행할 수 있습니다.

1. HTTP 요청이 Servlet → DAO → MySQL → JSP로 이어지는 과정을 설명한다.
2. Member DTO와 MemberDao를 분리하여 SELECT/INSERT/UPDATE/DELETE를 작성한다.
3. PreparedStatement와 try-with-resources를 사용한다.
4. JSP의 EL/JSTL로 회원을 출력하고 POST 요청으로 DB를 변경한다.
5. 웹 결과와 Workbench 조회를 비교하여 CRUD 성공 여부를 검증한다.

권장 3시간 운영: 환경·이론 40분, 목록/등록 예제 45분, 수정/삭제 워크북 65분, 평가·정리 30분.

## 2. 이론

### 2.1 MVC와 DAO/DTO

브라우저의 `/members` 요청을 MemberServlet이 받습니다. Servlet은 파라미터를 검증하고 MemberDao를 호출합니다. DAO는 JDBC로 SQL을 실행하며 Member DTO에 결과를 담습니다. Servlet이 request 속성을 설정하고 JSP로 forward하면 JSP가 EL/JSTL로 HTML을 만듭니다.

| 구성 | 역할 | 이 프로젝트의 파일 |
|---|---|---|
| Controller | 요청 분기·검증·화면 선택 | MemberServlet.java |
| DTO | 회원 값 전달, SQL 없음 | Member.java |
| DAO | SQL 실행·DB 자원 관리 | MemberDao.java |
| 연결 도우미 | 설정 읽기·Connection 생성 | DB.java, db.properties |
| View | 결과 출력·입력 폼 | list.jsp, form.jsp |
| DB | 데이터 영속 저장 | members 테이블 |

DTO의 getter는 `${m.name}` 같은 EL 접근에 사용됩니다. DAO는 화면 HTML을 만들지 않으며 JSP에서는 SQL을 실행하지 않습니다. Servlet 인스턴스는 여러 요청이 공유하므로 Connection이나 현재 회원 값을 필드에 저장하지 않습니다. 여기서 DAO 필드는 상태가 없고 Connection은 매번 메서드 내부에서 생성합니다.

### 2.2 CRUD와 HTTP

| 기능 | 요청 | SQL / 처리 |
|---|---|---|
| 목록·검색 | GET /members?q=검색어 | SELECT |
| 등록 폼 | GET /members?action=new | 빈 DTO 전달 |
| 수정 폼 | GET /members?action=edit&id=번호 | WHERE id=?로 1명 SELECT |
| 등록 | POST /members, action=save, id 생략 | INSERT |
| 수정 | POST /members, action=save, id 포함 | UPDATE ... WHERE id=? |
| 삭제 | POST /members, action=delete, id 포함 | DELETE ... WHERE id=? |

GET 조회는 데이터를 바꾸지 않습니다. 저장과 삭제 후에는 redirect로 목록에 다시 접근합니다. 이를 PRG(Post/Redirect/Get)라 하며 새로고침에 의한 같은 POST 재전송을 줄입니다. 입력 오류는 forward하여 입력값과 오류문구를 같은 요청에서 보여줍니다.

### 2.3 JDBC 핵심

연결 생성 → PreparedStatement 생성 → 값 바인딩 → SQL 실행 → 결과 처리 → 자원 종료 순서입니다. `executeQuery()`는 ResultSet을 반환하며 SELECT에 사용합니다. `executeUpdate()`는 변경 행 수를 반환합니다. 수정·삭제가 0행이면 대상이 없을 수 있으므로 확인합니다.

`?`는 값 자리입니다. 문자열을 SQL에 연결하지 말고 `setString`, `setLong`으로 바인딩합니다. 테이블명이나 ORDER BY 열 이름을 `?`로 바인딩할 수는 없습니다. try-with-resources는 예외 발생 시에도 JDBC 자원을 닫습니다.

### 2.4 데이터 무결성과 출력 처리

id는 AUTO_INCREMENT 기본키, email은 UNIQUE입니다. 이름·이메일은 필수이고 서버에서도 길이와 형식을 검사합니다. HTML required만으로는 우회 요청을 막을 수 없습니다. DB 중복키 오류 1062는 사용자가 수정할 수 있는 문구로 돌려줍니다. `c:out`은 이름·검색어의 HTML 특수문자를 이스케이프합니다. 세션의 CSRF 토큰은 변경 요청이 해당 입력화면에서 시작되었는지 확인하는 데 사용하며 관리자 인증을 대신하지 않습니다.

## 3. 예제

### 예제 1. DB와 실행환경 준비

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

### 예제 2. SQL과 DTO

아래 코드를 파일 경로에 맞게 작성합니다. DB 생성 SQL은 Workbench에서 실행하고 Java 코드는 Eclipse의 src/main/java에 패키지를 생성하여 작성합니다.

#### sql/01_schema.sql

```sql
-- MySQL Workbench: 관리자 연결에서 한 번 실행합니다. 기존 DB는 삭제하지 않습니다.
CREATE DATABASE IF NOT EXISTS javaapp_week11
    CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
CREATE USER IF NOT EXISTS 'javaapp11'@'localhost' IDENTIFIED BY 'Local11!Change';
GRANT SELECT, INSERT, UPDATE, DELETE ON javaapp_week11.* TO 'javaapp11'@'localhost';
USE javaapp_week11;
CREATE TABLE IF NOT EXISTS members (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL DEFAULT '',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
INSERT INTO members (name, email, phone)
SELECT '김자바', 'java@example.com', '010-1111-2222'
WHERE NOT EXISTS (SELECT 1 FROM members WHERE email='java@example.com');
INSERT INTO members (name, email, phone)
SELECT '이서블릿', 'servlet@example.com', '010-3333-4444'
WHERE NOT EXISTS (SELECT 1 FROM members WHERE email='servlet@example.com');
SELECT * FROM members ORDER BY id;
-- 이미 존재하는 계정의 비밀번호는 CREATE USER IF NOT EXISTS로 변경되지 않습니다.
```

#### src/main/java/edu/week11/Member.java

```java
package edu.week11;

// DTO: 요청 계층과 DB 계층 사이에서 회원 정보를 전달합니다.
public class Member {
    private long id;
    private String name;
    private String email;
    private String phone;

    public Member(long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}
```

#### src/main/java/edu/week11/DB.java

```java
package edu.week11;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DB {
    private static final Properties CONFIG = new Properties();

    static {
        try (InputStream in = DB.class.getResourceAsStream("/db.properties")) {
            if (in == null) {
                throw new IllegalStateException("db.properties가 없습니다.");
            }
            CONFIG.load(in);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DB() { }

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(
            CONFIG.getProperty("db.url"),
            CONFIG.getProperty("db.user"),
            CONFIG.getProperty("db.password")
        );
    }
}
```

### 예제 3. 목록 SELECT와 등록 INSERT

전체 실행 파일은 하위 워크북에 모두 제시합니다. 다음은 DAO의 핵심 등록 흐름입니다. INSERT 결과가 1이면 회원 1행이 생성되었습니다.

```java
String sql = "INSERT INTO members(name, email, phone) VALUES (?, ?, ?)";
try (Connection con = DB.open();
     PreparedStatement ps = con.prepareStatement(sql)) {
    ps.setString(1, m.getName());
    ps.setString(2, m.getEmail());
    ps.setString(3, m.getPhone());
    return ps.executeUpdate();
}
```

워크북의 전체 파일을 완성한 뒤 등록 화면에 이름 `박학생`, 이메일 `park@example.com`, 전화 `010-5555-6666`을 입력합니다. 저장 후 목록에 박학생이 보이고 Workbench에서 같은 이메일의 1행이 조회되어야 합니다.

```sql
USE javaapp_week11;
SELECT id, name, email, phone
FROM members
WHERE email = 'park@example.com';
```

## 4. 실습 및 과제

하위 **워크북: 교육센터 회원관리 웹 구축**을 순서대로 수행합니다. 제공 프로젝트를 실행해 완성 결과를 먼저 확인한 뒤, 별도 작업 폴더에서 파일별 코드를 작성해 재현하세요.

| 단계 | 수행 | 완료 기준 |
|---|---|---|
| 1 | DB 생성·프로젝트 실행 | 초기 회원 목록 표시 |
| 2 | 박학생 등록 | 웹과 DB 양쪽에서 새 행 확인 |
| 3 | 전화번호 수정 | 동일 id의 전화만 변경 |
| 4 | 박학생 삭제 | 해당 id가 DB에서 0행 |
| 5 | 이름/이메일 검색 | 해당 회원만 출력, 전체 보기로 복귀 |
| 6 | 오류 입력 | 중복 이메일 안내, 필수값 누락 차단 |

과제: 교육센터에서 사용하는 회원관리 도구로 제목과 디자인을 수정하고, 신규 회원 3명을 직접 등록한 뒤 검색·수정·삭제를 각각 수행하세요. 결과보고서에는 학번·성명, CRUD 전후 화면 4장 이상, 확인 SQL과 조회 결과, DAO/DTO 역할 설명, 발견한 오류와 해결 과정을 포함합니다. 제출물은 프로젝트 ZIP과 PDF 보고서이며 실제 개인정보나 개인 DB 비밀번호는 제외합니다. 제출 위치와 마감은 교수자 공지를 따릅니다.

평가 기준(100점): 환경/DB 15, 구조 분리 20, CRUD 40(각 10), 검색·검증 15, 결과 설명 10.

## 5. 평가문제(객관식 및 단답형)

### 객관식

1. DAO의 주된 역할은? ① HTML 디자인 ② SQL 실행과 DB 접근 ③ 브라우저 쿠키 관리 ④ Tomcat 설치
2. SELECT 결과를 얻는 메서드는? ① executeQuery ② executeUpdate ③ sendRedirect ④ forward
3. SQL 인젝션 위험을 줄이는 방법은? ① 입력값을 SQL에 연결 ② 비밀번호를 JSP에 기록 ③ PreparedStatement 바인딩 ④ GET으로 삭제
4. DTO에 가장 적절한 내용은? ① HTML 전체 ② DB 관리자 비밀번호 ③ 서버 종료 코드 ④ 회원 id·이름·이메일
5. 회원 수정에서 WHERE id=?를 빠뜨리면? ① 수정이 자동 취소됨 ② 모든 행이 변경될 수 있음 ③ SELECT로 바뀜 ④ JSP가 생성됨
6. 저장 후 redirect하는 주된 목적은? ① DB 비밀번호 변경 ② SQL 문법 검사 ③ 새로고침 POST 재전송 완화 ④ JSP 컴파일 생략
7. 사용자 이름을 JSP에 안전하게 출력하는 태그는? ① c:out ② c:import ③ sql:update ④ jsp:include
8. Tomcat 10.1 Servlet의 import는? ① java.servlet ② javax.servlet ③ mysql.servlet ④ jakarta.servlet

### 단답형

9. Connection, PreparedStatement, ResultSet을 자동 종료하는 Java 문법을 쓰시오.
10. UPDATE와 DELETE 실행 결과로 반환되는 int의 의미를 쓰시오.
11. forward와 redirect의 요청 유지 여부 차이를 쓰시오.
12. 회원 삭제에 GET 대신 POST를 사용하는 이유를 쓰시오.
13. 같은 이메일을 동시에 등록할 때 중복을 최종적으로 방지하는 DB 제약조건을 쓰시오.

### 정답 및 해설

1 ②(DAO는 DB 접근 담당), 2 ①(SELECT의 ResultSet 반환), 3 ③(값을 SQL 문법과 분리), 4 ④(데이터 전달 객체), 5 ②(조건 없는 UPDATE는 전체 대상), 6 ③(PRG), 7 ①(HTML 이스케이프), 8 ④(Jakarta API).

9 try-with-resources. 10 변경된 행 수. 11 forward는 같은 요청과 request 속성을 유지하고 서버 내부에서 이동하며, redirect는 브라우저의 새 요청이므로 request 속성은 유지되지 않는다. 12 조회용 GET에 변경 부작용을 두지 않고 우발적인 링크 접근에 의한 삭제를 피하기 위해서이다(POST만으로 보안이 완성되지는 않음). 13 UNIQUE 제약조건.

## 6. 학습요약

- Servlet은 요청을 받고 검증·분기를 수행한다.
- DTO는 회원 값을 전달하고 DAO는 JDBC로 SQL을 실행한다.
- JSP는 request 속성을 EL/JSTL로 출력한다.
- SELECT는 executeQuery, 변경 SQL은 executeUpdate를 사용한다.
- WHERE 조건, 파라미터 바인딩, 자원 종료, UNIQUE 제약조건을 함께 확인한다.
- CRUD 성공 여부는 웹 화면과 DB 조회를 비교해 판단한다.

공식 참고: [Tomcat 10.1 문서](https://tomcat.apache.org/tomcat-10.1-doc/), [Tomcat 10.1 마이그레이션](https://tomcat.apache.org/migration-10.1.html), [Connector/J Maven 설치](https://dev.mysql.com/doc/connector-j/en/connector-j-installing-maven.html).
## 오류 해결

| 증상 | 확인 및 해결 |
|---|---|
| 404 /member-crud/members | 앱이 서버에 추가되었는지, Context root와 @WebServlet 경로 확인 |
| javax.servlet 관련 컴파일 오류 | import를 jakarta.servlet로 통일, Tomcat 10.1 Runtime 확인 |
| JRE 21 오류 / class version 오류 | Eclipse 실행 JDK와 서버 Runtime JDK를 모두 21로 지정 |
| Driver 클래스 없음 | Maven Update, Deployment Assembly의 Maven Dependencies, WAR의 WEB-INF/lib 확인 |
| Communications link failure | MySQL80 서비스, 3306 포트, db.url 확인 |
| Access denied | db.properties와 SQL 계정/비밀번호/호스트 확인 |
| Public Key Retrieval is not allowed | 로컬 실습 계정 연결 설정 확인. 로컬에서만 db.url 끝에 &allowPublicKeyRetrieval=true&sslMode=DISABLED를 추가하여 재배포할 수 있음. 외부 서버에서는 TLS 설정 사용 |
| Table doesn't exist | javaapp_week11 선택 및 01_schema.sql 실행 확인 |
| JSTL 태그를 못 찾음 | jakarta.tags.core URI와 JSTL API/구현 두 의존성 확인 |
| 한글 깨짐 | 파일 UTF-8, JSP page 지시자, 요청 파라미터 읽기 전 setCharacterEncoding 확인 |
| 403 | 같은 브라우저에서 입력화면을 다시 열어 CSRF 토큰과 세션 재발급 |
| 409 | 중복 이메일을 고친 뒤 다시 저장 |
| Maven 다운로드 실패 | 네트워크와 프록시 설정 확인 후 Maven Update. 이것은 Java 코드 오류와 구분 |
