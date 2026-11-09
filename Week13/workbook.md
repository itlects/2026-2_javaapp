## 개발환경 및 시작 전 확인

| 구분 | 이번 주 기준 |
| --- | --- |
| 운영체제 | Windows 10 64bit |
| JDK | Java 21, Eclipse 실행 JDK와 프로젝트 JDK 모두 21 |
| IDE | Eclipse IDE for Enterprise Java and Web Developers 2025-09 |
| DB 서버 | MySQL Community Server 8.0.46, localhost:3306 |
| DB 도구 | MySQL Workbench 8 |
| 웹 프레임워크 | Spring Boot 3.5.7로 실습 버전 고정 |
| 빌드·뷰 | Maven, Thymeleaf, Spring JDBC |
| 실행 | JAR + 내장 Tomcat, http://localhost:8080 |

Eclipse의 기본 Maven 통합(m2e)을 사용합니다. Spring Tools 추가 설치 없이 Import → Existing Maven Projects로 실행할 수 있습니다. 외부 Tomcat의 Servers에 배포하지 않습니다. 기존 JSP 예제와 달리 이번 View는 templates 아래의 HTML입니다. MySQL 서버 버전과 Maven이 관리하는 JDBC 드라이버 버전은 서로 다른 버전입니다.

명령 프롬프트에서 `java -version`, `javac -version`이 21인지 확인합니다. Eclipse → Window → Preferences → Java → Installed JREs에서 JDK 21을 추가하고 기본값으로 선택합니다. 프로젝트 Properties → Java Build Path → Libraries의 JRE System Library와 Java Compiler의 compliance level도 21로 맞춥니다. Workspace와 프로젝트 Text file encoding은 UTF-8로 설정합니다.

Spring Boot 3.5 계열의 Java 21 호환성과 MVC·Thymeleaf 사용법은 아래 공식 문서를 참고합니다. 3.5.7은 본 실습의 재현을 위한 고정 버전이며 최신 버전이라는 의미는 아닙니다.

- [Spring Boot 시스템 요구사항](https://docs.spring.io/spring-boot/3.5/system-requirements.html)
- [3.5.7 릴리스 안내](https://spring.io/blog/2025/10/23/spring-boot-3-5-7-available-now/)
- [Spring MVC와 Thymeleaf 공식 예제](https://spring.io/guides/gs/serving-web-content)
- [Spring JDBC 공식 문서](https://docs.spring.io/spring-framework/reference/data-access/jdbc/core.html)

<table-of-contents />

## 완성 목표: 동아리 회원 조회 웹사이트

운영자가 회원 이름으로 검색하고 상세 정보를 확인하는 조회 사이트입니다. 신규 DB의 샘플 5명을 기준으로 실습합니다. 본문 과제로 3명을 추가했다면 전체·검색 건수가 달라지므로 샘플 기준값과 구분해 기록하세요.

### 완성화면 설계 예시

아래는 실제 실행 캡처가 아닌 화면 구성 예시입니다. 스타일은 제공 CSS를 사용합니다.

| 회원목록 · 이름 [김] · [검색] · [전체보기] | | | |
| --- | --- | --- | --- |
| 번호 | 이름 | 이메일 | 상세 |
| 1 | 김민수 | minsu@example.com | 상세보기 |
| 4 | 김하늘 | haneul@example.com | 상세보기 |

조회 결과 2명. 상세보기 클릭 시 번호·이름·이메일과 목록으로 링크가 보입니다. 아래 튜토리얼은 본문을 다시 열지 않아도 완성할 수 있도록 모든 파일을 포함합니다.

## 1. 준비·가져오기·실행
### 단계 1. 프로젝트 준비

1. ZIP의 압축 해제 위치를 `C:\javaapp`로 지정합니다. ZIP의 최상위 `Week13` 폴더가 생성되며 `C:\javaapp\Week13\spring-members\pom.xml`이 있는지 확인합니다. 파일 탐색기의 확장명 표시를 켜서 `.java.txt`, `.html.txt`가 되지 않도록 합니다.
2. Eclipse → File → Import → Maven → Existing Maven Projects → Root Directory에 `spring-members`를 선택합니다. pom.xml 체크 후 Finish를 누릅니다. Maven 의존성 다운로드가 끝날 때까지 기다립니다.
3. 빨간 오류가 남으면 프로젝트 우클릭 → Maven → Update Project → Force Update를 선택합니다. 인터넷 연결이 필요합니다. 실행 환경은 Java 21입니다.
4. 직접 새로 만드는 경우 https://start.spring.io 에서 Maven / Java / Jar / Java 21, Group `com.example`, Artifact `spring-members`, Package `com.example.members`, Dependencies `Spring Web`, `Thymeleaf`, `JDBC API`, `MySQL Driver`를 선택합니다. 3.5.7이 선택 목록에 없으면 제공한 pom.xml 전체로 교체하고 Maven Update를 실행합니다. 제공본은 Maven Wrapper를 포함하지 않으므로 `mvnw.cmd` 명령을 사용하지 않습니다.

### 단계 2. 데이터베이스와 계정

1. Workbench 8에서 로컬 MySQL 서버에 root로 연결합니다. 아래 `01_schema_seed.sql`을 열고 전체 실행합니다. 기존 다른 주차 DB와 분리된 `javaapp13`을 사용합니다.
2. `02_user.sql`의 `CHANGE_ME_LOCAL_PASSWORD`를 본인 실습 비밀번호로 바꾼 후 root 연결에서 실행합니다. 기존 계정 비밀번호를 바꾸려면 파일 안의 ALTER USER 안내를 따릅니다.
3. Workbench 새 연결을 만들어 Username `javaapp13`, Hostname `localhost`, Port `3306`, 위에서 정한 비밀번호로 Test Connection합니다. 이 계정은 조회 전용입니다.
4. `USE javaapp13; SELECT * FROM members ORDER BY id;` 결과가 5행인지 확인합니다. 제공 INSERT는 같은 id의 샘플 값을 다시 맞추며 임의로 추가한 행은 지우지 않습니다. 초기 샘플만 있을 때 이후 예상 건수와 일치합니다.

### 단계 3. 실행 환경변수

1. Eclipse에서 MemberApplication.java 우클릭 → Run As → Java Application을 선택해 실행 구성을 생성합니다. 비밀번호 미설정 오류가 나면 Stop합니다.
2. Run → Run Configurations → Java Application → MemberApplication → Environment → New에서 `DB_PASSWORD`에 본인 실습 비밀번호를 입력합니다. `DB_USER`는 기본 javaapp13이므로 다른 계정을 쓸 때만 추가합니다.
3. Apply → Run을 누릅니다. Console에서 `Started MemberApplication`을 확인합니다. 실행 버튼을 다시 누르기 전 이전 서버를 빨간 Stop 버튼으로 종료합니다.
4. 브라우저에서 `http://localhost:8080/hello?name=홍길동`, 이어서 `http://localhost:8080/members`를 엽니다. `/`는 매핑하지 않았으므로 회원목록 주소를 직접 사용합니다.

명령행 Maven을 별도로 설치한 경우의 대안입니다. Eclipse 사용자는 위 방법만으로 실행할 수 있습니다.

```bat
cd C:\javaapp\Week13\spring-members
set "DB_PASSWORD=본인실습비밀번호"
mvn test
mvn spring-boot:run
```

`mvn test`는 test 프로필의 H2 메모리 DB를 사용하므로 MySQL 서버와 비밀번호 없이 테스트합니다. 실제 구동은 MySQL을 사용합니다. Maven 테스트를 Eclipse에서 실행하려면 프로젝트 우클릭 → Run As → Maven build... → Goals에 `test`를 입력합니다.

### sql/01_schema_seed.sql

```sql
CREATE DATABASE IF NOT EXISTS javaapp13
    CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE javaapp13;
CREATE TABLE IF NOT EXISTS members (
    id BIGINT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE
);
INSERT INTO members (id, name, email) VALUES
    (1, '김민수', 'minsu@example.com'),
    (2, '이서연', 'seoyeon@example.com'),
    (3, '박지훈', 'jihun@example.com'),
    (4, '김하늘', 'haneul@example.com'),
    (5, '최유진', 'yujin@example.com')
ON DUPLICATE KEY UPDATE name = VALUES(name), email = VALUES(email);
SELECT id, name, email FROM members ORDER BY id;
```

### sql/02_user.sql

```sql
-- Workbench에서 root로 실행. 아래 자리표시자를 본인 실습 비밀번호로 바꾸세요.
CREATE USER IF NOT EXISTS 'javaapp13'@'localhost'
    IDENTIFIED BY 'CHANGE_ME_LOCAL_PASSWORD';
-- 계정이 이미 있으면 CREATE USER는 기존 비밀번호를 변경하지 않습니다.
-- 변경이 필요하면 아래 주석을 해제하고 본인 비밀번호로 수정하세요.
-- ALTER USER 'javaapp13'@'localhost' IDENTIFIED BY 'CHANGE_ME_LOCAL_PASSWORD';
GRANT SELECT ON javaapp13.* TO 'javaapp13'@'localhost';
```

## 2. 프로젝트 설정과 공통 파일 만들기

src/main/java 아래의 패키지를 com.example.members로 시작합니다. resources 아래 templates, static/css 폴더를 만듭니다. 제공 프로젝트에서는 이미 완성되어 있으므로 파일을 순서대로 읽고 같은 내용을 직접 입력해 볼 수 있습니다.

### pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.7</version>
        <relativePath/>
    </parent>
    <groupId>com.example</groupId>
    <artifactId>spring-members</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <properties>
        <java.version>21</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

### src/main/resources/application.properties

```properties
spring.application.name=spring-members
server.address=127.0.0.1
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/javaapp13?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul&characterEncoding=UTF-8
spring.datasource.username=${DB_USER:javaapp13}
spring.datasource.password=${DB_PASSWORD}
spring.sql.init.mode=never
spring.thymeleaf.cache=false
server.servlet.encoding.charset=UTF-8
server.servlet.encoding.enabled=true
server.servlet.encoding.force=true
server.error.include-message=never
```

### src/main/java/com/example/members/MemberApplication.java

```java
package com.example.members;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
```

### src/main/java/com/example/members/model/Member.java

```java
package com.example.members.model;

public class Member {
    private final Long id;
    private final String name;
    private final String email;

    public Member(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
```

## 3. 데이터 접근과 검색 규칙

Repository를 작성하고 Service를 이어서 작성합니다. 검색어가 없거나 공백뿐이면 전체 목록입니다. 다른 검색어는 양쪽 공백을 제거해 이름 포함 검색을 합니다. LOCATE는 문자열을 찾으므로 `%`와 `_`도 그대로 검색합니다. 상세 id가 없으면 Service가 404를 발생시킵니다.

### src/main/java/com/example/members/repository/MemberRepository.java

```java
package com.example.members.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.members.model.Member;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> mapper = (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"));

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        String sql = "SELECT id, name, email FROM members ORDER BY id";
        return jdbcTemplate.query(sql, mapper);
    }

    public List<Member> searchByName(String keyword) {
        // LOCATE는 %, _도 와일드카드가 아닌 일반 문자로 검색한다.
        String sql = "SELECT id, name, email FROM members "
                + "WHERE LOCATE(?, name) > 0 ORDER BY id";
        return jdbcTemplate.query(sql, mapper, keyword);
    }

    public Optional<Member> findById(Long id) {
        String sql = "SELECT id, name, email FROM members WHERE id = ?";
        return jdbcTemplate.query(sql, mapper, id).stream().findFirst();
    }
}
```

### src/main/java/com/example/members/service/MemberService.java

```java
package com.example.members.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.members.model.Member;
import com.example.members.repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public List<Member> list(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }
        return repository.searchByName(keyword.trim());
    }

    public Member detail(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
```

## 4. Controller와 URL Mapping

GET /members와 GET /members/{id}를 연결합니다. @RequestMapping("/members")와 각 메서드 매핑을 결합합니다. View의 파일 경로를 URL로 직접 입력하지 않습니다.

### src/main/java/com/example/members/controller/MemberController.java

```java
package com.example.members.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.members.model.Member;
import com.example.members.service.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping
    public String list(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            Model model) {
        List<Member> members = service.list(keyword);
        model.addAttribute("members", members);
        model.addAttribute("keyword", keyword.trim());
        model.addAttribute("count", members.size());
        return "members/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("member", service.detail(id));
        return "members/detail";
    }
}
```

### src/main/java/com/example/members/controller/HelloController.java

```java
package com.example.members.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(
            @RequestParam(name = "name", defaultValue = "학습자") String name,
            Model model) {
        model.addAttribute("name", name);
        return "hello";
    }
}
```

## 5. View와 CSS 전체 작성

templates/members/list.html, detail.html, templates/hello.html, templates/error/404.html을 아래 내용으로 작성합니다. HTML 파일을 더블클릭해서 여는 대신 서버 주소로 접속해야 Thymeleaf가 처리됩니다.

### src/main/resources/templates/members/list.html

```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>회원 관리 · 목록</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<main>
    <p class="eyebrow">JAVA APPLICATION / WEEK 13</p>
    <h1>회원목록</h1>
    <p>이름을 검색하고 회원 정보를 확인하세요.</p>
    <form method="get" th:action="@{/members}">
        <label for="keyword">회원 이름</label>
        <input id="keyword" name="keyword" th:value="${keyword}"
               placeholder="예: 김" maxlength="100">
        <button type="submit">검색</button>
        <a th:href="@{/members}">전체보기</a>
    </form>
    <p>조회 결과 <strong th:text="${count}">0</strong>명</p>
    <table>
        <caption>회원 조회 결과</caption>
        <thead>
        <tr><th scope="col">번호</th><th scope="col">이름</th>
            <th scope="col">이메일</th><th scope="col">상세</th></tr>
        </thead>
        <tbody>
        <tr th:each="member : ${members}">
            <td th:text="${member.id}">1</td>
            <td th:text="${member.name}">김민수</td>
            <td th:text="${member.email}">minsu@example.com</td>
            <td><a th:href="@{/members/{id}(id=${member.id})}">상세보기</a></td>
        </tr>
        <tr th:if="${#lists.isEmpty(members)}">
            <td colspan="4">검색 결과가 없습니다.</td>
        </tr>
        </tbody>
    </table>
</main>
</body>
</html>
```

### src/main/resources/templates/members/detail.html

```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>회원 상세</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<main>
    <h1>회원 상세정보</h1>
    <dl>
        <dt>번호</dt><dd th:text="${member.id}">1</dd>
        <dt>이름</dt><dd th:text="${member.name}">김민수</dd>
        <dt>이메일</dt><dd th:text="${member.email}">minsu@example.com</dd>
    </dl>
    <a th:href="@{/members}">목록으로</a>
</main>
</body>
</html>
```

### src/main/resources/templates/hello.html

```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Spring MVC 인사</title>
    <link rel="stylesheet" th:href="@{/css/style.css}">
</head>
<body>
<main>
    <h1>Spring MVC 첫 화면</h1>
    <p><span th:text="${name}">학습자</span>님, 안녕하세요.</p>
    <a th:href="@{/members}">회원목록으로</a>
</main>
</body>
</html>
```

### src/main/resources/templates/error/404.html

```html
<!DOCTYPE html>
<html lang="ko">
<head><meta charset="UTF-8"><title>회원을 찾을 수 없습니다</title></head>
<body>
    <h1>회원을 찾을 수 없습니다.</h1>
    <p>회원 번호 또는 주소를 확인하세요.</p>
    <a href="/members">회원목록으로</a>
</body>
</html>
```

### src/main/resources/static/css/style.css

```css
body {
    margin: 0;
    background: #f1f5f9;
    color: #172536;
    font: 16px/1.6 "Malgun Gothic", sans-serif;
}
main {
    max-width: 960px;
    margin: 48px auto;
    padding: 32px;
    background: white;
    border-radius: 16px;
}
h1 { margin: 4px 0 12px; }
.eyebrow { color: #16716b; font-weight: bold; }
form { display: flex; flex-wrap: wrap; gap: 12px; align-items: center; }
input, button { padding: 10px; font: inherit; }
button { background: #16716b; color: white; border: 0; border-radius: 6px; }
a { color: #125d98; }
table { width: 100%; border-collapse: collapse; margin-top: 20px; }
caption { text-align: left; margin-bottom: 10px; }
th, td { padding: 14px; text-align: left; border-bottom: 1px solid #dce3eb; }
th { background: #eaf3f3; }
dt { font-weight: bold; }
dd { margin: 0 0 16px; }
@media (max-width: 640px) {
    main { margin: 16px 8px; padding: 16px; overflow-x: auto; }
}
```

## 6. 실행 결과 확인

서버를 Stop 후 다시 실행하고 아래 순서로 확인합니다. 개발자 도구 Network에서는 상태 코드를 확인합니다.

| 요청·조작 | 샘플 5명 기준 예상 결과 |
| --- | --- |
| /hello | 학습자님, 안녕하세요. |
| /members | 5명, id 오름차순 |
| 검색창 김 입력 → 검색 | 김민수·김하늘, 2명 |
| 검색창에 공백만 입력 | 전체 5명 |
| 없는회원 검색 | 0명, 검색 결과가 없습니다. |
| % 검색 | 0명, 일반 문자로 처리 |
| 김민수 상세보기 | /members/1, 이름·이메일 표시 |
| /members/999 | 404, 회원을 찾을 수 없습니다. |
| /members/abc | 400, 숫자로 바인딩 불가 |
| 전체보기 클릭 | 검색어 초기화, 5명 |

SQL 인젝션 확인용 검색어 `' OR 1=1 --`는 데이터에 이 문자열이 없으므로 0명이 나와야 합니다. 검색어가 SQL의 구조가 되지 않도록 ?로 바인딩했습니다. `/hello`의 name에 HTML 태그를 입력하면 th:text가 이스케이프해 글자로 표시합니다.

## 7. 자동 테스트와 수동 검증 기록

프로젝트 우클릭 → Run As → Maven build... → Goals `test`. H2의 테스트 전용 스키마로 목록·검색·없는 id·HTML 이스케이프를 검사합니다. H2 테스트 성공은 MySQL 접속 및 MySQL 전용 DDL 실행 성공을 보장하지 않으므로 위 수동 검증을 함께 수행합니다.

| 검사 | 실제 결과(학생 작성) | 통과 여부 |
| --- | --- | --- |
| Java 21·서버 시작 | 직접 기록 | 직접 기록 |
| MySQL 초기 5명 | 직접 기록 | 직접 기록 |
| 이름 검색 2명·빈 결과 | 직접 기록 | 직접 기록 |
| 상세·404·400 | 직접 기록 | 직접 기록 |
| mvn test | 직접 기록 | 직접 기록 |

### 오류 해결과 확인

| 현상 | 확인 및 해결 |
| --- | --- |
| DB_PASSWORD placeholder 오류 | Run Configurations의 실행 중인 구성에 DB_PASSWORD 추가 |
| Access denied | Workbench의 javaapp13 연결로 비밀번호 확인. 계정 존재 시 ALTER USER로 변경 |
| Communications link failure | Windows 서비스 MySQL80 실행, localhost:3306 확인 |
| Unknown database/table | root로 01_schema_seed.sql 전체 실행, datasource URL 확인 |
| Port 8080 already in use | 이전 Java 서버 Stop. 다른 앱 사용 중이면 server.port=8081로 변경 후 URL도 변경 |
| Template not found | templates/members/list.html 경로와 return "members/list" 일치 확인 |
| 화면 대신 문자열만 표시 | HTML 반환 Controller에 @RestController 대신 @Controller 사용 |
| /members가 404 | 패키지가 com.example.members 하위인지, 클래스 매핑과 메서드 매핑 확인 |
| /members/abc가 400 | id는 Long 타입이므로 숫자 입력 |
| 한글 깨짐 | 파일 UTF-8, HTML meta charset, DB utf8mb4 확인 |
| Maven dependency 오류 | 인터넷·프록시 설정 확인 후 Maven Update Project. JDK 21 확인 |

검증 기록: 이 자료 작성 환경에는 Java 21이 있으나 Maven/MySQL이 설치되어 있지 않고 Maven Central 접속도 제한되어 있어 Spring 애플리케이션 빌드와 실제 MySQL 연동 실행은 확인하지 못했습니다. XML·파일 경로·템플릿 구조를 정적으로 점검했으며 실행 결과 표는 샘플 데이터 기준 예상값입니다. 제공한 자동 테스트와 아래 수동 체크를 강의실 환경에서 실행하여 최종 확인하세요.

## 8. 도전 과제와 제출

본문 과제의 가상 회원 3명을 추가한 뒤 전체 8명, 김 검색 3명으로 바뀌는지 확인합니다. 화면 제목을 동아리에 맞게 변경하고 목록→상세→목록 경로를 캡처하세요. 선택 확장으로 이메일 표시를 숨기고 이름과 번호만 보이게 할 수 있습니다. 이때 Java·DB를 삭제하지 않고 View만 수정해도 되는 이유를 보고서에 설명합니다.

제출: 전체 프로젝트 소스 ZIP, 사용 SQL, 화면 4장, 요청→Controller→Service→Repository→View의 실제 파일·메서드명을 적은 report.md. DB_PASSWORD 값은 제출물에 넣지 않습니다.
