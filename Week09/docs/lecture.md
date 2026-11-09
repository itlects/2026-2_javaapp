## 공통 개발환경 — 실습 시작 전 확인

이 강의의 개발환경은 다음 기준으로 통일합니다. 예제의 메뉴, 프로젝트 경로와 실행 방법은 Windows 10과 Eclipse 2025-09를 기준으로 설명합니다.

| 구분 | 수업 기준 | 확인 항목 |
|---|---|---|
| 운영체제 | Windows 10 64비트 | 설정 → 시스템 → 정보 |
| IDE | Eclipse IDE for Enterprise Java and Web Developers 2025-09 (2025.9) | Help → About Eclipse IDE |
| Java | JDK 21 | java -version / javac -version |
| 웹 실행 서버 | Apache Tomcat 10.1.x | Eclipse Runtime에 10.1 등록 |
| DB 서버 | MySQL Community Server 8.0.46 | SELECT VERSION(); |
| DB 관리 도구 | MySQL Workbench 8.0 계열 | Help → About MySQL Workbench |
| 문자 인코딩 | UTF-8 | Eclipse Workspace·JSP 파일·응답 설정 |
| 웹 프로젝트 | jsp-login | src/main/webapp / Context root: jsp-login |
| 포트 | Tomcat 8080 / MySQL 3306 | 두 프로그램의 포트는 서로 다름 |

### ① JDK와 Eclipse 설정

1. JDK 21을 설치하고 JAVA_HOME을 JDK 설치 폴더로 지정합니다. 예: `C:\Java\jdk-21`. Path에 `%JAVA_HOME%\bin`을 추가하고 새 명령 프롬프트에서 아래를 확인합니다.

```bat
java -version
javac -version
echo %JAVA_HOME%
```

2. Eclipse **2025-09 Enterprise Java and Web Developers** 패키지를 사용합니다. 기존 Java Developers 패키지에서 Dynamic Web Project가 보이지 않으면 웹 개발 도구가 포함된 Enterprise 패키지를 사용합니다.
3. Window → Preferences → Java → Installed JREs → Add → Standard VM에서 JDK 21 폴더를 등록하고 기본값으로 선택합니다. 프로젝트 Properties → Java Compiler의 Compiler compliance level도 21로 맞춥니다.
4. Window → Preferences → General → Workspace → Text file encoding을 UTF-8로 지정합니다. JSP 파일 첫 줄의 pageEncoding과 contentType charset도 UTF-8을 유지합니다.

### ② Tomcat 등록과 JSP 프로젝트 실행

1. Tomcat 10.1.x Core ZIP을 압축 해제합니다. 예: `C:\tools\tomcat`. 해당 폴더 바로 아래에 bin, conf, webapps가 있어야 합니다.
2. Eclipse의 Window → Preferences → Server → Runtime Environments → Add → Apache Tomcat v10.1에서 설치 폴더와 JDK 21을 지정합니다.
3. File → New → Dynamic Web Project에서 Project name `jsp-login`, Target runtime Tomcat v10.1, Dynamic web module 6.0을 선택합니다. Next → Web Module에서 Content directory를 `src/main/webapp`으로 지정합니다.
4. 제공한 `Week09/jsp-login` 폴더의 **내용물**을 프로젝트의 `src/main/webapp`에 복사합니다. 예: `src/main/webapp/login.jsp`, `src/main/webapp/WEB-INF/web.xml`. jsp-login 폴더를 한 겹 더 넣지 않습니다.
5. 프로젝트 Properties → Web Project Settings → Context root를 `jsp-login`으로 맞춥니다. Properties → Targeted Runtimes에서 Tomcat 10.1이 선택되어 있는지도 확인합니다.
6. Window → Show View → Other → Server → Servers로 Servers 뷰를 엽니다. 프로젝트 우클릭 → Run As → Run on Server → Tomcat v10.1을 선택합니다.
7. 브라우저에서 `http://localhost:8080/jsp-login/`을 엽니다. 홈 → 로그인 → 회원 화면 → 로그아웃 순으로 확인합니다. 실습 계정은 `student / Java1234!`입니다.
8. JSP 파일은 더블클릭하거나 Live Server로 실행하지 않습니다. Eclipse 서버를 사용할 때 별도 startup.bat로 Tomcat을 중복 실행하지 않습니다. 8080 포트가 사용 중이면 다른 Tomcat을 종료한 뒤 다시 실행합니다.

### ③ MySQL과 Workbench 확인

1. MySQL Community Server **8.0.46**과 MySQL Workbench **8.0 계열**을 사용합니다. Workbench는 관리·접속 프로그램이며 DB 서버와 별도로 실행됩니다.
2. Windows의 서비스 앱에서 설치한 MySQL 서비스가 실행 중인지 확인합니다. 기본 서비스명은 보통 MySQL80이며 설치 시 지정한 이름을 사용합니다.
3. Workbench의 MySQL Connections 옆 +에서 연결을 만듭니다. Connection Method: Standard (TCP/IP), Hostname: `127.0.0.1`, Port: `3306`, Username: 설치 시 설정한 계정. 비밀번호는 본인 설치값을 사용하고 교재 코드에 기록하지 않습니다.
4. Test Connection 성공 후 SQL Editor에서 다음을 실행합니다. version이 8.0.46인지 확인합니다. Workbench의 버전은 Help → About에서 따로 확인합니다.

```sql
SELECT VERSION() AS server_version;
SELECT @@port AS server_port;
```

**이번 9주차 예제는 고정 실습 계정으로 JSP·세션 흐름을 학습하므로 DB 연결·테이블 생성·JDBC JAR가 필요하지 않습니다.** MySQL은 공통 수업 환경으로 준비합니다. DB 연동 예제에서는 Connector/J 버전·배치 위치·SQL 초기화·접속 계정·JDBC URL을 해당 예제 앞에 별도로 명시합니다. Workbench 설치만으로 JSP에서 DB 연결이 되는 것은 아닙니다.

### 실행 전 점검

- JDK 21 / Tomcat 10.1 / Dynamic Web Module 6.0을 동일하게 지정했는가?
- 소스 파일이 src/main/webapp 아래에 있고 컨텍스트가 jsp-login인가?
- Tomcat 서버는 하나만 실행 중이며 http://localhost:8080/jsp-login/으로 접속했는가?
- JSP는 UTF-8이며 Tomcat 9의 javax 예제와 Tomcat 10.1의 jakarta 예제를 혼용하지 않았는가?

설치 참고: [Eclipse 2025-09 Enterprise 패키지](https://www.eclipse.org/downloads/packages/release/2025-09/r/eclipse-ide-enterprise-java-and-web-developers), [Tomcat 10.1 설치](https://tomcat.apache.org/tomcat-10.1-doc/setup.html), [Workbench 연결 설정](https://dev.mysql.com/doc/workbench/en/wb-getting-started-tutorial-create-connection.html).


수업 주제: JSP 웹 개발환경 구축과 로그인 프로그램 구현. 사용자가 제시한 강의계획서 9주차 항목을 모두 반영했습니다. 별도 DB 설치 없이 WEB의 요청·응답 원리부터 로그인 상태 관리까지 연결합니다.

| 목차 | 학습 내용 | 권장 시간 |
|---|---|---|
| 1. 학습목표 | 완성 결과와 목표 확인 | 10분 |
| 2. 이론 | WEB·Tomcat·HTML Form·JSP 객체 | 35분 |
| 3. 예제 | 환경 구축·동적 페이지·폼 처리 | 45분 |
| 4. 실습 및 과제 | 학습 포털 로그인 워크북 | 65분 |
| 5. 평가문제 | 객관식·단답형 | 15분 |
| 6. 학습요약 | 흐름 설명·자기 점검 | 10분 |

## 1. 학습목표

- JDK와 Tomcat을 설치하고 HTTP URL로 JSP를 실행한다.
- 브라우저·웹서버·서블릿 컨테이너의 역할과 Tomcat 폴더 구조를 설명한다.
- HTML Form의 action, method, name을 사용하여 요청 데이터를 전달한다.
- JSP 지시자·스크립틀릿·표현식과 request, response, session을 구분한다.
- 성공·실패·직접 접근·로그아웃을 검증하는 로그인 웹사이트를 완성한다.

완성 결과: 홈 화면 → 로그인 화면 → 회원 학습 현황 → 로그아웃. 미로그인 상태에서 회원 페이지를 직접 열면 로그인 화면으로 이동한다.

## 2. 이론

### 2.1 WEB과 Tomcat의 역할

브라우저는 HTTP 요청을 보내고 서버는 상태 코드·헤더·본문으로 응답합니다. 정적 HTML은 저장된 내용을 전달하고, JSP는 서버에서 Java로 처리한 결과 HTML을 생성합니다. 브라우저는 JSP 원본 Java 코드를 실행하지 않습니다. Tomcat은 HTTP 요청을 받아 정적 파일도 제공하고 Servlet/JSP를 실행하는 컨테이너입니다. 별도의 Apache HTTP Server가 없어도 이번 실습을 실행할 수 있습니다.

JSP 첫 요청 또는 변경 후: JSP → Jasper가 Servlet Java 소스로 변환 → 컴파일 → 실행 → HTML 응답. 이후에는 컴파일된 클래스를 재사용합니다.

| 주소 구성 | 예 | 의미 |
|---|---|---|
| scheme | http | 통신 방식 |
| host:port | localhost:8080 | 로컬 서버와 HTTP 포트 |
| context path | /jsp-login | 웹 애플리케이션 식별 |
| resource | /examples/hello.jsp | 앱 내부 자원 |

| Tomcat 폴더 | 역할 |
|---|---|
| bin | 시작·종료 스크립트 |
| conf | server.xml 등 서버 설정 |
| webapps | 웹 앱 배포 위치 |
| work | JSP 변환·컴파일 작업 결과 |
| logs | 시작·요청 처리 오류 확인 |
| lib | 컨테이너 공통 라이브러리 |

`WEB-INF/web.xml`은 앱 설정, `WEB-INF/classes`는 Java 클래스, `WEB-INF/lib`는 앱의 JAR 위치입니다. WEB-INF 아래는 브라우저로 직접 접근할 수 없습니다. 보통 HTML·CSS·JSP는 웹 루트에 두되 보호할 JSP 조각은 WEB-INF에 둡니다.

### 2.2 HTML과 Form

HTML의 head는 메타데이터, body는 화면 내용입니다. form의 action은 처리 URL, method는 HTTP 메서드입니다. input의 name이 서버가 읽는 매개변수 키입니다. id는 label·CSS·JavaScript 연결에 쓰며 name을 대신하지 않습니다. password 입력형은 화면만 가리므로 암호화 전송을 보장하지 않습니다.

| 구분 | GET | POST |
|---|---|---|
| 폼 값 위치 | URL 쿼리 문자열 | 요청 본문 |
| 주 용도 | 조회·검색 | 로그인·등록 등 처리 |
| URL 노출 | 매개변수가 보임 | 주소창에 폼 값이 안 보임 |
| 기밀성 | HTTPS 필요 | POST도 HTTPS 필요 |

브라우저의 required와 maxlength만 믿을 수 없습니다. 서버에서도 null, 길이, 허용 값, HTTP 메서드를 검사해야 합니다.

### 2.3 JSP 기본 문법과 내장 객체

| 문법·객체 | 예 | 역할 |
|---|---|---|
| 지시자 | `<%@ page ... %>` | 인코딩·import 등 페이지 설정 |
| 스크립틀릿 | `<% ... %>` | 요청 처리 Java 실행 |
| 표현식 | `<%= 1 + 2 %>` | 계산 결과 출력, 세미콜론 없음 |
| 선언 | `<%! ... %>` | 메서드·필드 선언, 공유 가변 필드 주의 |
| JSP 주석 | `<%-- ... --%>` | 응답 HTML에 포함하지 않는 주석 |
| request | `request.getParameter("name")` | 요청 매개변수 읽기, 반환은 String 또는 null |
| response | `response.sendRedirect(...)` | 상태·헤더·이동 응답 구성 |
| session | `session.setAttribute(...)` | 여러 요청에 걸친 로그인 상태 |
| out | `out.print(...)` | 응답 본문 출력 |

`pageEncoding`은 JSP 파일 인코딩, `contentType`의 charset은 응답 인코딩, `request.setCharacterEncoding("UTF-8")`은 POST 본문 해석을 설정합니다. POST 매개변수를 읽기 **전**에 호출합니다. 요청 범위 속성은 현재 요청에서, 세션 속성은 같은 세션의 후속 요청에서도 사용할 수 있습니다. 기본적으로 브라우저가 JSESSIONID 쿠키를 보내 세션을 식별합니다.

### 2.4 Redirect·Forward·로그인

`sendRedirect`는 보통 302 응답을 보내 브라우저가 새 요청을 하므로 URL이 바뀌고 request 속성이 유지되지 않습니다. `request.getRequestDispatcher(...).forward(request,response)`는 서버 안에서 같은 요청을 넘겨 request 속성을 유지하고 주소창은 유지합니다. 둘 다 응답이 커밋되기 전에 사용합니다. 이동 뒤에는 `return`으로 나머지 처리를 끝냅니다.

로그인 처리 순서: POST인지 확인 → 값 읽기 → 계정 검증 → 세션 ID 갱신 → 로그인 정보 저장 → 회원 페이지로 redirect. 회원 페이지는 화면 출력 전 세션을 검사합니다. 로그아웃은 세션을 invalidate합니다. 이 수업은 원리 학습을 위해 JSP 스크립틀릿을 사용합니다. 향후에는 Servlet 컨트롤러·서비스·JSP 뷰로 역할을 분리합니다.

사용자 입력을 그대로 HTML로 출력하면 XSS 위험이 있습니다. 폼 결과 예제는 HTML 이스케이프를 적용합니다. 로그인 실패 메시지는 아이디와 비밀번호 중 어느 쪽이 틀렸는지 구분하지 않습니다. 실제 운영은 HTTPS, DB와 비밀번호 해시, CSRF 방어, 시도 제한 등을 갖춰야 합니다.

## 3. 예제

### 실습 환경 및 실행 위치

페이지 상단의 **공통 개발환경** 설정을 먼저 완료합니다. Windows 10 / Eclipse 2025-09에서 진행하며, 예제 경로는 웹 루트인 `src/main/webapp` 기준입니다. 제공 소스의 `jsp-login` 내용물을 이 위치에 복사하고 Run As → Run on Server로 실행합니다. 웹 루트에 examples와 WEB-INF/jspf 폴더를 만듭니다.

실행 URL: `http://localhost:8080/jsp-login/`. JSP를 파일로 직접 열지 않습니다. 이번 로그인 예제는 DB 없이 실행되며 MySQL은 공통 환경 준비 항목입니다.

### 예제 1 — 첫 동적 페이지

`jsp-login/examples/hello.jsp`를 만들고 아래 전체 코드를 저장합니다. `http://localhost:8080/jsp-login/examples/hello.jsp`에 접속해 2초 뒤 새로 고칩니다. 서버 시각이 변하고 계산값 3이 보이면 성공입니다. 페이지 소스 보기에서는 Java 코드가 보이지 않는지 확인합니다.

파일: `examples/hello.jsp`

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.time.LocalDateTime,java.time.format.DateTimeFormatter" %>
<!doctype html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>동적 페이지</title>
    </head>
    <body>
        <h1>서버에서 생성한 페이지</h1>
        <p>서버 시각:<%= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %></p>
        <p>1 + 2 =<%= 1 + 2 %></p>
        <a href="../index.jsp">홈으로</a>
    </body>
</html>
```

### 예제 2 — HTML Form과 request·response

`examples/form.jsp`, `examples/result.jsp`를 다음 순서로 만듭니다. form.jsp에서 이름 `홍길동`, 분야 `JSP`를 전송합니다. 결과 화면에 한글과 POST가 표시되어야 합니다. 개발자 도구 Network에서 요청 URL·메서드·Form Data·응답 상태를 확인하세요. 처리 페이지를 주소창에서 직접 열면 GET이므로 405입니다.

파일: `examples/form.jsp`

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>폼 입력</title>
    </head>
    <body>
        <h1>이름과 관심 분야 전송</h1>
        <form action="result.jsp" method="post">
            <label>
                이름
                <input name="name" required maxlength="30">
            </label>
            <label>
                관심 분야
                <select name="topic">
                    <option value="JSP">JSP</option>
                    <option value="Java">Java</option>
                </select>
            </label>
            <button>전송</button>
        </form>
        <a href="../index.jsp">홈으로</a>
    </body>
</html>
```

파일: `examples/result.jsp`

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                    .replace("\"", "&quot;").replace("'", "&#39;");
    }
%>
<%
    request.setCharacterEncoding("UTF-8");
    if (!"POST".equals(request.getMethod())) {
        response.sendError(405);
        return;
    }
    String name = request.getParameter("name");
    String topic = request.getParameter("topic");
    if (name == null || name.isBlank() || name.length() > 30 ||
        !("JSP".equals(topic) || "Java".equals(topic))) {
        response.sendError(400);
        return;
    }
%>
<!doctype html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>요청 결과</title>
    </head>
    <body>
        <h1>요청 결과</h1>
        <p>이름:<%= escapeHtml(name) %></p>
        <p>관심 분야:<%= escapeHtml(topic) %></p>
        <p>메서드:<%= request.getMethod() %></p>
        <a href="form.jsp">다시 입력</a>
    </body>
</html>
```

관찰 질문: input의 name을 `studentName`으로 바꾸고 서버 키는 그대로 두면 왜 400이 되는가? required를 개발자 도구로 지워 빈 값을 보내도 서버 검사가 작동하는가? 이름에 `<b>홍길동</b>`를 넣으면 굵은 글씨가 아닌 문자열로 보이는가?

## 4. 실습 및 과제

별도 워크북의 단계대로 실용 예제인 **JAVA 학습 포털**을 완성합니다. 모든 파일의 전체 코드, 작성 순서, URL, 예상 화면, 검사 항목을 제공합니다.

1. 기본 과제: 홈·로그인·회원 페이지·로그아웃을 구현하고 성공·실패·직접 접근을 검증합니다.
2. 응용 과제: dashboard에 고정 학습 항목 2개를 추가하고 제목과 CSS 색상을 변경합니다. 인증 검사 위치를 유지합니다.
3. 탐구 과제: form.jsp의 name 제거 실험과 로그아웃 후 재접근 결과를 설명합니다.

제출: 직접 작성한 프로젝트 ZIP, 성공/실패/미로그인 접근/로그아웃 후 재접근 화면 4장, 실습 결과표, 300자 이상의 request·response·session 역할 설명. 실제 비밀번호·세션 쿠키는 제출하지 않습니다. 제출 경로와 기한은 교수자 안내를 따릅니다.

| 채점 항목 | 배점 | 확인 증거 |
|---|---|---|
| 환경과 동적 페이지 | 20 | URL·서버 시각 실행 화면 |
| 폼·한글·입력 검증 | 20 | 정상 입력과 잘못된 요청 결과 |
| 로그인·실패 처리 | 25 | 성공과 실패 화면 |
| 세션 보호·로그아웃 | 25 | 직접 접근과 로그아웃 후 재접근 |
| 보고서·코드 설명 | 10 | 관찰 결과와 설명 |

## 5. 평가문제 (객관식 및 단답형)

### 객관식 (각 5점)

1. JSP를 Servlet으로 변환·컴파일하여 실행하는 위치는? ① 브라우저 ② Tomcat 서버 ③ CSS 엔진 ④ DNS 서버
2. Tomcat의 기본 배포 폴더는? ① logs ② conf ③ webapps ④ bin
3. form 데이터를 서버에서 구분하는 입력 요소 속성은? ① id ② class ③ style ④ name
4. request.getParameter의 반환형은? ① String ② int ③ Boolean ④ Session
5. POST 한글 인코딩은 언제 설정해야 하는가? ① 매개변수 읽기 전 ② 읽은 후 ③ 응답 완료 후 ④ 브라우저 종료 후
6. sendRedirect에 대한 설명으로 맞는 것은? ① 항상 같은 요청 사용 ② 브라우저가 새 요청 ③ 세션 삭제 ④ JSP 원문 전송
7. 후속 요청에서도 로그인 정보를 유지하는 데 적합한 것은? ① 지역 변수 ② request만 ③ session ④ HTML 주석
8. 로그아웃에 사용하는 메서드는? ① session.invalidate() ② request.close() ③ out.login() ④ response.session()
9. POST이면 HTTPS 없이도 비밀번호가 암호화된다는 설명은? ① 맞다 ② 틀리다 ③ Tomcat에서만 맞다 ④ required가 있으면 맞다
10. WEB-INF에 대한 설명으로 맞는 것은? ① 브라우저 직접 접근 허용 ② 항상 CSS 전용 ③ 브라우저 직접 접근 차단 ④ URL 포트 설정 위치

### 단답형 (각 10점)

11. 아이디 입력 `name="userId"`의 값을 서버에서 읽는 한 줄을 쓰시오.
12. UTF-8 JSP 응답과 소스 인코딩을 설정하는 page 지시자를 쓰시오.
13. 보호된 화면 출력 전에 세션을 검사해야 하는 이유를 쓰시오.
14. 로그인 성공 시 세션 ID를 바꾸는 메서드와 목적을 쓰시오.
15. GET·POST 차이 한 가지와 POST만으로 기밀성이 확보되지 않는 이유를 쓰시오.

객관식 50점 + 단답형 50점. 형성평가용이며 위 실습 과제 배점과 별개입니다. 정답과 해설은 별도 해설 페이지에서 확인합니다.

## 6. 학습요약

- 브라우저는 요청, Tomcat은 JSP 실행, response는 결과 HTML을 전달합니다.
- Form의 action·method·name이 전송 위치·방식·키를 정합니다.
- request는 입력, response는 응답과 이동, session은 후속 요청의 로그인 상태입니다.
- 로그인 성공·실패뿐 아니라 미로그인 직접 접근과 로그아웃 후 접근도 확인합니다.
- JSP 스크립틀릿의 실행 위치는 서버입니다. 비밀번호 입력형·POST 자체는 암호화가 아닙니다.

자기 점검: URL의 context path 설명 / 세 가지 인코딩 설정 구분 / redirect 후 request 속성 소실 설명 / 회원 화면 세션 검사 / HTML 이스케이프 이유.

공식 참고: [Tomcat 10.1 설치](https://tomcat.apache.org/tomcat-10.1-doc/setup.html), [Jasper JSP 엔진](https://tomcat.apache.org/tomcat-10.1-doc/jasper-howto.html), [애플리케이션 배포](https://tomcat.apache.org/tomcat-10.1-doc/deployer-howto.html), [Servlet API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/index.html). Tomcat 10.1은 Java 11 이상과 Jakarta Servlet 6.0·Pages 3.1 계열을 사용하며 수업 기준 JDK는 21입니다.
