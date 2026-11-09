## 개발환경 및 실행 기준

| 항목 | 수업 기준 |
| --- | --- |
| 운영체제 | Windows 10, UTF-8 |
| JDK | Java 21 (JRE만 설치하지 않음) |
| IDE | Eclipse IDE 2025-09, Java 개발 도구 및 Maven(m2e) |
| DB 서버 | MySQL Community Server 8.0.46, 포트 3306 |
| DB 도구 | MySQL Workbench 8 |
| Spring | Spring Boot 3.5.7 고정, Spring MVC |
| 화면·DB 접근 | Thymeleaf, Spring JDBC JdbcTemplate |
| 실행 형태 | Maven JAR, 내장 Tomcat, http://localhost:8080/members |

별도 외부 Tomcat이나 JSP 설정은 필요하지 않습니다. Java 21과 호환되는 Spring Boot 3.5 계열을 사용하며, 수업 재현성을 위해 pom.xml의 3.5.7을 유지합니다. MySQL Connector/J는 Maven이 내려받습니다. 기존 Dynamic Web Project에 파일을 섞지 말고 별도 Maven 프로젝트로 가져옵니다.

이 프로젝트는 로컬 회원 주소록 CRUD 학습용입니다. 비밀번호·인증 기능은 없으며 가상 회원 데이터만 사용합니다. 서버는 127.0.0.1에 바인딩됩니다. 실제 서비스 확장 과제로 인증·권한·CSRF 방어·TLS를 검토합니다.

## 완성 목표: 동원 회원관리 웹 주소록

목록에서 회원을 등록하고, 이름·이메일·전화를 수정하며, 확인 후 삭제할 수 있는 로컬 웹 프로그램을 만듭니다. 먼저 아래 화면 구성을 확인한 뒤 DB → Java 계층 → HTML 순으로 작성합니다.

| 화면 | 상단 | 본문 | 동작 |
| --- | --- | --- | --- |
| 회원 목록 | 동원 회원관리 / 회원 등록 버튼 | 번호·이름·이메일·전화·관리 표 | 수정 링크·삭제 확인창 |
| 회원 입력 | 회원 입력 | 이름·이메일·전화 입력창, 필드별 오류 | 저장·목록으로 |

예시 초기 행: 홍길동 / hong@example.com / 010-1111-2222, 김자바 / java@example.com / 010-3333-4444. id는 자동 생성됩니다. 실행 시 CSS가 적용되어 흰색 카드와 파란 버튼으로 표시됩니다.

## 튜토리얼

### 1단계. 준비 및 프로젝트 가져오기

1. ZIP을 내려받아 `C:\javaapp\Week14`에 압축을 풉니다. 한글 인코딩은 UTF-8을 사용합니다.
2. 명령 프롬프트에서 `java -version`과 `javac -version`을 실행하여 모두 21인지 확인합니다.
3. Eclipse → Window → Preferences → Java → Installed JREs → Add → Standard VM에서 JDK 21 폴더를 등록하고 기본으로 선택합니다. Java → Compiler의 compliance level도 21로 맞춥니다.
4. File → Import → Maven → Existing Maven Projects → `Week14/member-crud` 선택 → pom.xml 체크 → Finish. 프로젝트 우클릭 → Maven → Update Project를 실행합니다. 최초 의존성 다운로드에는 인터넷이 필요합니다.
5. 프로젝트 Properties → Resource → Text file encoding을 UTF-8, Java Build Path의 JRE System Library를 JavaSE-21로 확인합니다.

성공 기준: 빨간 오류 표시 없이 `MemberApplication.java`와 `src/main/resources/templates`가 보입니다. Spring Tools 설치는 필수가 아닙니다.

### 2단계. MySQL 스키마와 예제 데이터 만들기

Workbench에서 로컬 관리자 연결을 열고 `database/01_setup.sql`을 실행한 다음 `02_seed.sql`을 실행합니다. SCHEMAS를 새로고침하여 javaapp14 → members를 확인합니다. 아래 SQL은 기존 테이블을 삭제하지 않습니다. 01_setup.sql의 암호는 수업용 예시이며 개인 로컬 환경에서 변경할 수 있습니다. 기존 javaapp14 계정이 있으면 CREATE USER IF NOT EXISTS가 암호를 바꾸지 않으므로 기존 암호를 사용합니다.

관리자 연결과 별개로 Workbench 새 연결을 만들고 Hostname=localhost, Port=3306, Username=javaapp14로 설정하여 Test Connection합니다. 이 계정은 데이터 조작 권한만 가지며 테이블 생성은 관리자가 수행합니다.

### 3단계. 실행 설정

Eclipse → Run → Run Configurations → Java Application → New. Project=member-crud, Main class=`edu.javaapp.member.MemberApplication`. JRE 탭에서 Java 21을 지정합니다. Environment 탭 → Add에서 `DB_PASSWORD`를 추가하고 SQL에서 설정한 암호를 입력합니다. 기본 계정은 javaapp14입니다. 계정이나 연결 주소가 다를 때만 `DB_USER`, `DB_URL`을 추가합니다. 암호를 소스 파일에 작성하지 않습니다.

Run → Console에 Started MemberApplication과 8080 포트를 확인하고 브라우저에서 `http://localhost:8080/members`를 엽니다. 기본 `/` 주소는 구현하지 않아 404가 정상입니다.

Maven CLI가 별도로 설치되어 있다면 Windows 명령 프롬프트에서 다음과 같이 실행할 수도 있습니다.

```bat
cd C:\javaapp\Week14\member-crud
set "DB_PASSWORD=Java14!LocalOnly"
mvn spring-boot:run
```

PowerShell에서는 `$env:DB_PASSWORD = 'Java14!LocalOnly'`를 사용합니다. Eclipse 내장 Maven으로 검증할 때는 Run As → Maven build → Goals에 `test` 또는 `package`를 입력합니다. 패키징 후 `java -jar target/member-crud-1.0.0.jar`도 가능합니다. 실행 환경에 DB_PASSWORD가 있어야 합니다.

### 4단계. 프로젝트 파일 작성 순서

완성 프로젝트를 가져왔다면 아래 코드를 파일별로 비교합니다. 직접 작성한다면 순서대로 같은 경로에 파일을 만듭니다. Java 파일의 package는 모두 edu.javaapp.member입니다. templates 폴더에 Java 파일을 넣지 않습니다.

#### 파일 1. pom.xml

의존성을 저장한 뒤 Maven Update Project로 라이브러리를 받습니다.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.7</version>
        <relativePath/>
    </parent>
    <groupId>edu.javaapp</groupId>
    <artifactId>member-crud</artifactId>
    <version>1.0.0</version>
    <properties><java.version>21</java.version></properties>
    <dependencies>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-web</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-thymeleaf</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-jdbc</artifactId></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-validation</artifactId></dependency>
        <dependency><groupId>com.mysql</groupId><artifactId>mysql-connector-j</artifactId><scope>runtime</scope></dependency>
        <dependency><groupId>org.springframework.boot</groupId><artifactId>spring-boot-starter-test</artifactId><scope>test</scope></dependency>
        <dependency><groupId>com.h2database</groupId><artifactId>h2</artifactId><scope>test</scope></dependency>
    </dependencies>
    <build><plugins><plugin><groupId>org.springframework.boot</groupId><artifactId>spring-boot-maven-plugin</artifactId></plugin></plugins></build>
</project>
```

#### 파일 2. src/main/resources/application.properties

자동 테이블 생성을 끄고 DB_PASSWORD를 환경변수에서 읽습니다.

```properties
spring.application.name=week14-member-crud
server.address=127.0.0.1
server.port=8080
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/javaapp14?connectionTimeZone=Asia/Seoul&characterEncoding=UTF-8&sslMode=DISABLED&allowPublicKeyRetrieval=true}
spring.datasource.username=${DB_USER:javaapp14}
spring.datasource.password=${DB_PASSWORD}
spring.sql.init.mode=never
spring.thymeleaf.cache=false
server.error.include-message=never
```

#### 파일 3. src/main/java/edu/javaapp/member/MemberApplication.java

Spring Boot 실행 진입점입니다. 모든 클래스는 이 패키지 안에 둡니다.

```java
package edu.javaapp.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
```

#### 파일 4. src/main/java/edu/javaapp/member/Member.java

DB 조회 결과를 담는 불변 record입니다.

```java
package edu.javaapp.member;

public record Member(Long id, String name, String email, String phone) {
}
```

#### 파일 5. src/main/java/edu/javaapp/member/MemberForm.java

폼 바인딩과 검증을 담당하는 기본 생성자·getter/setter가 있는 클래스입니다.

```java
package edu.javaapp.member;

import jakarta.validation.constraints.*;

public class MemberForm {
    @NotBlank(message = "이름을 입력하세요.")
    @Size(max = 50, message = "이름은 50자 이내입니다.")
    private String name;

    @NotBlank(message = "이메일을 입력하세요.")
    @Email(message = "이메일 형식을 확인하세요.")
    @Size(max = 120, message = "이메일은 120자 이내입니다.")
    private String email;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Pattern(regexp = "[0-9+() -]{7,20}", message = "전화번호는 숫자와 기호로 7~20자입니다.")
    private String phone;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
```

#### 파일 6. src/main/java/edu/javaapp/member/MemberDao.java

목록·개별조회·삽입·수정·삭제 SQL을 확인합니다. 물음표 순서와 인수 순서가 같습니다.

```java
package edu.javaapp.member;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbc;
    private final RowMapper<Member> mapper = (rs, rowNum) -> new Member(
            rs.getLong("id"), rs.getString("name"),
            rs.getString("email"), rs.getString("phone"));

    public MemberDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Member> findAll() {
        return jdbc.query("SELECT id, name, email, phone FROM members ORDER BY id DESC", mapper);
    }

    public Optional<Member> findById(long id) {
        return jdbc.query("SELECT id, name, email, phone FROM members WHERE id = ?", mapper, id)
                .stream().findFirst();
    }

    public int insert(MemberForm form) {
        return jdbc.update("INSERT INTO members(name, email, phone) VALUES (?, ?, ?)",
                form.getName(), form.getEmail(), form.getPhone());
    }

    public int update(long id, MemberForm form) {
        return jdbc.update("UPDATE members SET name = ?, email = ?, phone = ? WHERE id = ?",
                form.getName(), form.getEmail(), form.getPhone(), id);
    }

    public int delete(long id) {
        return jdbc.update("DELETE FROM members WHERE id = ?", id);
    }
}
```

#### 파일 7. src/main/java/edu/javaapp/member/MemberService.java

DB 처리를 업무 단위로 묶고 없는 회원을 확인합니다.

```java
package edu.javaapp.member;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberDao dao;

    public MemberService(MemberDao dao) {
        this.dao = dao;
    }

    public List<Member> list() {
        return dao.findAll();
    }

    public Member get(long id) {
        return dao.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void create(MemberForm form) {
        normalize(form);
        dao.insert(form);
    }

    @Transactional
    public void update(long id, MemberForm form) {
        get(id);
        normalize(form);
        dao.update(id, form);
    }

    @Transactional
    public void delete(long id) {
        if (dao.delete(id) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다.");
        }
    }

    private void normalize(MemberForm form) {
        form.setName(form.getName().strip());
        form.setEmail(form.getEmail().strip().toLowerCase(java.util.Locale.ROOT));
        form.setPhone(form.getPhone().strip());
    }
}
```

#### 파일 8. src/main/java/edu/javaapp/member/MemberController.java

BindingResult 위치, 오류 시 form 반환, 성공 시 redirect를 비교합니다.

```java
package edu.javaapp.member;

import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("members", service.list());
        return "members/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        model.addAttribute("action", "/members");
        return "members/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute MemberForm memberForm,
            BindingResult result, Model model, RedirectAttributes redirect) {
        if (!result.hasErrors()) {
            try {
                service.create(memberForm);
            } catch (DuplicateKeyException ex) {
                result.rejectValue("email", "duplicate", "이미 등록된 이메일입니다.");
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("action", "/members");
            return "members/form";
        }
        redirect.addFlashAttribute("message", "회원이 등록되었습니다.");
        return "redirect:/members";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        Member member = service.get(id);
        MemberForm form = new MemberForm();
        form.setName(member.name());
        form.setEmail(member.email());
        form.setPhone(member.phone());
        model.addAttribute("memberForm", form);
        model.addAttribute("action", "/members/" + id);
        return "members/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable long id,
            @Valid @ModelAttribute MemberForm memberForm,
            BindingResult result, Model model, RedirectAttributes redirect) {
        service.get(id);
        if (!result.hasErrors()) {
            try {
                service.update(id, memberForm);
            } catch (DuplicateKeyException ex) {
                result.rejectValue("email", "duplicate", "이미 등록된 이메일입니다.");
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("action", "/members/" + id);
            return "members/form";
        }
        redirect.addFlashAttribute("message", "회원이 수정되었습니다.");
        return "redirect:/members";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirect) {
        service.delete(id);
        redirect.addFlashAttribute("message", "회원이 삭제되었습니다.");
        return "redirect:/members";
    }
}
```

#### 파일 9. src/main/resources/templates/members/list.html

members 모델을 반복하고 id로 수정/삭제 URL을 만듭니다.

```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>동원 회원관리</title>
    <link rel="stylesheet" th:href="@{/style.css}">
</head>
<body>
<main>
    <h1>동원 회원관리</h1>
    <p>Spring과 MySQL로 만드는 회원 주소록</p>
    <p class="notice" th:if="${message}" th:text="${message}"></p>
    <a class="button" th:href="@{/members/new}">회원 등록</a>
    <table>
        <thead><tr><th>번호</th><th>이름</th><th>이메일</th><th>전화번호</th><th>관리</th></tr></thead>
        <tbody>
        <tr th:if="${#lists.isEmpty(members)}"><td colspan="5">등록된 회원이 없습니다.</td></tr>
        <tr th:each="member : ${members}">
            <td th:text="${member.id}"></td>
            <td th:text="${member.name}"></td>
            <td th:text="${member.email}"></td>
            <td th:text="${member.phone}"></td>
            <td>
                <a th:href="@{/members/{id}/edit(id=${member.id})}">수정</a>
                <form class="inline" method="post"
                      th:action="@{/members/{id}/delete(id=${member.id})}"
                      onsubmit="return confirm('이 회원을 삭제할까요?');">
                    <button type="submit">삭제</button>
                </form>
            </td>
        </tr>
        </tbody>
    </table>
</main>
</body>
</html>
```

#### 파일 10. src/main/resources/templates/members/form.html

등록·수정에 같은 폼을 쓰되 action URL이 다릅니다.

```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>회원 입력</title>
    <link rel="stylesheet" th:href="@{/style.css}">
</head>
<body>
<main>
    <h1>회원 입력</h1>
    <form method="post" th:action="${action}" th:object="${memberForm}">
        <label for="name">이름</label>
        <input id="name" th:field="*{name}" maxlength="50" required>
        <p class="error" th:errors="*{name}"></p>
        <label for="email">이메일</label>
        <input id="email" type="email" th:field="*{email}" maxlength="120" required>
        <p class="error" th:errors="*{email}"></p>
        <label for="phone">전화번호</label>
        <input id="phone" th:field="*{phone}" maxlength="20" required placeholder="010-1234-5678">
        <p class="error" th:errors="*{phone}"></p>
        <button type="submit">저장</button>
        <a th:href="@{/members}">목록으로</a>
    </form>
</main>
</body>
</html>
```

#### 파일 11. src/main/resources/static/style.css

외부 CDN 없이 로컬 CSS를 사용합니다.

```css
body {
    margin: 0;
    background: #f3f6fb;
    color: #17263e;
    font: 16px/1.6 "Malgun Gothic", sans-serif;
}
main {
    max-width: 1000px;
    margin: 48px auto;
    padding: 32px;
    background: white;
    border-radius: 16px;
}
table { width: 100%; border-collapse: collapse; margin-top: 24px; }
th, td { padding: 14px; border-bottom: 1px solid #d9e0eb; text-align: left; }
th { background: #edf2fa; }
label { display: block; margin-top: 16px; }
input { display: block; width: 90%; padding: 12px; margin-bottom: 8px; }
button, .button { background: #2155c9; color: white; padding: 8px 16px; border: 0; border-radius: 6px; cursor: pointer; }
a { color: #2155c9; }
a.button { display: inline-block; text-decoration: none; }
.inline { display: inline; margin-left: 12px; }
.error { color: #b42318; }
.notice { background: #e6f5ec; padding: 12px; }
```

### 5단계. DB SQL 전체 코드

#### database/01_setup.sql

```sql
-- Workbench 관리자 연결에서 한 번 실행. 기존 데이터 삭제 없음.
CREATE DATABASE IF NOT EXISTS javaapp14 CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
CREATE USER IF NOT EXISTS 'javaapp14'@'localhost' IDENTIFIED BY 'Java14!LocalOnly';
GRANT SELECT, INSERT, UPDATE, DELETE ON javaapp14.* TO 'javaapp14'@'localhost';
USE javaapp14;
CREATE TABLE IF NOT EXISTS members (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
-- 이미 같은 계정이 있다면 암호는 바뀌지 않는다. 기존 암호를 DB_PASSWORD에 지정한다.
```

#### database/02_seed.sql

```sql
USE javaapp14;
INSERT INTO members(name, email, phone)
SELECT '홍길동', 'hong@example.com', '010-1111-2222'
WHERE NOT EXISTS (SELECT 1 FROM members WHERE email='hong@example.com');
INSERT INTO members(name, email, phone)
SELECT '김자바', 'java@example.com', '010-3333-4444'
WHERE NOT EXISTS (SELECT 1 FROM members WHERE email='java@example.com');
SELECT id, name, email, phone, created_at FROM members ORDER BY id DESC;
```

### 6단계. 요청 흐름 추적

1. 회원 등록 버튼 → GET /members/new → newForm → MemberForm과 action을 모델에 추가 → form.html.
2. 저장 → POST /members → @Valid 검증 → MemberService.create → MemberDao.insert → MySQL INSERT.
3. 중복 이메일이면 UNIQUE 위반 → DuplicateKeyException → 필드 오류 표시. 정상일 때는 redirect:/members.
4. GET /members → service.list → dao.findAll → SQL 행을 Member로 매핑 → 모델 members → list.html.
5. 수정은 id를 포함한 POST /members/{id}, 삭제는 POST /members/{id}/delete입니다. DAO WHERE 조건에 id가 바인딩되는지 확인합니다.

### 7단계. 실행 및 검증

Eclipse에서 MemberApplication을 실행한 뒤 아래 표를 순서대로 확인합니다.

### CRUD 확인표

각 단계에서 브라우저와 Workbench를 함께 확인합니다. 번호는 자동 증가하므로 특정 id를 가정하지 않습니다.

| 순서 | 입력·행동 | 예상 화면·DB 결과 |
| --- | --- | --- |
| 1 | 초기 목록 열기 | 홍길동·김자바 2명 표시(새 DB 기준) |
| 2 | 등록 → 이름 박스프링, 이메일 park@example.com, 전화 010-5555-6666 → 저장 | 목록에 추가, 새 DB 기준 3행 |
| 3 | 박스프링 수정 → 전화 010-7777-8888 → 저장 | 같은 id의 전화번호만 변경 |
| 4 | park@example.com으로 다른 이름 등록 | 이미 등록된 이메일 안내, 행 수 증가 없음 |
| 5 | 이름을 공백으로 입력 | 서버 검증 메시지, DB 변화 없음 |
| 6 | 박스프링 삭제 → 확인 | 목록에서 사라짐, 새 DB 기준 2행 |
| 7 | 앱 종료 후 재실행 | DB에 남은 회원이 다시 표시됨 |
| 8 | /members/999999/edit 접근(없는 id 확인 후) | HTTP 404 |

Workbench 확인 SQL:

```sql
SELECT id, name, email, phone, created_at
FROM javaapp14.members
ORDER BY id DESC;
```

GET 요청은 조회와 입력 화면을 표시하고, POST 요청은 데이터를 변경합니다. 삭제 버튼은 POST form이며 확인창에서 취소하면 요청하지 않습니다. 저장 성공 후 redirect:/members로 이동하므로 목록 새로고침이 같은 등록 POST를 반복하지 않습니다(PRG).

### 오류 해결

| 증상 | 확인 및 해결 |
| --- | --- |
| Access denied | DB_USER/DB_PASSWORD와 Workbench 연결 계정 비교. 기존 계정 암호 여부 확인 |
| Unknown database / table not found | 관리자 연결로 01_setup.sql 실행, DB_URL의 javaapp14 확인 |
| Communications link failure | Windows 서비스의 MySQL80 실행 여부, 포트 3306 확인 |
| Port 8080 already in use | 기존 Java 실행을 종료하거나 Run Configuration에 SERVER_PORT=8081 추가 후 URL 변경 |
| Could not resolve placeholder DB_PASSWORD | Run Configurations의 Environment 탭에 DB_PASSWORD 추가 |
| 템플릿을 찾지 못함 | templates/members/list.html, form.html의 위치·대소문자 확인 |
| 404 | /members 주소, MemberApplication 하위 패키지 배치 확인 |
| Java 컴파일 버전 오류 | Eclipse 실행 JRE와 프로젝트 JDK를 모두 21로 지정, Maven Update Project |
| SSL/public key 오류 | 로컬 실습 URL 전체를 확인. 원격 운영 환경에서는 TLS와 서버 인증을 별도 구성 |

### 8단계. 테스트와 관찰 기록

Eclipse Maven build Goals=`test`로 실행합니다. 제공된 MemberFlowTest는 H2 MySQL 호환 모드로 HTML 렌더링·CRUD·검증·중복·404·GET 삭제 거부·출력 이스케이프를 검사합니다. 테스트용 schema.sql은 src/test/resources에만 있으며 운영 MySQL에서는 자동 실행하지 않습니다. H2 결과는 실제 MySQL 8.0.46 검증을 대신하지 않으므로 Workbench 확인과 재실행 점검을 수행합니다.

| 시나리오 | 예상 | 실제(학생 작성) | 통과 여부 |
| --- | --- | --- | --- |
| 등록 | 회원 1명 증가 | | |
| 수정 | 동일 id의 값 변경 | | |
| 삭제 | 대상 1명만 제거 | | |
| 중복 이메일 | 안내·DB 변화 없음 | | |
| 공백 이름 | 필드 오류 | | |
| 재실행 | 회원 유지 | | |

## 확장 과제와 제출


### 기본 실습

별도 워크북에서 1~8단계를 순서대로 진행합니다. 제공 코드를 파일 단위로 입력하거나 완성 프로젝트를 가져온 뒤 등록 한 건이 어느 메서드와 SQL을 통과하는지 추적합니다. CRUD 확인표의 8개 시나리오를 수행하고 실제 결과를 기록합니다.

### 확장 과제: 이름 검색

목록 상단에 검색어 입력창을 만들고 `GET /members?keyword=홍` 요청으로 이름에 ‘홍’이 포함된 회원만 표시하도록 확장합니다. Controller에서 keyword를 받고 Service를 거쳐 DAO의 `WHERE name LIKE ?`로 전달합니다. 바인딩 값은 `"%" + keyword + "%"`로 만듭니다. 빈 검색어는 전체 목록입니다. 기존 CRUD는 계속 동작해야 합니다. `%`, `_`를 와일드카드로 취급할지 문자 그대로 검색할지도 설명합니다.

제출물: 수정 소스 ZIP(target·암호 제외), CRUD 및 검색 화면 5장 이상, Workbench 결과, 요청 흐름 설명 1쪽, 확인표의 예상/실제/통과 여부. 제출 기한과 제출 경로는 교수자 안내를 따릅니다.

| 평가 항목 | 배점 | 증거 |
| --- | --- | --- |
| MySQL 연동과 영속성 | 20 | SQL 조회·재실행 |
| CRUD 네 기능 | 40 | 각 기능 10점, 화면·DB 일치 |
| 계층 분리 | 15 | Controller에 SQL 없음, 생성자 주입 |
| 입력·중복·없는 id 처리 | 15 | 오류 화면과 DB 불변 |
| 이름 검색 | 10 | 부분·빈 검색과 바인딩 |

## 스스로 설명하기

- form.html에서 th:object와 th:field가 연결되는 과정을 설명하세요.
- Controller의 String 반환값이 뷰 이름일 때와 redirect일 때의 차이를 설명하세요.
- 이 프로그램에서 비밀번호를 저장하지 않는 이유와 로그인 기능 추가 시 고려할 점을 적으세요.
