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

## 완성할 웹과 결과 예상

학습 과제 관리 웹에서 과제명과 진행 상태를 등록하고 수정·삭제·검색한다. 목록은 5개씩 보여주며, 입력 오류가 있으면 값을 유지한 채 안내한다. 저장 후 목록으로 이동하고 재실행 뒤에도 MySQL 데이터가 남아 있어야 한다.

| 화면 | 완성 시 보이는 내용 |
| --- | --- |
| 목록 /tasks | 총 건수, 과제명 검색, 과제명·상태·수정·삭제, 이전·다음 |
| 등록 /tasks/new | 과제명 입력, TODO/DOING/DONE 선택, 저장·취소 |
| 수정 /tasks/{id}/edit | 기존 값, 저장 시 해당 번호만 변경 |
| 오류 | 공백 제목 오류, 없는 번호 404, DB 오류 503 안내 |

예제 데이터 6건을 넣으면 첫 페이지 5건·다음 페이지 1건이다. 'Spring'을 검색하면 'Spring 검색 기능 완성' 1건이다. 아래 코드 전체를 순서대로 만들면 독립 실행된다. 본 예제의 View는 Thymeleaf이고 JSP 파일을 섞지 않는다. 자기 JSP 프로젝트를 보완할 때는 본 강의의 JSP 예제와 마지막 점검표를 사용한다.

## 1단계 — JDK와 프로젝트 준비

1. Eclipse Window → Preferences → Java → Installed JREs에서 JDK 21을 추가하고 기본값으로 선택한다.
2. Maven → Existing Maven Projects로 ZIP의 Week15/taskboard를 가져온다. 직접 따라 만드는 경우 File → New → Maven Project → Create a simple project를 선택하고 groupId=edu.week15, artifactId=taskboard로 만든다.
3. 아래 pom.xml을 저장한 뒤 우클릭 → Maven → Update Project를 실행한다. Java Compiler가 21인지 확인한다.
4. src/main/java 아래 패키지 edu.week15, src/main/resources 아래 templates·static 폴더를 만든다. 모든 파일은 UTF-8로 저장한다.

## 2단계 — MySQL 생성 및 예제 데이터

Workbench에서 localhost root 연결을 연다. sql/01_schema.sql을 먼저 실행하고, 02_seed.sql은 최초 한 번만 실행한다. 기존 계정이 있다면 비밀번호가 자동 변경되지 않으므로 실행 환경변수는 그 계정의 실제 비밀번호와 맞춘다. 기존 다른 주차 DB는 건드리지 않는다.

### sql/01_schema.sql

```sql
-- Workbench에서 root로 실행. 기존 DB와 자료는 삭제하지 않는다.
CREATE DATABASE IF NOT EXISTS javaapp15
    CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE javaapp15;
CREATE TABLE IF NOT EXISTS tasks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    status VARCHAR(10) NOT NULL DEFAULT 'TODO',
    CONSTRAINT ck_tasks_status CHECK (status IN ('TODO', 'DOING', 'DONE'))
);
-- 아래 비밀번호 예시는 수업용이며 실제 개인 비밀번호로 바꾼다.
CREATE USER IF NOT EXISTS 'javaapp15'@'localhost' IDENTIFIED BY 'ChangeMe15!';
GRANT SELECT, INSERT, UPDATE, DELETE ON javaapp15.* TO 'javaapp15'@'localhost';
-- 계정이 이미 있다면 CREATE USER IF NOT EXISTS는 비밀번호를 바꾸지 않는다.

```

### sql/02_seed.sql

```sql
USE javaapp15;
-- 최초 1회만 실행한다. 다시 실행하면 예제 행이 추가된다.
INSERT INTO tasks(title, status) VALUES
('JSP 한글 출력 확인', 'DONE'),
('회원 등록 검증 보완', 'DOING'),
('Spring 검색 기능 완성', 'TODO'),
('삭제 확인창 추가', 'TODO'),
('새 PC 실행 설명서 작성', 'DOING'),
('프로젝트 최종 시연', 'TODO');

```

확인 SQL을 실행해 6행이 보이는지 확인한다.

```sql
SELECT id, title, status FROM javaapp15.tasks ORDER BY id DESC;
```

## 3단계 — 빌드와 실행 설정 파일 작성

아래 경로는 taskboard 폴더 기준이다. pom.xml은 의존성과 Java 버전을 고정하고 application.properties는 연결을 지정한다. H2는 자동 검증 전용이며 실제 수업 실행은 MySQL을 쓴다.

### pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.7</version>
        <relativePath/>
    </parent>
    <groupId>edu.week15</groupId>
    <artifactId>taskboard</artifactId>
    <version>1.0.0</version>
    <properties><java.version>21</java.version></properties>
    <dependencies>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-web</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-thymeleaf</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-jdbc</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-validation</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-security</artifactId></dependency>
        <dependency><groupId>com.mysql</groupId><artifactId>mysql-connector-j</artifactId><version>8.0.33</version><scope>runtime</scope></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-test</artifactId><scope>test</scope></dependency>
        <dependency><groupId>org.springframework.security</groupId><artifactId>spring-security-test</artifactId><scope>test</scope></dependency>
        <dependency><groupId>com.h2database</groupId><artifactId>h2</artifactId><scope>test</scope></dependency>
    </dependencies>
    <build><plugins><plugin><groupId>org.springframework.boot</groupId><artifactId>spring-boot-maven-plugin</artifactId></plugin></plugins></build>
</project>

```

### src/main/resources/application.properties

```properties
spring.application.name=week15-taskboard
server.address=127.0.0.1
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/javaapp15?connectionTimeZone=Asia/Seoul&characterEncoding=UTF-8
spring.datasource.username=${DB_USER:javaapp15}
spring.datasource.password=${DB_PASSWORD}
spring.sql.init.mode=never
server.error.include-message=never
server.error.include-stacktrace=never
spring.thymeleaf.cache=false

```

## 4단계 — Java 코드 작성

Application을 시작점으로 사용한다. TaskForm은 검증, Repository는 SQL, Controller는 요청과 화면 연결, Advice는 DB 장애 안내를 담당한다. WebSecurity는 수업용 전체 접근과 CSRF 보호를 설정한다. 아래 파일을 모두 작성한 뒤 실행한다.

### src/main/java/edu/week15/Application.java

```java
package edu.week15;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

```

### src/main/java/edu/week15/DatabaseErrorAdvice.java

```java
package edu.week15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DatabaseErrorAdvice {
    private static final Logger log = LoggerFactory.getLogger(DatabaseErrorAdvice.class);

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String databaseError(DataAccessException exception) {
        log.error("Database operation failed", exception);
        return "db-error";
    }
}

```

### src/main/java/edu/week15/Task.java

```java
package edu.week15;

public record Task(long id, String title, String status) { }

```

### src/main/java/edu/week15/TaskController.java

```java
package edu.week15;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
public class TaskController {
    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home() { return "redirect:/tasks"; }

    @GetMapping("/tasks")
    public String list(@RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page, Model model) {
        if (page < 0 || page > 100000 || q.length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        model.addAttribute("tasks", repository.find(q, page));
        model.addAttribute("count", repository.count(q));
        model.addAttribute("q", q);
        model.addAttribute("page", page);
        return "list";
    }

    @GetMapping("/tasks/new")
    public String create(Model model) {
        model.addAttribute("taskForm", new TaskForm());
        model.addAttribute("id", null);
        return "form";
    }

    @GetMapping("/tasks/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        Task task = repository.get(id);
        TaskForm form = new TaskForm();
        form.setTitle(task.title());
        form.setStatus(task.status());
        model.addAttribute("taskForm", form);
        model.addAttribute("id", id);
        return "form";
    }

    @PostMapping("/tasks")
    public String insert(@Valid @ModelAttribute TaskForm taskForm,
            BindingResult errors, Model model) {
        return save(null, taskForm, errors, model);
    }

    @PostMapping("/tasks/{id}")
    public String update(@PathVariable long id,
            @Valid @ModelAttribute TaskForm taskForm,
            BindingResult errors, Model model) {
        repository.get(id);
        return save(id, taskForm, errors, model);
    }

    private String save(Long id, TaskForm form,
            BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("id", id);
            return "form";
        }
        repository.save(id, form);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/delete")
    public String delete(@PathVariable long id) {
        repository.delete(id);
        return "redirect:/tasks";
    }
}

```

### src/main/java/edu/week15/TaskForm.java

```java
package edu.week15;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TaskForm {
    @NotBlank(message = "제목을 입력하세요.")
    @Size(max = 100, message = "제목은 100자 이내입니다.")
    private String title = "";

    @NotBlank(message = "상태를 선택하세요.")
    @Pattern(regexp = "TODO|DOING|DONE", message = "올바른 상태를 선택하세요.")
    private String status = "TODO";

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

```

### src/main/java/edu/week15/TaskRepository.java

```java
package edu.week15;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbc;

    public TaskRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Task> find(String keyword, int page) {
        // LOCATE로 %, _도 일반 문자로 검색한다. 정렬은 고정한다.
        return jdbc.query("""
                SELECT id, title, status FROM tasks
                WHERE LOCATE(?, title) > 0
                ORDER BY id DESC LIMIT 5 OFFSET ?
                """, (rs, row) -> new Task(rs.getLong("id"),
                rs.getString("title"), rs.getString("status")),
                keyword, page * 5);
    }

    public long count(String keyword) {
        return jdbc.queryForObject(
                "SELECT COUNT(*) FROM tasks WHERE LOCATE(?, title) > 0",
                Long.class, keyword);
    }

    public Task get(long id) {
        return jdbc.query("SELECT id, title, status FROM tasks WHERE id = ?",
                (rs, row) -> new Task(rs.getLong("id"),
                        rs.getString("title"), rs.getString("status")), id)
                .stream().findFirst().orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void save(Long id, TaskForm form) {
        if (id == null) {
            jdbc.update("INSERT INTO tasks(title, status) VALUES (?, ?)",
                    form.getTitle().strip(), form.getStatus());
        } else {
            // 같은 값으로 저장하더라도 존재하는 행은 정상 처리한다.
            get(id);
            jdbc.update("UPDATE tasks SET title = ?, status = ? WHERE id = ?",
                    form.getTitle().strip(), form.getStatus(), id);
        }
    }

    public void delete(long id) {
        if (jdbc.update("DELETE FROM tasks WHERE id = ?", id) != 1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

```

### src/main/java/edu/week15/WebSecurity.java

```java
package edu.week15;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurity {
    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        // 개인 PC 수업용. 인증 기능은 없으며 CSRF 보호는 유지한다.
        return http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }
}

```

## 5단계 — 화면과 스타일 작성

th:field는 입력값을 연결하고 th:errors는 오류를 표시한다. th:text는 사용자 입력을 텍스트로 출력한다. th:action을 사용한 POST 폼의 토큰을 임의로 제거하지 않는다.

### src/main/resources/templates/db-error.html

```html
<!DOCTYPE html>
<html lang="ko"><head><meta charset="UTF-8"><title>연결 확인</title></head>
<body><h1>데이터를 처리할 수 없습니다.</h1>
<p>잠시 후 다시 시도하세요. 실습 중이라면 MySQL 서비스와 실행 설정을 확인하세요.</p>
<a href="/tasks">다시 시도</a></body></html>

```

### src/main/resources/templates/form.html

```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>과제 등록 및 수정</title>
    <link rel="stylesheet" th:href="@{/style.css}">
</head>
<body><main>
    <h1 th:text="${id == null ? '과제 등록' : '과제 수정'}"></h1>
    <form method="post" th:object="${taskForm}"
          th:action="${id == null} ? @{/tasks} : @{/tasks/{id}(id=${id})}">
        <label>과제명
            <input th:field="*{title}" maxlength="100" required>
        </label>
        <p class="error" th:errors="*{title}"></p>
        <label>상태
            <select th:field="*{status}">
                <option value="TODO">TODO · 예정</option>
                <option value="DOING">DOING · 진행</option>
                <option value="DONE">DONE · 완료</option>
            </select>
        </label>
        <p class="error" th:errors="*{status}"></p>
        <button>저장</button>
        <a th:href="@{/tasks}">취소</a>
    </form>
</main></body>
</html>

```

### src/main/resources/templates/list.html

```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>학습 과제 관리</title>
    <link rel="stylesheet" th:href="@{/style.css}">
</head>
<body><main>
    <p>JAVA 응용 · Week15</p>
    <h1>학습 과제 관리</h1>
    <a class="button" th:href="@{/tasks/new}">새 과제 등록</a>
    <form method="get" th:action="@{/tasks}">
        <label>제목 검색 <input name="q" th:value="${q}" maxlength="100"></label>
        <button>검색</button>
        <a th:href="@{/tasks}">전체 보기</a>
    </form>
    <p th:text="|총 ${count}건 · ${page + 1}페이지|">총 0건</p>
    <table>
        <thead><tr><th>번호</th><th>과제명</th><th>상태</th><th>관리</th></tr></thead>
        <tbody>
            <tr th:each="task : ${tasks}">
                <td th:text="${task.id}"></td>
                <td th:text="${task.title}"></td>
                <td th:text="${task.status}"></td>
                <td>
                    <a th:href="@{/tasks/{id}/edit(id=${task.id})}">수정</a>
                    <form method="post" th:action="@{/tasks/{id}/delete(id=${task.id})}"
                          onsubmit="return confirm('이 과제를 삭제할까요?')">
                        <button>삭제</button>
                    </form>
                </td>
            </tr>
            <tr th:if="${#lists.isEmpty(tasks)}"><td colspan="4">검색 결과가 없습니다.</td></tr>
        </tbody>
    </table>
    <nav>
        <a th:if="${page > 0}" th:href="@{/tasks(q=${q},page=${page - 1})}">이전</a>
        <a th:if="${(page + 1) * 5 < count}" th:href="@{/tasks(q=${q},page=${page + 1})}">다음</a>
    </nav>
</main></body>
</html>

```

### src/main/resources/static/style.css

```css
body { font-family: "Malgun Gothic", sans-serif; background: #edf2f7; color: #18324a; margin: 0; padding: 32px 16px; }
main { max-width: 900px; background: white; margin: auto; padding: 32px; border-radius: 16px; }
table { width: 100%; border-collapse: collapse; margin: 24px 0; }
th, td { border-bottom: 1px solid #ccd7e2; text-align: left; padding: 12px; }
form { margin: 16px 0; } td form { display: inline; }
label { display: block; margin: 16px 0; }
input, select, button, .button { padding: 10px; border-radius: 5px; }
button, .button { background: #195d9d; color: white; border: 0; }
a { margin-right: 12px; } .error { color: #b32032; }

```

## 6단계 — Eclipse에서 실행

1. Application.java 우클릭 → Run As → Java Application. 첫 실행이 DB_PASSWORD 오류로 실패하면 다음 설정을 한다.
2. Run → Run Configurations → Java Application → Application 선택 → Environment → New에서 DB_USER=javaapp15와 DB_PASSWORD=자신의 DB 암호를 추가한다. 따옴표는 넣지 않는다. JRE 탭은 JDK 21을 선택한다.
3. Apply → Run. Console에서 Started Application 및 8080 기동을 확인한다.
4. 브라우저에서 http://localhost:8080/tasks 를 연다. 첫 화면의 과제 5행과 '다음'을 확인한다.
5. Eclipse 실행 중 Stop으로 중지하고 다시 실행한다. 이전 데이터가 유지되는지 확인한다.

선택: Maven이 PATH에 설정되어 있다면 Windows PowerShell에서 다음 명령도 가능하다. 암호는 예시 대신 자신의 값을 입력한다.

```powershell
cd Week15\taskboard
$env:DB_USER = "javaapp15"
$env:DB_PASSWORD = "자신의 DB 암호"
mvn clean test package
java -jar target/taskboard-1.0.0.jar
```

## 7단계 — 정상 흐름 검증

1. '새 과제 등록' → 제목 '최종 보고서 작성', 상태 TODO → 저장. 목록 맨 위에 1행 추가되어야 한다.
2. 해당 행 수정 → 상태 DONE → 저장. 같은 id의 상태만 바뀌어야 한다.
3. 제목 검색에 '최종' 입력 → 검색. 해당 과제 1건만 보인다.
4. 전체 보기 → 다음 → 이전. 검색 중 페이지를 바꾸면 q 조건이 주소에 유지되어야 한다.
5. 삭제 → 확인. 행이 사라지고 Workbench 조회에서도 없어야 한다.
6. 저장 후 새로고침. 직전 등록 요청이 반복 전송되지 않아야 한다.

## 8단계 — 실패 및 경계값 검증

data/test-cases.csv의 실제결과·판정을 채운다. 브라우저 maxlength는 먼저 차단하므로 101자 서버 검증은 포함된 자동 테스트로도 확인한다. 브라우저 개발자 도구로 제약을 지워도 서버에서 거부해야 한다.

| 입력 또는 상황 | 예상 결과 |
| --- | --- |
| 제목 공백 3개 | 오류 안내, DB 행 수 유지 |
| 제목 100자 / 101자 | 100자 저장 / 101자 거부 |
| status=OTHER 변조 | 상태 검증 오류, DB 변경 없음 |
| 제목 &lt;script&gt;alert(1)&lt;/script&gt; | 실행 없이 글자로 표시 |
| 검색어 ' OR 1=1 -- | SQL 오류 없이 일반 문자열 검색 |
| /tasks?page=-1 또는 page=abc | 400 |
| /tasks/999999/edit, 실제로 없는 id | 404 |
| GET /tasks/1/delete | 405, 데이터 유지 |
| CSRF 토큰 없는 POST | 403, 데이터 유지 |
| MySQL 중지 후 목록 새로고침 | 연결 대기 후 503 안내, 서버에는 로그 |
| MySQL 재시작 후 다시 시도 | 정상 복구 및 데이터 유지 |

## 9단계 — 자동 검증과 자기 프로젝트 적용

Eclipse에서 프로젝트 우클릭 → Run As → Maven test를 실행한다. 테스트 코드는 src/test에 포함되어 있다. H2 MySQL 호환 모드로 CRUD·검증·CSRF·이스케이프·검색·페이지를 확인한다. 이 결과가 Windows/MySQL 8.0.46 실제 실행 검증을 대체하지는 않는다.

기존 JSP 프로젝트는 동일한 검증표로 다음을 보완한다.

1. 인코딩 Filter와 contextPath를 확인한다.
2. Servlet의 입력 검사를 DAO 호출 전으로 이동한다.
3. PreparedStatement로 ?를 바인딩한다.
4. 수정 대상 조회 실패를 처리하고 삭제는 POST로 제한한다.
5. JSP의 사용자 출력은 c:out으로 바꾼다.
6. 성공 시 redirect, 실패 시 오류·입력값을 담아 forward한다.
7. 세션 인증과 CSRF 검증이 기존에 있다면 제거하지 않는다.

## 10단계 — 최종 완성과 제출

1. data/defects.csv의 결함 3개 중 실제 수정한 항목을 완료 처리한다.
2. 수정 전·후 화면, DB 확인 결과, 테스트 결과를 evidence 폴더에 넣는다.
3. 아래 README 서식을 자기 프로젝트 기준으로 채운다.
4. 다른 폴더에 소스를 다시 풀어 SQL → 환경변수 → 실행 → CRUD 순서로 재현한다.
5. 소스·SQL·README·검증 기록을 ZIP으로 묶는다. target과 암호는 제외한다.

```text
프로젝트명 / 학번 / 성명:
JDK / Eclipse / DB / 서버 버전:
SQL 실행 순서:
설정할 환경변수 이름(비밀번호 값 제외):
실행 방법 및 접속 URL:
수정한 미완성 기능과 원인:
검증 입력 / 예상 결과 / 실제 결과:
남아 있는 제한사항:
```

## 자주 만나는 오류

| 증상 | 확인 및 해결 |
| --- | --- |
| DB_PASSWORD 미해결 또는 Access denied | Run Configurations 환경변수·사용자·실제 비밀번호 확인 |
| Unknown database / tasks 없음 | Workbench에서 01_schema.sql을 먼저 실행 |
| Communications link failure | MySQL 서비스 시작 및 3306 확인 |
| Port 8080 in use | 기존 Tomcat 중지 또는 server.port=8081 |
| Unsupported class file | Eclipse 실행 JRE와 Compiler를 21로 맞춤 |
| 템플릿 없음 | src/main/resources/templates 경로와 파일명 확인 |
| POST 403 | 목록/폼 GET을 다시 연 후 저장; CSRF를 끄지 않음 |
| JSP의 javax.servlet 오류 | Tomcat 10.1에서는 jakarta.servlet 기준과 의존성을 일치시킴 |

보충 과제: 자신의 프로젝트에서 상태별 필터 또는 삭제 취소 기능 중 하나를 골라 설계하고 정상·실패 검증을 추가한다. 기본 완성 후에만 진행한다.
