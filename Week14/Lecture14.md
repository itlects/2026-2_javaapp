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

<table-of-contents />

## 1. 학습목표

수업을 마치면 다음을 수행할 수 있습니다.

- Spring MVC 회원관리에서 목록·등록·수정·삭제를 구현한다.
- Controller-Service-DAO 계층의 역할을 설명하고 생성자 주입으로 연결한다.
- MySQL 테이블과 JdbcTemplate을 연결하고 SQL 파라미터 바인딩을 적용한다.
- 입력 검증·중복 제약·PRG를 적용하고 브라우저와 SQL로 데이터 변경을 확인한다.

강의계획의 Lect14 학습내용: Spring 회원관리 CRUD 프로그램, Controller-Service-DAO 구조, MySQL 연동을 모두 포함합니다.

| 시간 | 활동 | 확인 결과 |
| --- | --- | --- |
| 0~25분 | 계층과 CRUD 이론 | 요청·SQL·뷰의 역할 설명 |
| 25~50분 | Eclipse·MySQL 준비 | DB 연결과 초기 데이터 |
| 50~95분 | DAO·Service·Controller 구현 | 등록·조회 흐름 추적 |
| 95~135분 | 화면 연결 및 수정·삭제 | CRUD 동작 |
| 135~165분 | 워크북 검증·확장 과제 | 테스트 증거 |
| 165~180분 | 평가문제·요약 | 취약 개념 복습 |

## 2. 이론

### 2.1 Spring과 Spring Boot

Spring은 객체 생성·의존성 주입과 웹 요청·트랜잭션 처리를 지원합니다. Spring Boot는 Spring 애플리케이션의 의존성과 자동 설정, 내장 웹 서버 실행을 묶어 개발 환경 구성을 단순화합니다. 이 수업은 Spring MVC를 Spring Boot로 실행합니다.

### 2.2 Controller-Service-DAO와 의존성 주입

| 구성 | 책임 | 예제 |
| --- | --- | --- |
| Controller | URL과 HTTP 메서드 매핑, 검증 결과 확인, 모델·뷰 선택 | MemberController |
| Service | 업무 처리 단위, 입력 정리, 존재 확인, 트랜잭션 | MemberService |
| DAO | SQL 수행, ResultSet을 객체로 변환 | MemberDao |
| DTO/폼 | 조회 데이터 및 입력값 전달 | Member, MemberForm |
| View | 모델을 HTML로 표시 | list.html, form.html |

브라우저 요청을 Controller가 받고 Service를 호출합니다. Service가 DAO를 통해 DB를 변경하면 Controller가 목록으로 redirect합니다. 목록 GET에서 읽은 데이터를 Model에 담고 Thymeleaf가 HTML을 만듭니다. 생성자 인수로 필요한 객체를 받으면 Spring이 관리 객체(Bean)를 주입합니다. `new MemberDao(...)`를 Controller에서 직접 호출하지 않습니다.

`@Controller`, `@Service`, `@Repository`는 역할을 표현하고 컴포넌트 검색 대상이 됩니다. `@SpringBootApplication`이 있는 edu.javaapp.member 패키지 안에 모든 Java 클래스를 배치합니다.

### 2.3 CRUD와 URL 설계

| 작업 | 요청 | SQL / 동작 |
| --- | --- | --- |
| 목록 | GET /members | SELECT |
| 등록 폼 | GET /members/new | 빈 폼 |
| 등록 | POST /members | INSERT |
| 수정 폼 | GET /members/{id}/edit | SELECT WHERE id |
| 수정 | POST /members/{id} | UPDATE WHERE id |
| 삭제 | POST /members/{id}/delete | DELETE WHERE id |

HTML form이 직접 지원하는 GET/POST에 맞춰 설계했습니다. API에서는 PUT/DELETE를 사용할 수 있지만 이 예제에 메서드 변환 필터는 필요하지 않습니다.

### 2.4 MySQL·JDBC·트랜잭션

DataSource는 DB 연결을 제공하고, JdbcTemplate은 연결·문장·결과 처리의 반복 코드를 줄입니다. SQL에는 `?`를 쓰고 인수로 입력값을 전달합니다. 문자열 연결로 사용자 입력을 SQL에 붙이지 않습니다. RowMapper는 SQL 결과 한 행을 Member로 바꿉니다. DAO의 update 반환값은 영향받은 행 수입니다.

회원 id는 BIGINT PK AUTO_INCREMENT이며 변경하지 않습니다. 이메일은 NOT NULL UNIQUE입니다. 화면에서 중복을 검사해도 동시 요청이 가능하므로 DB 제약조건이 최종 기준입니다. DuplicateKeyException을 잡아 이메일 필드에 안내합니다. 이 클래스의 트랜잭션은 Service 프록시 경계에서 처리되므로 Controller가 예외를 처리할 때는 해당 Service 호출의 롤백이 끝난 상태입니다.

`@Transactional`은 하나의 업무 처리에 속한 변경을 함께 확정하거나 실패 시 되돌립니다. 기본적으로 런타임 예외가 전파되면 롤백합니다. 조회에는 readOnly=true, 쓰기 메서드에는 별도 @Transactional을 사용합니다. 읽기 전용은 최적화 힌트이며 보안 권한을 대체하지 않습니다.

### 2.5 검증과 화면 처리

`@NotBlank`, `@Email`, `@Size`, `@Pattern`으로 입력 규칙을 표현하고 `@Valid`로 검사합니다. BindingResult는 검증 대상 인수 바로 다음에 둡니다. 브라우저의 required 속성만 믿지 않고 서버에서도 검증합니다. `th:field`는 값 바인딩, `th:errors`는 오류 표시, `th:each`는 반복, `th:text`는 이스케이프된 텍스트 출력입니다.

PRG(Post-Redirect-Get)는 저장 성공 후 목록 GET으로 이동하는 패턴입니다. 검증 오류가 있으면 입력값을 유지하며 같은 폼을 반환합니다. 없는 회원 수정/삭제는 404로 처리합니다.

## 3. 예제: Spring 회원관리 CRUD

완성 화면은 회원 목록과 공통 입력 폼으로 구성합니다. 별도 워크북에 모든 파일의 전체 코드와 작성 순서가 있습니다. 다음 코드는 완성 프로젝트의 실제 DAO 및 Service입니다.

### MemberDao.java

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

### MemberService.java

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
## 4. 실습 및 과제

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
## 5. 평가문제(객관식 및 단답형)

문제 1. HTTP 요청을 받아 Service를 호출하고 뷰 이름을 반환하는 계층은? ① DAO ② Controller ③ MySQL ④ CSS

문제 2. JdbcTemplate에서 `?`에 값을 전달하는 주요 이유는? ① HTML 꾸미기 ② URL 변경 ③ SQL과 데이터 분리 ④ 포트 변경

문제 3. 이메일 중복을 동시 요청까지 포함하여 최종 방지하는 것은? ① placeholder ② UNIQUE 제약조건 ③ GET ④ CSS

문제 4. 삭제 처리에 적합한 이 예제의 요청은? ① GET /members ② GET /members/new ③ POST /members/{id}/delete ④ GET /style.css

문제 5. POST 처리 성공 후 redirect하는 방식의 효과는? ① 암호화 ② 목록 새로고침에 의한 같은 POST 재전송 방지 ③ 자동 백업 ④ SQL 생략

문제 6. Bean Validation 어노테이션을 선언한 폼에 서버 검증을 실행하는 어노테이션은? ① @Valid ② @Repository ③ @GetMapping ④ @Bean

문제 7. 업무 처리 단위의 트랜잭션을 선언하기 적합한 계층은? ① CSS ② HTML ③ Service ④ 브라우저

문제 8. `th:text`의 설명으로 적절한 것은? ① SQL 실행 ② 기본적으로 HTML 문자를 이스케이프하여 출력 ③ DB 연결 ④ 암호 저장

단답형 1. CRUD에 해당하는 SQL 네 가지를 작성하시오.

단답형 2. Controller → Service → DAO의 역할을 각각 한 문장으로 작성하시오.

단답형 3. `@Valid` 다음의 BindingResult를 검사하는 이유를 작성하시오.

단답형 4. 수정 SQL에서 WHERE id = ?를 누락할 때 생기는 문제는 무엇인가?

단답형 5. `spring.sql.init.mode=never`로 설정한 이 프로젝트에서 테이블을 생성하는 방법은 무엇인가?

### 정답 및 해설(자가학습)

| 문항 | 정답 | 해설 |
| --- | --- | --- |
| 객관식 1~4 | ②, ③, ②, ③ | 요청은 Controller, SQL 바인딩은 데이터 분리, 중복은 DB 제약, 삭제는 POST |
| 객관식 5~8 | ②, ①, ③, ② | PRG, 입력 검증, 업무 트랜잭션, 안전한 텍스트 출력 |
| 단답형 1 | INSERT, SELECT, UPDATE, DELETE | 생성·조회·수정·삭제 순서 |
| 단답형 2 | 요청/뷰, 업무 규칙/트랜잭션, SQL/매핑 | 각 책임을 구분해야 함 |
| 단답형 3 | 검증 오류 확인과 입력 화면 재표시 | 오류가 있으면 DB 변경하지 않음 |
| 단답형 4 | 전체 행 수정 위험 | 대상 회원을 PK로 한정해야 함 |
| 단답형 5 | Workbench 관리자 연결로 01_setup.sql 실행 | 앱은 자동 DDL을 실행하지 않음 |
## 6. 학습요약

- 요청과 화면은 Controller, 업무 규칙과 트랜잭션은 Service, SQL은 DAO에 둡니다.
- MySQL 연결 정보는 application.properties와 실행 환경변수로 구성합니다.
- JdbcTemplate은 바인딩된 SQL과 RowMapper로 CRUD를 수행합니다.
- 서버 입력 검증과 DB UNIQUE 제약조건을 함께 적용합니다.
- 수정·삭제 SQL은 PK 조건을 포함하고, 변경 성공 후 PRG로 목록을 표시합니다.
- 재실행 후에도 데이터가 남는지 확인해야 DB 영속성을 검증할 수 있습니다.

### 공식 참고자료

- Spring Boot 3.5 요구사항: https://docs.spring.io/spring-boot/3.5/system-requirements.html
- Spring JDBC 입문: https://spring.io/guides/gs/relational-data-access/
- Spring SQL 지원: https://docs.spring.io/spring-boot/reference/data/sql.html
