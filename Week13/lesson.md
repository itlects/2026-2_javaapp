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

## 1. 학습목표

**Lect13 Spring 기반 웹 프로그램의 기본 구조를 이해하고 구현**

수업을 마치면 다음을 수행할 수 있습니다.

- Java 21 Maven 기반 Spring Boot 프로젝트를 Eclipse에서 가져와 실행한다.
- MVC의 Model·View·Controller 역할과 요청 처리 흐름을 설명한다.
- Controller, View, Mapping을 연결하여 인사 화면과 회원목록을 만든다.
- MySQL 회원 데이터를 Spring JDBC로 조회하고 Model로 View에 전달한다.
- 별도 워크북에서 이름 검색·상세 조회·빈 결과 처리를 완성한다.

| 수업 구간 | 학습 활동 | 확인 결과 |
| --- | --- | --- |
| 1교시 50분 | 개발환경·Spring Boot·MVC·인사 예제 | /hello 출력 |
| 2교시 50분 | DB 준비·계층별 코드·회원목록 | 5명 목록 출력 |
| 3교시 50분 | 워크북 검색·상세·오류 확인·평가 | 검색 2명·404 확인 |

휴식 시간은 별도이며 환경 설치는 수업 전 준비합니다. 입력·수정·삭제는 다음 단계의 확장 주제로 두고 이번에는 조회 흐름을 충분히 익힙니다.

## 2. 이론

### 2.1 Spring과 Spring Boot

Spring은 객체 생성·의존성 주입, 웹 요청 처리, 데이터 접근을 지원하는 프레임워크입니다. Spring Boot는 Spring을 바탕으로 Starter 의존성, 조건에 맞는 자동 설정, 내장 서버를 제공해 실행 가능한 프로젝트를 빠르게 구성합니다. pom.xml의 Starter가 라이브러리를 묶어 가져오므로 JAR를 수동으로 복사하지 않습니다.

`SpringApplication.run`은 애플리케이션 컨텍스트를 시작하고 설정된 내장 웹 서버를 구동합니다. 시작 클래스의 패키지 아래에서 @Controller, @Service, @Repository 등이 탐색됩니다. 따라서 모든 클래스를 `com.example.members` 하위에 둡니다.

### 2.2 MVC와 계층별 책임

| 구성 | 이 예제 | 책임 |
| --- | --- | --- |
| Controller | MemberController | 경로·파라미터 수신, Service 호출, Model과 View 선택 |
| Model 데이터 | Member, List<Member> | 회원 정보 표현 |
| View 전달 Model | org.springframework.ui.Model | 이름 있는 속성을 View로 전달 |
| Service | MemberService | 검색어 공백 정리, 목록·상세 처리 규칙 |
| Repository | MemberRepository | SQL 실행 및 행을 Member로 변환 |
| View | templates/members/list.html | HTML과 Thymeleaf로 표시 |

Service와 Repository는 MVC를 구현할 때 관심사를 분리하기 위한 계층입니다. MVC의 Model은 단순히 하나의 Model 클래스만을 뜻하지 않으며 도메인 데이터와 처리 구조를 포함합니다.

회원목록 요청의 흐름은 브라우저 GET 요청을 DispatcherServlet이 받은 후 매핑된 Controller를 호출하는 것에서 시작합니다. Controller가 Service를 호출하면 Repository가 MySQL 결과를 Member 목록으로 변환합니다. Controller는 이를 Model에 넣고 View 이름을 반환합니다. Thymeleaf는 HTML을 렌더링하여 브라우저로 보냅니다. 브라우저가 Repository나 MySQL에 직접 접속하지 않습니다.

### 2.3 Mapping과 View 이름

| 요청 | 매핑 | 파라미터 | View |
| --- | --- | --- | --- |
| GET /hello?name=홍길동 | @GetMapping("/hello") | @RequestParam name | hello |
| GET /members | 클래스 /members + 메서드 @GetMapping | keyword 기본값 빈 문자열 | members/list |
| GET /members?keyword=김 | 동일 목록 메서드 | @RequestParam keyword | members/list |
| GET /members/1 | 클래스 /members + /{id} | @PathVariable id | members/detail |

`return "members/list"`는 실제 URL로 재요청하는 명령이 아니라 논리적인 View 이름입니다. 기본 Thymeleaf 설정이 `classpath:/templates/`와 `.html`을 결합합니다. `@Controller`에 `@ResponseBody`가 없으면 문자열을 View 이름으로 처리합니다. `@RestController`는 기본적으로 반환값을 응답 본문으로 쓰므로 이번 HTML 화면용 Controller와 구분합니다.

### 2.4 의존성 주입과 JDBC

MemberController는 생성자로 MemberService를 받고, Service는 Repository를 받습니다. 생성자가 하나이면 @Autowired를 생략할 수 있습니다. Spring이 생성하고 관리하는 Bean을 전달해 주므로 Controller 안에서 `new MemberService(...)`를 직접 호출하지 않습니다.

JdbcTemplate은 커넥션·문장·결과 자원 처리를 지원합니다. RowMapper는 결과 한 행을 Member 한 개로 변환합니다. 검색 SQL의 `?`와 인자를 분리해 전달하면 입력이 SQL 문장으로 합쳐지지 않습니다. DB 계정에는 SELECT만 부여하고 비밀번호는 실행 환경변수로 설정합니다.

### 2.5 Thymeleaf와 디렉터리

| 위치·표현 | 의미 |
| --- | --- |
| pom.xml | Java·Spring Boot 버전 및 의존성 |
| src/main/java | Controller·Service·Repository·Member·시작 클래스 |
| src/main/resources/templates | 서버에서 처리할 HTML View |
| src/main/resources/static | CSS 등 정적 파일 |
| application.properties | DB 연결과 서버 설정 |
| th:each | 목록 반복 |
| th:text | 값을 이스케이프하여 텍스트 출력 |
| th:href, @{...} | 링크 URL 생성 |
| ${members} | Model의 members 속성 참조 |

## 3. 예제

### 예제 1. 첫 MVC 화면

아래 공통 준비를 먼저 수행합니다. HelloController만 있어도 프로젝트에 JDBC 의존성이 있으므로 이 제공 프로젝트에서는 DB 설정을 완료한 뒤 실행합니다.
### 단계 1. 프로젝트 준비

1. 제공한 ZIP을 `C:\javaapp\Week13`에 압축 해제합니다. `spring-members\pom.xml`이 있는지 확인합니다. 파일 탐색기의 확장명 표시를 켜서 `.java.txt`, `.html.txt`가 되지 않도록 합니다.
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

브라우저에 `/hello?name=홍길동`을 입력하면 “홍길동님, 안녕하세요.”가 보여야 합니다. name을 생략하면 “학습자님, 안녕하세요.”가 보입니다. Model의 속성 이름 name과 `${name}`이 같아야 합니다.

### 예제 2. Spring 회원목록

아래 Member → Repository → Service → Controller → View 순서로 파일을 만듭니다. 제공 완성본에는 워크북 확장인 검색·상세 코드도 포함되어 있습니다. 우선 `/members`로 요청하여 기본 목록 경로만 따라갑니다. 파일의 package와 저장 위치를 일치시키세요.

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

읽는 순서: `findAll()`이 SELECT로 5명을 만들고, `list("")`가 전체 목록을 선택합니다. Controller가 members·keyword·count를 Model에 넣고 `members/list`를 반환합니다. View의 th:each가 회원 한 명마다 tr 한 줄을 만듭니다. Java getter가 `${member.name}` 등의 속성 접근을 지원합니다.

## 4. 실습 및 과제

### 필수 실습

1. `/hello?name=본인이름` 화면과 `/members`의 5행을 확인합니다.
2. 회원목록 요청을 담당하는 메서드에 breakpoint를 걸고 Debug As → Java Application으로 실행합니다. 요청 시 keyword, members.size(), 반환 View 이름을 확인합니다.
3. 개발자 도구 Network에서 /members 응답이 HTML이고 CSS가 별도 요청되는 것을 확인합니다.
4. 하위 워크북을 따라 이름 검색·상세 조회·빈 결과·잘못된 id를 확인합니다.

### 제출 과제: 지역 동아리 회원 조회 페이지

제공 구조를 활용해 화면 제목을 “우리 동아리 회원 조회”로 바꾸고, root Workbench 연결에서 샘플 회원을 3명 추가하세요. 실제 개인정보 대신 가상 이름과 example.com 주소를 사용합니다. 조회 전용 애플리케이션 계정 권한은 그대로 사용합니다.

```sql
USE javaapp13;
INSERT INTO members (id, name, email) VALUES
    (6, '정다은', 'daeun@example.com'),
    (7, '김도윤', 'doyun@example.com'),
    (8, '한지우', 'jiwoo@example.com');
```

추가 후 전체 8명, “김” 검색 3명, “정다은” 검색 1명, 없는 이름 검색 0명이 예상됩니다. `/members/6` 상세에는 정다은이 보여야 합니다. id가 이미 존재하면 재실행하지 말고 SELECT로 확인하세요.

제출물은 프로젝트 ZIP(target·비밀번호·환경변수 제외), 전체/검색/상세/빈 결과 화면 4장, `report.md`입니다. 보고서에는 요청 URL, Controller 메서드, Model 속성, View 경로를 대응시켜 적고 오류 한 건과 해결 방법을 포함하세요. 사용한 SQL도 함께 제출합니다.

| 평가 항목 | 배점 | 확인 기준 |
| --- | --- | --- |
| MVC 구조 | 25 | Controller·Service·Repository 책임 분리와 설명 |
| 실행·DB | 25 | Java 21 실행, DB 8명 정상 조회 |
| 검색·상세·예외 | 30 | 3명/1명/0명 결과와 상세·404 |
| 코드 가독성·보고서 | 20 | 들여쓰기·줄바꿈·전체 코드·검증 증거 |

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
## 5. 평가문제(객관식 및 단답형)

각 객관식은 정답 하나를 고르세요. 먼저 답안을 작성한 후 정답·해설을 확인합니다.

1. Spring Boot가 웹 개발을 간소화하는 핵심 구성은? ① 수동 JAR 복사만 제공 ② 자동 설정·Starter·내장 서버 ③ HTML만 컴파일 ④ MySQL 자체 대체
2. MVC에서 HTTP 요청을 받아 Service를 호출하고 View 이름을 반환하는 것은? ① View ② DB ③ Controller ④ CSS
3. `@Controller` 메서드가 `"members/list"`를 반환할 때 찾는 파일은? ① static/list.jsp ② templates/members/list.html ③ controller/list.java ④ sql/list.sql
4. 이름 검색을 GET으로 처리하는 매핑은? ① @GetMapping ② @PostConstruct ③ @Repository ④ @SpringBootApplication
5. `/members?keyword=김`의 keyword를 받는 것은? ① @PathVariable ② @RequestBody만 가능 ③ @RequestParam ④ @Service
6. `/members/1`에서 1을 받는 것은? ① @PathVariable ② @Controller ③ Model ④ @Bean
7. View에 회원목록을 전달하는 코드는? ① return members.toString() ② model.addAttribute("members", members) ③ System.out.println(members) ④ new Model()
8. SQL에 사용자 입력을 전달할 때 적절한 것은? ① 문자열을 SQL 뒤에 그대로 연결 ② HTML에 SQL 삽입 ③ ?와 바인딩 인자 사용 ④ 비밀번호를 URL에 노출
9. `th:text`의 기본 동작은? ① HTML 태그를 무조건 실행 ② 문자열을 HTML 이스케이프하여 출력 ③ SQL 실행 ④ URL 매핑 생성
10. 생성자 주입의 설명으로 옳은 것은? ① 모든 객체를 View에서 new ② Spring이 관리하는 의존 객체를 생성자로 전달 ③ DB 테이블을 생성자에 삽입 ④ getter를 삭제하는 기능

단답형

11. 시작 클래스에 붙이는 Spring Boot 기본 애너테이션을 쓰세요.
12. 클래스에 `@RequestMapping("/members")`, 메서드에 `@GetMapping("/{id}")`가 있을 때 id=3의 요청 경로를 쓰세요.
13. `Model`과 데이터 클래스 `Member`의 역할 차이를 한 문장으로 쓰세요.
14. 회원 id=999가 없을 때 본 예제가 응답하는 HTTP 상태 코드를 쓰세요.
15. `return "hello";`가 문자열 본문이 아니라 HTML View로 처리되는 이유를 쓰세요.

### 정답 및 해설

| 번호 | 정답 | 해설 |
| --- | --- | --- |
| 1 | ② | 자동 설정과 Starter, 내장 서버로 초기 설정을 줄임 |
| 2 | ③ | Controller는 요청과 응답 흐름 담당 |
| 3 | ② | Thymeleaf 기본 View 위치와 접미사 적용 |
| 4 | ① | GET 요청 매핑 |
| 5 | ③ | 쿼리 문자열 파라미터 바인딩 |
| 6 | ① | URL 경로의 변수 바인딩 |
| 7 | ② | Model의 속성 이름으로 View에서 접근 |
| 8 | ③ | SQL 구조와 입력값을 분리 |
| 9 | ② | 사용자 입력이 태그로 해석되지 않도록 출력 |
| 10 | ② | 필요한 의존성을 명시적으로 전달받음 |
| 11 | @SpringBootApplication | 자동 설정과 컴포넌트 탐색 등을 활성화 |
| 12 | /members/3 | 클래스·메서드 경로 결합 |
| 13 | Model은 View 전달용 속성 저장소, Member는 회원 한 명의 데이터 | 둘을 같은 객체로 혼동하지 않기 |
| 14 | 404 | 존재하지 않는 회원 |
| 15 | @Controller 메서드에 @ResponseBody가 없어서 반환값을 View 이름으로 해석 | @RestController는 기본적으로 응답 본문 처리 |

## 6. 학습요약

- Spring Boot 프로젝트는 pom.xml과 시작 클래스를 기준으로 실행하고 내장 서버를 사용한다.
- Controller는 요청을 받아 Service를 호출하고 Model 속성과 View 이름을 결정한다.
- Repository의 JDBC 결과는 Member 객체로 변환되며 View는 HTML 표시를 담당한다.
- @RequestParam은 쿼리 파라미터, @PathVariable은 경로 변수에 사용한다.
- templates 아래 View와 static 아래 CSS의 역할을 구분한다.
- 다음 학습에서는 이번 조회 구조를 바탕으로 입력 폼과 등록·수정·삭제 흐름을 확장할 수 있다.
