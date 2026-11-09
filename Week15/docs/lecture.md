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

## 1. 학습목표

**Lect15 보강주간 — JSP/Spring 미완성 기능 보완 및 프로젝트 완성**

수업 종료 시 자신의 프로젝트에서 미완성 기능을 찾아 우선순위를 정하고, 기능 하나 이상을 수정한 뒤 정상·실패·경계값 검증 결과와 실행 설명서를 제출할 수 있다. 완성의 기준은 화면 표시, DB 저장, 잘못된 입력 처리, 재실행 가능성이다.

| 시간 | 활동 | 남길 결과 |
| --- | --- | --- |
| 0~20분 | 기존 프로젝트 기동 및 기능 점검 | 미완성 목록 3개 |
| 20~50분 | 요청 흐름·입력 검증·오류 원인 복습 | 원인과 수정 위치 |
| 50~100분 | 우선순위 높은 기능 수정 | 기능 1개 이상 동작 |
| 100~150분 | 워크북 적용 또는 자기 프로젝트 확장 | 정상·예외 시나리오 검증 |
| 150~180분 | 재실행·시연·제출 점검 | 실행 설명서와 최종 ZIP |

180분은 보강 수업 운영 제안이다. 전체 워크북을 새로 만드는 학생은 사전 다운로드 후 진행하고, 기존 프로젝트가 있는 학생은 필요한 단계만 적용한다.

## 2. 이론

### 2.1 프로젝트 완성 기준과 수정 우선순위

미완성은 '버튼이 없다'뿐 아니라 잘못된 값이 저장되거나 다른 PC에서 실행되지 않는 상태도 포함한다. 재현 절차 → 예상 결과 → 실제 결과 → 원인 → 수정 → 재검증 순서로 기록한다.

| 우선순위 | 예시 | 완료 판정 |
| --- | --- | --- |
| P0 | 서버가 안 켜짐, DB 연결 실패 | 기동 및 목록 조회 성공 |
| P1 | 수정·삭제 불가, 입력 오류로 데이터 손상 | CRUD와 입력 검증 통과 |
| P2 | 검색 조건 사라짐, 빈 결과 안내 누락 | 검색·이동·빈 화면 검증 |
| P3 | 색상·간격·버튼 문구 | 시연 전 가독성 개선 |

### 2.2 JSP와 Spring에서 같은 문제 찾기

| 단계 | JSP/Servlet | Spring MVC |
| --- | --- | --- |
| 요청 수신 | @WebServlet / doGet·doPost | @GetMapping·@PostMapping |
| 입력 검증 | null·strip·길이·허용값 검사 | @Valid와 BindingResult |
| DB 처리 | DAO / PreparedStatement | Repository / JdbcTemplate의 ? 바인딩 |
| 화면 출력 | request 속성, forward, c:out | Model, 뷰 이름, th:text |
| 저장 후 이동 | sendRedirect | redirect:/tasks |

새로고침에 의한 반복 POST를 줄이는 PRG(Post/Redirect/Get)를 적용한다. PRG만으로 더블 클릭이나 네트워크 재시도의 모든 중복 저장을 막지는 못한다. 업무상 중복 불가 항목은 UNIQUE 제약이나 요청 식별자 검증을 별도로 설계한다.

### 2.3 검증은 브라우저와 서버 양쪽에서

required·maxlength는 사용자 안내에 유용하지만 변조 요청을 막지 못한다. 서버는 공백 제목, 101자 제목, 허용하지 않은 상태를 거부해야 한다. 검증 실패 시 입력값과 오류 문장을 함께 다시 보여주고 DB 변경은 하지 않는다. 수정 대상이 없으면 404, 잘못된 페이지 번호는 400, CSRF 토큰이 없는 POST는 403으로 구별한다.

### 2.4 안전한 저장과 출력

SQL 문자열에 입력을 더하지 않고 ?에 바인딩한다. HTML 출력은 JSP의 c:out 또는 Thymeleaf의 th:text를 사용한다. th:utext는 이 예제에서 사용하지 않는다. 변경 요청은 POST로 제한하고 Spring Security의 CSRF 보호를 유지한다. Thymeleaf의 th:action을 사용하는 POST 폼에는 토큰이 자동 포함된다.

서로 다른 두 테이블을 함께 변경해야 하는 기능은 Service의 public 메서드에 @Transactional을 두어 작업 단위를 묶는다. 이번 워크북은 한 번의 쓰기 SQL로 한 행을 변경하므로 별도 트랜잭션 서비스를 추가하지 않는다.

### 2.5 오류와 재현 가능한 실행

브라우저에는 복구 안내를, 서버 로그에는 진단 정보를 남긴다. DB 암호는 소스 대신 실행 환경변수로 전달한다. 제출 설명서에는 JDK·DB·SQL 실행 순서·환경변수·실행 URL·검증 결과를 쓴다. 외부 서버 배포나 새 기술 도입보다 현재 기능을 끝내는 데 집중한다.

## 3. 예제

### 예제 1 — JSP/Servlet의 제목 검증 보완

기존 doPost에서 DB 저장 전에 다음 검사를 넣는다. 파일명·속성명·forward 경로는 자신의 기존 프로젝트와 맞춘다. 이 코드는 기존 Servlet에 넣는 수정 예시이며 별도 실행 프로젝트가 아니다.

```java
String rawTitle = request.getParameter("title");
String title = rawTitle == null ? "" : rawTitle.strip();

if (title.isEmpty() || title.length() > 100) {
    request.setAttribute("error", "제목은 1~100자로 입력하세요.");
    request.setAttribute("title", title);
    request.getRequestDispatcher("/WEB-INF/views/form.jsp")
            .forward(request, response);
    return;
}

// 기존 DAO의 INSERT 또는 UPDATE 호출은 이 검사 다음에 둔다.
```

form.jsp는 Jakarta JSTL 의존성이 있는 기존 프로젝트를 기준으로 한다.

```jsp
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<p><c:out value="${requestScope.error}" /></p>
<input name="title" maxlength="100" required
       value="<c:out value='${requestScope.title}' />">
```

공백만 입력하면 오류를 보여주며 DB 행 수가 늘지 않아야 한다. 기존 인코딩 Filter가 doPost보다 먼저 UTF-8을 설정하는지도 확인한다.

### 예제 2 — Spring 수정 처리와 PRG

다음은 워크북에 포함된 실제 메서드이다. @Valid 바로 뒤의 BindingResult가 검증 오류를 받는다. 오류가 있으면 form, 성공하면 목록으로 redirect한다.

```java
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
```

### 예제 3 — 바인딩과 대상 행 확인

```java
repository.get(id); // 없는 번호이면 404
jdbc.update("UPDATE tasks SET title = ?, status = ? WHERE id = ?",
        form.getTitle().strip(), form.getStatus(), id);
```

수정 전후 Workbench에서 같은 id의 title·status를 조회한다. 제목이 같아도 정상 저장되어야 한다. 목록 정렬은 id DESC로 고정해 페이지 간 순서를 일관되게 한다.

## 4. 실습 및 과제

### 실습 A — 자기 프로젝트 완성

1. JSP 또는 Spring 기존 프로젝트를 복사하고 수정 전 상태를 커밋한다.
2. 목록·등록·수정·삭제·검색을 직접 눌러 본다. 없는 기능은 '해당 없음'과 이유를 적는다.
3. data/defects.csv에 미완성 기능 3개를 기록한다. P0 또는 P1을 먼저 선정한다.
4. 입력 → Controller/Servlet → DAO/Repository → DB → 출력 순서로 로그와 데이터를 확인한다.
5. 기능 1개 이상을 고친다. 단순 화면 색상 변경은 기능 보완으로 세지 않는다.
6. 정상 입력·공백·긴 입력·없는 번호·새로고침을 다시 확인한다.
7. 수정 전후 증거와 실행 설명서를 작성한다.

### 실습 B — 별도 워크북

하위 '워크북 — 학습 과제 관리 웹 완성'을 따라 독립 실행 프로젝트를 완성한다. 수정·삭제·검색·검증이 이미 있는 학생은 워크북의 검증표를 자기 프로젝트에 적용한다. 워크북 본문에는 필요한 모든 파일의 전체 코드와 SQL이 있다.

### 제출물 및 채점 기준(실습용 제안)

아래 배점은 보강 실습 피드백용 제안이며 강의계획서의 공식 성적 배점을 변경하지 않는다.

Week15_학번_성명.zip 안에 소스 프로젝트, SQL, README.md, defects.csv, test-results.csv, 수정 전후 화면 및 시연 자료를 넣는다. target·개인 암호·실제 회원 개인정보는 제외한다. 제출 위치·마감은 교수자 공지를 따른다.

| 항목 | 배점 | 확인 기준 |
| --- | --- | --- |
| 기능 완성 | 35 | 선정 기능 동작, DB 반영, 흐름 유지 |
| 오류 및 입력 처리 | 25 | 공백·길이·잘못된 값·없는 대상 처리 |
| 검증 근거 | 20 | 입력·예상·실제·통과 여부 기록 |
| 재현 가능한 제출 | 15 | SQL·환경·실행 URL·소스 완비 |
| 개선 설명 | 5 | 원인과 수정 이유를 설명 |

## 5. 평가문제(객관식 및 단답형)

### 객관식 — 각 문항에서 하나 선택

1. 프로젝트가 실행되지 않고 삭제 확인창도 없다. 먼저 할 일은? ① 색상 변경 ② 실행 실패 해결 ③ 아이콘 교체 ④ 애니메이션 추가
2. 브라우저 required만으로 부족한 이유는? ① DB가 느려짐 ② POST 불가 ③ 요청을 변조할 수 있음 ④ 한글 금지
3. SQL에 입력을 전달하는 올바른 방식은? ① 입력을 문자열에 더함 ② ? 바인딩 ③ 태그 제거만 함 ④ 암호화 후 더함
4. 저장 성공 후 PRG의 마지막 요청은? ① GET ② POST ③ PUT ④ DELETE
5. 일반 텍스트를 안전하게 표시하는 Thymeleaf 속성은? ① th:utext ② th:raw ③ th:html ④ th:text
6. @Valid 검증 결과를 받는 인자는? ① Model만 ② BindingResult ③ HttpStatus만 ④ JdbcTemplate
7. 삭제를 GET으로 구현하지 않는 이유는? ① GET은 변경 없이 조회하도록 설계하기 때문 ② GET은 숫자 불가 ③ POST는 항상 암호화 ④ JDBC가 요구
8. 존재하지 않는 수정 대상에 적합한 응답은? ① 200 ② 302 ③ 404 ④ 201
9. 여러 DB 변경을 하나의 작업 단위로 묶는 기능은? ① CSS ② 트랜잭션 ③ 폰트 ④ 포트
10. 다른 PC에서 실행하기 위해 가장 필요한 자료는? ① 화면 사진만 ② 소스·SQL·환경·실행 절차 ③ IDE 배경색 ④ 브라우저 즐겨찾기

### 단답형

1. 결함 재현 기록에 반드시 포함할 세 가지 정보를 쓰시오.
2. PRG의 전체 이름을 쓰시오.
3. 제목을 공백만 입력했을 때 서버가 해야 할 동작을 설명하시오.
4. JSP에서 사용자 입력을 HTML 이스케이프하여 출력하는 JSTL 태그를 쓰시오.
5. '목록 화면은 나오지만 DB 재시작 후 데이터가 사라진다'면 완성으로 볼 수 있는가? 이유와 점검 대상을 쓰시오.

### 정답 및 해설 — 복습용

객관식: 1②(기동이 선행), 2③(서버 검증 필요), 3②(입력과 SQL 분리), 4①(redirect 후 조회), 5④(이스케이프), 6②(검증 오류 수신), 7①(조회와 변경 구분), 8③(대상 없음), 9②(원자적 작업), 10②(재현에 필요한 정보).

단답형: 1) 재현 절차·예상 결과·실제 결과. 2) Post/Redirect/Get. 3) 저장하지 않고 기존 입력 및 오류 안내로 폼을 다시 표시. 4) c:out. 5) 지속 저장 요구가 있다면 미완성이다. 실제 DB URL·메모리 DB 사용 여부·INSERT 반영·트랜잭션 커밋 등을 점검한다.

## 6. 학습요약

- 완성 기준을 기능·오류 처리·데이터 유지·재실행 가능성으로 정의한다.
- 미완성 목록을 기록하고 P0/P1부터 고친다.
- 입력 검증, SQL 바인딩, 출력 이스케이프, POST 변경 요청을 함께 확인한다.
- 수정 뒤 정상값뿐 아니라 실패·경계값도 검증한다.
- 소스·SQL·실행 설명서·검증 기록을 묶어 프로젝트를 완성한다.

### 참고 및 연결

강의계획서 이미지에서 확인한 15주 범위는 '보강주간: JSP/Spring 미완성 기능 보완 및 프로그램 완성'이며, 사용자가 지정한 Lect15 학습내용과 일치한다. 추가 과제 관리 웹은 이를 연습하는 보충 예제이다.

- [Spring Boot 3.5 시스템 요구사항](https://docs.spring.io/spring-boot/3.5/system-requirements.html)
- [Spring JDBC 사용 방식](https://docs.spring.io/spring-framework/reference/6.2/data-access/jdbc/choose-style.html)
- [Spring MVC 폼 검증](https://spring.io/guides/gs/validating-form-input/)
