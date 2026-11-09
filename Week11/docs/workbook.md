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

## 완성할 프로그램

교육센터 직원이 회원 이름·이메일·전화번호를 관리하는 로컬 웹 도구를 만듭니다. 목록에는 번호/이름/이메일/전화번호/수정/삭제가 표시되고, 위쪽에 검색창과 회원 등록 버튼이 있습니다. 등록과 수정은 같은 입력화면을 사용합니다. 검색 결과가 없으면 안내문이 나옵니다.

| 화면 | 사용자가 수행하는 일 | 다음 화면 |
|---|---|---|
| 회원 목록 | 검색, 등록 선택, 수정 선택, 삭제 확인 | 입력 폼 또는 갱신된 목록 |
| 회원 등록 | 이름·이메일·전화 입력 후 저장 | 새 회원이 추가된 목록 |
| 회원 수정 | 기존 값 확인 후 전화번호 변경 | 변경된 회원 목록 |
| 오류 안내 | 중복 이메일이나 잘못된 입력 수정 | 입력값을 유지한 폼 |

예상 초기 목록: 김자바(java@example.com), 이서블릿(servlet@example.com). id는 자동 증가하므로 중간 삭제 후에도 연속 번호가 아닐 수 있습니다. id가 반드시 1, 2라고 가정하지 마세요.

## 1단계. 환경과 DB 준비

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

## 2단계. 파일 작성 순서

먼저 pom.xml과 SQL을 작성합니다. 이어 DB 설정 → DTO → 연결 도우미 → DAO → Servlet → JSP/CSS 순서로 아래 파일을 생성합니다. Java 파일은 `src/main/java/edu/week11`, JSP는 `src/main/webapp/WEB-INF/views`에 둡니다. src/main/resources 폴더가 없으면 Source Folder로 생성하세요. 아래는 생략 없는 완성 소스입니다. 파일명이 같은 코드 블록은 해당 파일 전체를 교체합니다.

| 순서 | 파일 | 확인 포인트 |
|---|---|---|
| 1 | pom.xml | Java 21, WAR, Servlet provided |
| 2 | sql/01_schema.sql | 초기 데이터 2명 |
| 3 | db.properties / DB.java | 로컬 계정 일치 |
| 4 | Member.java | getter와 EL 속성 연결 |
| 5 | MemberDao.java | 5개 메서드와 SQL 바인딩 |
| 6 | MemberServlet.java | GET/POST 분리와 입력검증 |
| 7 | list.jsp / form.jsp | c:out, contextPath, CSRF hidden |
| 8 | index.jsp / web.xml / style.css | 시작 URL과 화면 스타일 |

### pom.xml

의존성을 입력한 뒤 Maven Update를 실행합니다.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>edu.week11</groupId>
    <artifactId>member-crud</artifactId>
    <version>1.0.0</version>
    <packaging>war</packaging>
    <properties>
        <maven.compiler.release>21</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>
        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <version>6.0.0</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <version>9.4.0</version>
        </dependency>
        <dependency>
            <groupId>jakarta.servlet.jsp.jstl</groupId>
            <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
            <version>3.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.glassfish.web</groupId>
            <artifactId>jakarta.servlet.jsp.jstl</artifactId>
            <version>3.0.1</version>
        </dependency>
    </dependencies>
    <build>
        <finalName>member-crud</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.4.0</version>
            </plugin>
        </plugins>
    </build>
</project>
```

### sql/01_schema.sql

Workbench 관리자 연결에서 실행합니다.

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

### src/main/resources/db.properties

비밀번호를 바꿨다면 이 설정도 동일하게 변경합니다.

```properties
# 로컬 수업 전용 계정. 실제 서비스 비밀정보를 이 파일에 저장하지 마세요.
db.url=jdbc:mysql://localhost:3306/javaapp_week11?connectionTimeZone=Asia/Seoul&characterEncoding=UTF-8
db.user=javaapp11
db.password=Local11!Change
```

### src/main/java/edu/week11/Member.java

DTO에는 SQL을 넣지 않습니다.

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

### src/main/java/edu/week11/DB.java

Connection은 DAO가 try 블록에서 닫습니다.

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

### src/main/java/edu/week11/MemberDao.java

findAll은 검색과 목록을 겸합니다. 빈 검색어는 모든 행을 조회합니다. LIKE의 %와 _는 와일드카드로 동작합니다.

```java
package edu.week11;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {
    private Member map(ResultSet rs) throws SQLException {
        return new Member(rs.getLong("id"), rs.getString("name"),
            rs.getString("email"), rs.getString("phone"));
    }

    public List<Member> findAll(String keyword) throws SQLException {
        String sql = "SELECT id, name, email, phone FROM members "
            + "WHERE name LIKE ? OR email LIKE ? ORDER BY id DESC";
        List<Member> members = new ArrayList<>();
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    members.add(map(rs));
                }
            }
        }
        return members;
    }

    public Member findById(long id) throws SQLException {
        String sql = "SELECT id, name, email, phone FROM members WHERE id=?";
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public int insert(Member m) throws SQLException {
        String sql = "INSERT INTO members(name, email, phone) VALUES (?, ?, ?)";
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getName());
            ps.setString(2, m.getEmail());
            ps.setString(3, m.getPhone());
            return ps.executeUpdate();
        }
    }

    public int update(Member m) throws SQLException {
        String sql = "UPDATE members SET name=?, email=?, phone=? WHERE id=?";
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getName());
            ps.setString(2, m.getEmail());
            ps.setString(3, m.getPhone());
            ps.setLong(4, m.getId());
            return ps.executeUpdate();
        }
    }

    public int delete(long id) throws SQLException {
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement("DELETE FROM members WHERE id=?")) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        }
    }
}
```

### src/main/java/edu/week11/MemberServlet.java

모든 변경 요청은 토큰을 검사합니다. 수정/삭제 대상이 없으면 404를 반환합니다.

```java
package edu.week11;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/members")
public class MemberServlet extends HttpServlet {
    private final MemberDao dao = new MemberDao();

    private String value(HttpServletRequest req, String key) {
        String text = req.getParameter(key);
        return text == null ? "" : text.trim();
    }

    private long id(HttpServletRequest req) {
        long id = Long.parseLong(value(req, "id"));
        if (id <= 0) {
            throw new IllegalArgumentException("잘못된 회원 번호");
        }
        return id;
    }

    private void token(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("csrf") == null) {
            session.setAttribute("csrf", UUID.randomUUID().toString());
        }
    }

    private void form(HttpServletRequest req, HttpServletResponse resp, Member m)
            throws ServletException, IOException {
        req.setAttribute("member", m);
        req.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Cache-Control", "no-store");
        token(req);
        try {
            switch (value(req, "action")) {
                case "new" -> form(req, resp, new Member(0, "", "", ""));
                case "edit" -> {
                    Member m = dao.findById(id(req));
                    if (m == null) {
                        resp.sendError(404);
                    } else {
                        form(req, resp, m);
                    }
                }
                case "", "list" -> {
                    String q = value(req, "q");
                    req.setAttribute("q", q);
                    req.setAttribute("members", dao.findAll(q));
                    req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
                }
                default -> resp.sendError(400);
            }
        } catch (IllegalArgumentException e) {
            resp.sendError(400);
        } catch (SQLException e) {
            throw new ServletException("회원 조회 실패: 서버 로그와 DB 설정을 확인하세요.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || !value(req, "csrf").equals(session.getAttribute("csrf"))) {
            resp.sendError(403);
            return;
        }
        String action = value(req, "action");
        Member m = null;
        try {
            if (action.equals("delete")) {
                if (dao.delete(id(req)) == 0) {
                    resp.sendError(404);
                    return;
                }
            } else if (action.equals("save")) {
                long memberId = value(req, "id").isEmpty() ? 0 : id(req);
                m = new Member(memberId, value(req, "name"),
                    value(req, "email"), value(req, "phone"));
                if (m.getName().isEmpty() || m.getName().length() > 50
                        || m.getEmail().length() > 120
                        || !m.getEmail().matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")
                        || !m.getPhone().matches("[0-9+() -]{0,20}")) {
                    resp.setStatus(400);
                    req.setAttribute("error", "이름(1~50자), 이메일(120자 이하), 전화번호(20자 이하)를 확인하세요.");
                    form(req, resp, m);
                    return;
                }
                int count = memberId == 0 ? dao.insert(m) : dao.update(m);
                if (count == 0) {
                    resp.sendError(404);
                    return;
                }
            } else {
                resp.sendError(400);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/members");
        } catch (IllegalArgumentException e) {
            resp.sendError(400);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062 && m != null) {
                resp.setStatus(409);
                req.setAttribute("error", "이미 등록된 이메일입니다. 다른 이메일을 입력하세요.");
                form(req, resp, m);
            } else {
                throw new ServletException("회원 변경 실패: 서버 로그를 확인하세요.", e);
            }
        }
    }
}
```

### src/main/webapp/WEB-INF/views/list.jsp

폼의 action은 contextPath를 포함해 배포 경로 변경에 대응합니다.

```html
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<main>
    <p class="eyebrow">JAVA APPLICATION · WEEK 11</p>
    <h1>교육센터 회원 관리</h1>
    <p>회원 정보를 등록하고 검색·수정·삭제합니다.</p>
    <form method="get" action="${pageContext.request.contextPath}/members" class="toolbar">
        <input name="q" value="<c:out value='${q}'/>" placeholder="이름 또는 이메일 검색" aria-label="검색어">
        <button>검색</button>
        <a href="${pageContext.request.contextPath}/members">전체 보기</a>
        <a class="button" href="${pageContext.request.contextPath}/members?action=new">회원 등록</a>
    </form>
    <table>
        <thead><tr><th>번호</th><th>이름</th><th>이메일</th><th>전화번호</th><th>관리</th></tr></thead>
        <tbody>
        <c:forEach var="m" items="${members}">
            <tr>
                <td><c:out value="${m.id}"/></td>
                <td><c:out value="${m.name}"/></td>
                <td><c:out value="${m.email}"/></td>
                <td><c:out value="${m.phone}"/></td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/members?action=edit&amp;id=${m.id}">수정</a>
                    <form method="post" action="${pageContext.request.contextPath}/members"
                          onsubmit="return confirm('이 회원을 삭제할까요?');">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${m.id}">
                        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                        <button class="danger">삭제</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty members}">
            <tr><td colspan="5">조회된 회원이 없습니다.</td></tr>
        </c:if>
        </tbody>
    </table>
</main>
</body>
</html>
```

### src/main/webapp/WEB-INF/views/form.jsp

입력 오류가 나도 member 속성으로 입력값을 유지합니다.

```html
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 입력</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<main class="narrow">
    <p class="eyebrow">MEMBER INFORMATION</p>
    <h1>${member.id == 0 ? '회원 등록' : '회원 수정'}</h1>
    <p class="error" role="alert"><c:out value="${error}"/></p>
    <form method="post" action="${pageContext.request.contextPath}/members">
        <input type="hidden" name="action" value="save">
        <c:if test="${member.id != 0}">
            <input type="hidden" name="id" value="${member.id}">
        </c:if>
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <label>이름
            <input name="name" value="<c:out value='${member.name}'/>" maxlength="50" required>
        </label>
        <label>이메일
            <input type="email" name="email" value="<c:out value='${member.email}'/>" maxlength="120" required>
        </label>
        <label>전화번호
            <input name="phone" value="<c:out value='${member.phone}'/>" maxlength="20" placeholder="010-1234-5678">
        </label>
        <button>저장</button>
        <a href="${pageContext.request.contextPath}/members">목록으로</a>
    </form>
</main>
</body>
</html>
```

### src/main/webapp/index.jsp

루트 URL로 들어오면 목록으로 이동합니다.

```html
<%@ page contentType="text/html; charset=UTF-8" %>
<% response.sendRedirect(request.getContextPath() + "/members"); %>
```

### src/main/webapp/WEB-INF/web.xml

Servlet은 annotation으로 매핑하므로 web.xml에 중복 등록하지 않습니다.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee" version="6.0">
    <display-name>Week11 Member CRUD</display-name>
    <welcome-file-list>
        <welcome-file>index.jsp</welcome-file>
    </welcome-file-list>
    <session-config>
        <cookie-config><http-only>true</http-only></cookie-config>
    </session-config>
</web-app>
```

### src/main/webapp/style.css

CSS 저장 후 브라우저 강력 새로고침으로 적용을 확인합니다.

```css
* { box-sizing: border-box; }
body { margin: 0; background: #f1f5f9; color: #172b4d; font-family: "Malgun Gothic", sans-serif; }
main { max-width: 1080px; margin: 60px auto; padding: 36px; background: white; border-radius: 18px; box-shadow: 0 12px 40px #17345612; }
main.narrow { max-width: 600px; }
.eyebrow { color: #267c80; font-size: 12px; letter-spacing: 2px; font-weight: bold; }
h1 { font-size: 30px; }
.toolbar, .actions { display: flex; align-items: center; gap: 12px; }
.toolbar { margin: 30px 0; flex-wrap: wrap; }
input { padding: 12px; border: 1px solid #b9c6d4; border-radius: 6px; font: inherit; }
button, .button { padding: 11px 18px; border: 0; border-radius: 6px; background: #146b70; color: white; cursor: pointer; font: inherit; text-decoration: none; }
a { color: #146b70; }
table { width: 100%; border-collapse: collapse; }
th, td { text-align: left; padding: 16px 12px; border-bottom: 1px solid #e2e8f0; }
th { background: #f1f5f9; }
label { display: block; margin: 24px 0; }
label input { display: block; width: 100%; margin-top: 8px; }
.danger { background: #fff1f2; color: #b4233a; padding: 6px 12px; }
.error { color: #b4233a; }
@media(max-width: 700px) { main { margin: 12px; padding: 18px; overflow-x: auto; } }
```

## 3단계. 목록 → 등록

1. Run on Server 후 `/member-crud/members`를 엽니다. 초기 데이터 2명이 보이는지 확인합니다.
2. 회원 등록을 누릅니다. 이름 `박학생`, 이메일 `park@example.com`, 전화 `010-5555-6666`을 입력합니다.
3. 저장을 누릅니다. URL이 `/members`로 돌아오고 새 회원이 목록 상단에 나타나야 합니다.
4. 새로고침합니다. 같은 회원이 한 번 더 생기지 않아야 합니다.
5. Workbench에서 아래 SELECT를 실행합니다. 실제 id를 결과보고서에 기록합니다.

```sql
SELECT id, name, email, phone
FROM javaapp_week11.members
WHERE email = 'park@example.com';
```

## 4단계. 수정

1. 박학생 행의 수정을 누릅니다. 기존 값 3개가 입력창에 표시됩니다.
2. 전화번호를 `010-9999-0000`으로 변경하고 저장합니다.
3. 같은 SELECT를 실행합니다. id와 이메일은 같고 phone만 바뀌어야 합니다.
4. MemberDao.update의 `ps.setLong(4, m.getId())`를 확인합니다. WHERE 조건의 자리에 id가 바인딩됩니다.

## 5단계. 검색과 삭제

1. 검색창에 `박학생`을 입력합니다. 해당 회원이 표시되어야 합니다. 이메일 일부 `park@`로도 검색합니다.
2. `없는회원XYZ`를 입력하면 조회된 회원이 없다는 문구가 나옵니다. 전체 보기를 눌러 복귀합니다.
3. 박학생의 삭제를 누르고 먼저 취소합니다. 행이 남아 있어야 합니다.
4. 다시 삭제를 누르고 확인합니다. 목록에서 사라지고 같은 SELECT 결과가 0행이어야 합니다.
5. 삭제 후 새 회원을 등록했을 때 번호가 건너뛰어도 정상입니다.

## 6단계. 오류를 관찰하고 설명하기

| 실험 | 입력/행동 | 예상 결과 |
|---|---|---|
| 중복 이메일 | 다른 이름으로 java@example.com 등록 | 중복 안내, 새 행 없음, HTTP 409 |
| 필수값 | 이름 비우기 | 브라우저 검증. 우회 요청도 서버에서 400 |
| 잘못된 번호 | /members?action=edit&id=abc | HTTP 400 |
| 없는 번호 | /members?action=edit&id=999999999 | 해당 id가 없다면 HTTP 404 |
| GET 변경 시도 | /members?action=delete&id=1 | HTTP 400, 삭제되지 않음 |
| 출력 특수문자 | 이름에 <b>테스트</b>, 새 이메일 | 굵게 처리되지 않고 글자 그대로 출력 |
| 토큰 누락 | 개발자도구로 hidden csrf 제거 후 POST | HTTP 403 |

특수문자 출력 실험의 테스트 회원은 확인 후 삭제하세요. 실습 결과와 HTTP 상태는 브라우저 개발자도구 Network 탭에서 확인할 수 있습니다.

## 7단계. 결과 보고서

| 질문 | 실행 근거 / 학생 답안 |
|---|---|
| 등록한 회원의 실제 id는? | |
| 수정 전후 전화번호와 SELECT 결과는? | |
| 삭제 성공을 0행으로 확인했는가? | |
| DTO와 DAO에 각각 어떤 코드가 있는가? | |
| 새로고침 때 POST가 반복되지 않는 이유는? | |
| 검색어에 따옴표를 넣어도 SQL 구조가 바뀌지 않는 이유는? | |
| HTML required와 서버 검증이 모두 필요한 이유는? | |
| Connection을 Servlet 필드에 두면 어떤 문제가 생기는가? | |

제출 체크: 프로젝트 ZIP(pom.xml, src, sql), CRUD 화면 4장 이상, Workbench 검증 결과, 위 질문 답변. target 및 Eclipse 개인 설정은 ZIP에서 제외해도 됩니다.

## 심화 과제

기본 CRUD를 모두 확인한 학생은 가입일 created_at을 DTO와 SELECT에 추가하여 목록에 출력하세요. 수정/삭제 SQL은 기존대로 유지합니다. DB의 timestamp와 Java 타입 간 매핑을 설명하고 기존 기능이 계속 동작하는지 확인합니다.

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
