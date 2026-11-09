## 개발환경

| 항목 | 수업 기준 |
|---|---|
| OS | Windows 10 |
| JDK | Java 21 (JRE만 설치하지 않기) |
| IDE | Eclipse IDE for Enterprise Java and Web Developers 2025-09 |
| DB | MySQL Community Server 8.0.46 |
| DB 도구 | MySQL Workbench 8 |
| 웹 서버 | Apache Tomcat 10.1.x, 실행 JRE Java 21 |
| 프로젝트 | Maven WAR, Servlet 6.0 / JSP 3.1, jakarta.servlet |
| JDBC | MySQL Connector/J 8.4.0 (서버 버전과 별개) |

## 완성 화면과 범위

상단에 **학습 일정 관리** 제목, 검색 영역, 새 항목 등록 폼, 수정 가능한 목록 카드가 표시된다. 각 카드에는 수정 저장과 삭제 버튼이 있다. 검색 결과가 없으면 안내 메시지가 나온다.

| 화면 영역 | 실제 표시 예 |
|---|---|
| 검색 | 할 일 입력 + 검색 + 전체 목록 |
| 새 항목 | 할 일 / 담당자 / 진행 상태 / 등록 |
| 목록 카드 | #1 Servlet 복습 · 김하나 · 예정 |
| 카드 동작 | 값 수정 후 저장 / 삭제 확인 |

이는 기본 CRUD 참고 앱이며 로그인·사용자별 데이터 격리 기능은 포함하지 않는다. 로컬 학습 데이터만 사용한다. title/owner/status는 재사용을 위한 내부 컬럼명이고 화면에서 할 일/담당자/진행 상태로 표시한다.

## 1단계 — 도구와 서버 설정

1. JDK 21을 설치하고 명령 프롬프트에서 `java -version`, `javac -version`을 확인한다.
2. Eclipse → Window → Preferences → Java → Installed JREs에서 JDK 21을 선택한다.
3. Tomcat 10.1 ZIP을 내려받아 압축을 푼다. Eclipse → Window → Preferences → Server → Runtime Environments → Add → Apache Tomcat v10.1, 설치 폴더와 JDK 21을 지정한다.
4. MySQL Server를 시작하고 Workbench에서 localhost:3306에 접속한다.

## 2단계 — 데이터베이스 생성

Workbench의 SQL 편집기에서 아래 schema.sql 전체를 실행한다. 그 다음 seed.sql을 **최초 1회** 실행한다. seed 재실행은 데이터를 추가하므로 중복 입력에 주의한다. schema.sql은 기존 테이블 구조를 변경하지 않는다.

root 또는 관리자 연결에서 아래 계정을 최초 1회 생성한다. 비밀번호 문구는 자신의 로컬 비밀번호로 교체한다. 이미 존재하는 계정은 새로 만들지 말고 기존 설정을 사용하거나 관리자에게 변경을 요청한다.

```sql
CREATE USER 'javaapp16'@'localhost' IDENTIFIED BY '로컬에서_정한_비밀번호';
GRANT SELECT, INSERT, UPDATE, DELETE ON week16_study_planner.* TO 'javaapp16'@'localhost';
```

DAO는 `DB_PASSWORD` 환경변수를 읽는다. Eclipse Servers의 Tomcat 서버를 더블클릭 → Open launch configuration → Environment → New에서 해당 이름과 같은 비밀번호를 추가한다. 서버를 완전히 중지한 뒤 재시작한다. 비밀번호는 소스에 쓰지 않는다.

## 3단계 — 프로젝트 가져오기

1. 배포 ZIP을 압축 해제하고 Eclipse → File → Import → Maven → Existing Maven Projects를 선택한다.
2. `projects/study-planner` 폴더를 지정하고 pom.xml이 선택되었는지 확인한다. 세 앱은 각각 독립 프로젝트다.
3. Maven 의존성 다운로드를 기다린다. 오류가 남으면 프로젝트 우클릭 → Maven → Update Project를 실행한다.
4. Properties → Java Compiler 21, Project Facets의 Java 21 / Dynamic Web Module 6.0, Targeted Runtimes의 Tomcat 10.1을 확인한다.
5. Properties → Web Project Settings의 Context root를 `study-planner`로 설정한다.
6. 프로젝트 우클릭 → Run As → Run on Server → Tomcat 10.1 → Finish.
7. 브라우저에서 `http://localhost:8080/study-planner/items`를 연다. `/`에 환영 페이지는 없으므로 `/items`까지 입력한다.

별도 Maven이 설치된 경우 프로젝트 폴더에서 `mvn clean package`로 WAR를 만들고 target/study-planner.war를 Tomcat webapps에 배포할 수도 있다. Eclipse 실행과 외부 Tomcat 실행을 동시에 하지 않는다.

## 4단계 — 동작 확인

1. 초기 두 항목이 보이는지 확인한다.
2. 새 할 일, 담당자, 진행 상태를 입력하고 등록한다. 새 항목이 목록 상단에 나타난다.
3. 카드의 값을 변경하고 수정 저장한다. 화면과 DB의 값이 일치한다.
4. 제목 일부를 검색한다. 전체 목록을 눌러 필터를 해제한다.
5. 삭제 후 목록에서 사라지는지 확인한다. 취소를 누르면 삭제되지 않는다.
6. Workbench에서 `SELECT * FROM week16_study_planner.items ORDER BY id DESC;`를 실행해 저장 결과를 확인한다.
7. 서버 재시작 후 데이터가 유지되는지 확인한다.
8. 빈 필드는 브라우저가 차단한다. 개발자 도구로 required를 제거해 보내도 서버가 400으로 거부하는지 확인한다. 잘못된 CSRF 토큰은 403, 없는 ID 수정·삭제는 404다.

## 5단계 — 파일별 전체 소스

아래 경로와 이름을 그대로 사용한다. 파일은 UTF-8로 저장한다. JSP는 표현만 담당하고 SQL은 DAO에 둔다. Servlet은 요청 검증과 화면 이동을 담당한다.

### pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>edu.javaapp</groupId>
  <artifactId>study-planner</artifactId>
  <version>1.0.0</version>
  <packaging>war</packaging>
  <properties>
    <maven.compiler.release>21</maven.compiler.release>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>
  <dependencies>
    <dependency><groupId>jakarta.servlet</groupId><artifactId>jakarta.servlet-api</artifactId><version>6.0.0</version><scope>provided</scope></dependency>
    <dependency><groupId>jakarta.servlet.jsp.jstl</groupId><artifactId>jakarta.servlet.jsp.jstl-api</artifactId><version>3.0.0</version></dependency>
    <dependency><groupId>org.glassfish.web</groupId><artifactId>jakarta.servlet.jsp.jstl</artifactId><version>3.0.1</version></dependency>
    <dependency><groupId>com.mysql</groupId><artifactId>mysql-connector-j</artifactId><version>8.4.0</version></dependency>
  </dependencies>
  <build>
    <finalName>study-planner</finalName>
    <plugins>
      <plugin><groupId>org.apache.maven.plugins</groupId><artifactId>maven-compiler-plugin</artifactId><version>3.13.0</version></plugin>
      <plugin><groupId>org.apache.maven.plugins</groupId><artifactId>maven-war-plugin</artifactId><version>3.4.0</version></plugin>
    </plugins>
  </build>
</project>
```

### src/main/java/edu/javaapp/ItemDao.java

```java
package edu.javaapp;

import java.sql.*;
import java.util.*;

public class ItemDao {
    public static class Item {
        private final long id;
        private final String title, owner, status;
        public Item(long id, String title, String owner, String status) {
            this.id = id;
            this.title = title;
            this.owner = owner;
            this.status = status;
        }
        public long getId() { return id; }
        public String getTitle() { return title; }
        public String getOwner() { return owner; }
        public String getStatus() { return status; }
    }

    private Connection open() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC driver missing", e);
        }
        String password = System.getenv("DB_PASSWORD");
        if (password == null || password.isBlank()) {
            throw new SQLException("DB_PASSWORD is required");
        }
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/week16_study_planner?characterEncoding=UTF-8&connectionTimeZone=Asia/Seoul",
            "javaapp16", password);
    }

    public List<Item> list(String query) throws SQLException {
        List<Item> result = new ArrayList<>();
        String sql = "SELECT id,title,owner,status FROM items "
                   + "WHERE LOCATE(?,title)>0 ORDER BY id DESC";
        try (Connection c = open(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, query);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    result.add(new Item(rs.getLong("id"), rs.getString("title"),
                        rs.getString("owner"), rs.getString("status")));
                }
            }
        }
        return result;
    }

    public int save(long id, String title, String owner, String status) throws SQLException {
        String sql = id == 0
            ? "INSERT INTO items(title,owner,status) VALUES(?,?,?)"
            : "UPDATE items SET title=?,owner=?,status=? WHERE id=?";
        try (Connection c = open(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, title);
            p.setString(2, owner);
            p.setString(3, status);
            if (id != 0) p.setLong(4, id);
            return p.executeUpdate();
        }
    }

    public int delete(long id) throws SQLException {
        try (Connection c = open();
             PreparedStatement p = c.prepareStatement("DELETE FROM items WHERE id=?")) {
            p.setLong(1, id);
            return p.executeUpdate();
        }
    }
}
```

### src/main/java/edu/javaapp/ItemServlet.java

```java
package edu.javaapp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/items")
public class ItemServlet extends HttpServlet {
    private final ItemDao dao = new ItemDao();
    private static final List<String> STATUSES = List.of("예정","진행","완료");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String q = Objects.toString(req.getParameter("q"), "").strip();
        if (q.length() > 100) {
            resp.sendError(400, "검색어는 100자 이하입니다.");
            return;
        }
        HttpSession session = req.getSession();
        if (session.getAttribute("csrf") == null) {
            session.setAttribute("csrf", UUID.randomUUID().toString());
        }
        try {
            req.setAttribute("items", dao.list(q));
            req.setAttribute("statuses", STATUSES);
            req.getRequestDispatcher("/WEB-INF/views/items.jsp").forward(req, resp);
        } catch (SQLException e) {
            getServletContext().log("Database read failed", e);
            resp.sendError(500, "DB 연결과 서버 로그를 확인하세요.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        String token = req.getParameter("csrf");
        if (session == null || token == null || !token.equals(session.getAttribute("csrf"))) {
            resp.sendError(403, "화면을 새로 열어 다시 시도하세요.");
            return;
        }
        try {
            String action = req.getParameter("action");
            long id = Long.parseLong(Objects.toString(req.getParameter("id"), "0"));
            if (id < 0) throw new IllegalArgumentException();
            int changed;
            if ("delete".equals(action) && id > 0) {
                changed = dao.delete(id);
            } else if ("save".equals(action)) {
                String title = Objects.toString(req.getParameter("title"), "").strip();
                String owner = Objects.toString(req.getParameter("owner"), "").strip();
                String status = Objects.toString(req.getParameter("status"), "");
                if (title.isEmpty() || title.length() > 100 || owner.isEmpty()
                        || owner.length() > 60 || !STATUSES.contains(status)) {
                    throw new IllegalArgumentException();
                }
                changed = dao.save(id, title, owner, status);
            } else {
                throw new IllegalArgumentException();
            }
            if (changed == 0) {
                resp.sendError(404, "대상이 없습니다. 목록을 새로 확인하세요.");
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/items");
        } catch (IllegalArgumentException e) {
            resp.sendError(400, "입력값을 확인하세요. 제목 1~100자, 작성자 1~60자입니다.");
        } catch (SQLException e) {
            getServletContext().log("Database write failed", e);
            resp.sendError(500, "저장하지 못했습니다. 서버 로그를 확인하세요.");
        }
    }
}
```

### src/main/webapp/WEB-INF/views/items.jsp

```jsp
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>학습 일정 관리</title>
  <style>
    body { font:16px sans-serif; max-width:1000px; margin:40px auto; padding:16px; background:#f3f6fa; }
    section, article { background:white; padding:20px; margin:16px 0; border-radius:12px; }
    input, select, button { padding:10px; margin:4px; max-width:90%; }
    button { background:#1952a0; color:white; border:0; cursor:pointer; }
    label { display:inline-block; }
  </style>
</head>
<body>
  <h1>학습 일정 관리</h1>
  <p>JSP · Servlet · MySQL 개인 프로젝트 참고</p>
  <c:url var="endpoint" value="/items" />
  <section>
    <h2>검색</h2>
    <form method="get" action="${endpoint}">
      <label>할 일 <input name="q" maxlength="100" value="<c:out value='${param.q}' />"></label>
      <button>검색</button>
      <a href="${endpoint}">전체 목록</a>
    </form>
  </section>
  <section>
    <h2>새 항목 등록</h2>
    <form method="post" action="${endpoint}">
      <input type="hidden" name="csrf" value="${sessionScope.csrf}">
      <input type="hidden" name="action" value="save">
      <input type="hidden" name="id" value="0">
      <label>할 일 <input name="title" required maxlength="100"></label>
      <label>담당자 <input name="owner" required maxlength="60"></label>
      <label>진행 상태
        <select name="status">
          <c:forEach var="s" items="${statuses}">
            <option value="${s}"><c:out value="${s}" /></option>
          </c:forEach>
        </select>
      </label>
      <button>등록</button>
    </form>
  </section>
  <h2>목록 · 수정 · 삭제</h2>
  <c:if test="${empty items}"><p>조회 결과가 없습니다.</p></c:if>
  <c:forEach var="item" items="${items}">
    <article>
      <h3>#${item.id} <c:out value="${item.title}" /></h3>
      <form method="post" action="${endpoint}">
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="id" value="${item.id}">
        <label>할 일 <input name="title" required maxlength="100" value="<c:out value='${item.title}' />"></label>
        <label>담당자 <input name="owner" required maxlength="60" value="<c:out value='${item.owner}' />"></label>
        <label>진행 상태
          <select name="status">
            <c:forEach var="s" items="${statuses}">
              <option value="${s}" ${s eq item.status ? 'selected' : ''}><c:out value="${s}" /></option>
            </c:forEach>
          </select>
        </label>
        <button>수정 저장</button>
      </form>
      <form method="post" action="${endpoint}" onsubmit="return confirm('삭제하시겠습니까?');">
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="${item.id}">
        <button>삭제</button>
      </form>
    </article>
  </c:forEach>
</body>
</html>
```

### sql/schema.sql

```sql
CREATE DATABASE IF NOT EXISTS week16_study_planner CHARACTER SET utf8mb4;
USE week16_study_planner;
CREATE TABLE IF NOT EXISTS items (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(100) NOT NULL,
  owner VARCHAR(60) NOT NULL,
  status VARCHAR(20) NOT NULL,
  CONSTRAINT chk_status CHECK (status IN ('예정','진행','완료'))
);
```

### sql/seed.sql

```sql
USE week16_study_planner;
INSERT INTO items(title,owner,status) VALUES
('Servlet 복습','김하나','예정'),
('DB 설계','이두나','진행');
```

## 6단계 — 자신의 프로젝트로 바꾸기

화면 문구만 바꾸는 데 그치지 말고 실제 요구사항에 필요한 컬럼과 업무 규칙을 설계한다. 스키마 → DAO → Servlet 검증 → JSP 순서로 함께 수정한다. 목록의 상태 선택은 단순 수정이며 복잡한 업무 규칙은 추가 구현이 필요하다.

마감일을 DATE 컬럼으로 추가하고 서버에서 날짜를 파싱한다. 담당자별 접근 제한이 필요하면 로그인 사용자 ID를 별도 저장하고 모든 조회·수정·삭제에 소유자 조건을 넣는다. 현재 예제는 담당자 이름을 표시할 뿐 접근을 제한하지 않는다.

## 오류 해결

| 증상 | 확인 |
|---|---|
| 404 | 컨텍스트 경로와 /items, 서버에 프로젝트 추가 여부 |
| jakarta.servlet 오류 | Tomcat 10.1과 Servlet 6.0 의존성, javax 코드 혼용 여부 |
| JSTL 오류 | pom 의존성 다운로드, jakarta.tags.core URI |
| Access denied | 계정·localhost 권한·DB_PASSWORD 일치 여부 |
| Unknown database | schema.sql 실행 및 DB명 |
| 500 | Tomcat Console의 예외 원인 확인, JDBC/서버/설정 순서 점검 |
| 포트 충돌 | 다른 Tomcat 중지 또는 서버 포트 변경 |

참고: https://tomcat.apache.org/tomcat-10.1-doc/ · https://dev.mysql.com/doc/connector-j/en/
