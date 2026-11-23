## 개발환경

Windows 10 / Eclipse IDE for Enterprise Java and Web Developers 2025-09 / JDK 21 / Tomcat 10.1.x / Servlet 6.0 / JSP 3.1 / UTF-8. MySQL Community Server 8.0.46·Workbench 8은 공통 준비 환경이지만 이 보충 프로젝트는 DB 없이 실행합니다.

**학습 질문: JSP 화면이 요청을 읽고, 다른 JSP를 포함하고, 응답 코드를 바꾸는 과정은 기존 Servlet 로그인 흐름과 어떻게 연결될까요?**

기존 10주차 강의자료의 Servlet·Session 로그인 설명을 보완하는 별도 학습 모음입니다. 다섯 참고 글의 주제를 한국어로 재구성하고, 실제 코드는 현재 Tomcat 환경과 공식 API에 맞춰 작성했습니다. 참고 글의 코드를 그대로 번역·복제한 자료가 아닙니다.

## 학습 순서

| 순서 | 주제 | 만들어 볼 결과 | 권장 시간 |
|---|---|---|---|
| 1 | JSP Actions | 공통 배너·Bean·Forward 비교 | 25분 |
| 2 | 내장 객체 | 범위별 값과 오류 페이지 | 25분 |
| 3 | Client Request | 요청·헤더 관찰표 | 15분 |
| 4 | HTTP 상태 코드 | 200·302·400·403·404·405·500 | 20분 |
| 5 | Form Processing | 한글 신청 폼·다중 선택·검증 | 25분 |

총 110분의 보충·자율실습 분량입니다. 기존 180분 수업안에 그대로 더해 필수 시간으로 지정하지 않습니다. 수업 중 요청·폼·상태 코드를 먼저 시연하고 Actions·내장 객체는 후속 실습으로 배분할 수 있습니다.

## 기존 강의와 연결

| 보충 실습 | 기존 구현과 관계 |
|---|---|
| jsp:forward | RequestDispatcher.forward와 같은 내부 전달 관찰 |
| request / session 내장 객체 | 기존 loginUser 저장·읽기와 연결 |
| request.jsp | EchoServlet의 요청 관찰을 JSP에서 먼저 확인 |
| 상태 코드 실습 | 기존 로그인 성공 302→200, CSRF 403, GET logout 405 분석 |
| 폼 처리 | LoginServlet·EchoServlet의 Parameter 처리를 비교 |

JSP 스크립틀릿은 문법과 객체를 확인하는 학습 단계입니다. 기존 Servlet Controller + JSP View 구조는 그대로 사용하며 DB·인증 로직을 JSP에 옮기는 과제가 아닙니다.

## 참고 글을 읽을 때 보완할 부분

| 참고 글의 표현·주제 | 이 수업의 기준 |
|---|---|
| javax.servlet 계열 | Tomcat 10.1은 jakarta.servlet 계열 |
| out을 PrintWriter로 소개 | 정확한 JSP 변수 타입은 JspWriter |
| Exception 대문자 표기 | 변수명은 exception, 타입 Throwable, 오류 JSP에서만 제공 |
| 모든 액션에 id/scope 공통 | 액션별 허용 속성 구분, useBean에서 id/scope 사용 |
| GET 1024자 고정 제한 | 제품·서버 설정에 따라 다름 |
| Query string을 헤더처럼 설명 | URL의 query와 HTTP 헤더 구별 |
| Content-Length는 POST 전용 | 본문 길이 정보이며 POST에만 한정되지 않음 |
| Java 브라우저 플러그인 | 과거 방식으로 설명, 현대 브라우저 실행 과제 제외 |

공식 기준: [JSP 3.1 명세](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1), [Servlet 요청 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html), [Servlet 응답 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html).

## 먼저 하기 — Eclipse 프로젝트 만들기

1. 제공 완성본은 File → Import → Maven → Existing Maven Projects에서 `Week10/jsp-foundations`를 선택합니다.
2. 직접 만들 때는 File → New → Maven Project → Create a simple project → Group Id edu.week10 / Artifact Id jsp-foundations / Packaging war / Version 1.0.0으로 만듭니다.
3. 아래 pom.xml로 교체합니다. 프로젝트 우클릭 → Maven → Update Project를 실행합니다.
4. Java Compiler 21, Project Facets의 Java 21·Dynamic Web Module 6.0, Target runtime Tomcat 10.1, Web Project Settings의 Context root jsp-foundations를 확인합니다.
5. 아래 공통 파일을 작성하고 하위 5개 페이지의 전체 코드를 순서대로 입력합니다. JavaBean은 src/main/java, JSP는 src/main/webapp 아래입니다.
6. Run As → Run on Server → Tomcat 10.1. 시작 주소는 `http://localhost:8080/jsp-foundations/index.jsp`입니다.
7. Maven으로 패키징할 때는 프로젝트 폴더에서 `mvn clean package` 후 target/jsp-foundations.war를 Tomcat webapps에 배포할 수 있습니다. Eclipse 배포와 수동 실행은 하나만 사용합니다.

### 공통 파일의 역할

pom.xml은 Java 21과 WAR 설정입니다. web.xml은 공통 초기화 값과 세션 설정입니다. common.jspf는 사용자 입력을 HTML 텍스트로 안전하게 표시하는 교육용 함수입니다. JSTL 등 외부 라이브러리는 이 독립 프로젝트에서 사용하지 않습니다. esc는 URL·JavaScript 문자열 등 모든 출력 문맥을 포괄하는 범용 보안 함수가 아닙니다.

### `pom.xml` — 전체 코드

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>edu.week10</groupId>
    <artifactId>jsp-foundations</artifactId>
    <version>1.0.0</version>
    <packaging>war</packaging>
    <properties>
        <maven.compiler.release>21</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <build>
        <finalName>jsp-foundations</finalName>
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

### `src/main/webapp/WEB-INF/web.xml` — 전체 코드

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee" version="6.0">
    <context-param>
        <param-name>courseTitle</param-name>
        <param-value>JAVA 응용 10주차</param-value>
    </context-param>
    <session-config>
        <session-timeout>20</session-timeout>
        <cookie-config><http-only>true</http-only></cookie-config>
        <tracking-mode>COOKIE</tracking-mode>
    </session-config>
</web-app>
```

### `src/main/webapp/WEB-INF/common.jspf` — 전체 코드

```html
<%@ page pageEncoding="UTF-8" %>
<%!
    // HTML 텍스트·따옴표로 감싼 속성 출력에 사용하는 교육용 함수입니다.
    private String esc(Object value) {
        if (value == null) {
            return "(없음)";
        }
        return value.toString()
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>
```

### `src/main/webapp/index.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>JSP 기초 보충 실습</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>JSP 기초 보충 실습</h1>
    <ol>
        <li><a href="actions.jsp">Actions와 JavaBean</a></li>
        <li><a href="implicit.jsp">내장 객체와 범위</a></li>
        <li><a href="request.jsp?name=홍길동">Client Request</a></li>
        <li><a href="status.jsp?code=200">HTTP 상태 코드</a></li>
        <li><a href="forms.jsp">폼 처리: GET/POST/다중 선택</a></li>
    </ol>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

## 결과 기록과 문제 해결

각 페이지의 예상 결과를 확인하고 URL·입력·실제 표시·HTTP Status·설명으로 보고서를 작성합니다. 코드 줄바꿈을 유지하고 전체 파일을 복사합니다.

| 증상 | 확인 |
|---|---|
| 404 | 컨텍스트 jsp-foundations, 서버 배포·URL·파일 위치 |
| StudentBean을 찾을 수 없음 | src/main/java 패키지 edu.week10, Maven Update, Java 21 |
| JSP 500 | 서버 로그의 첫 원인 확인, 따옴표·세미콜론·선언 위치 |
| 의도한 오류 500 | status.jsp?code=500과 error-demo.jsp?fail=yes는 정상 실험 결과 |
| 한글 깨짐 | JSP pageEncoding UTF-8, POST 읽기 전 setCharacterEncoding |
| request 값이 없어짐 | 링크 클릭/Redirect는 새 요청인지 확인 |

통합 과제: 학습 신청 폼에 단일 선택 과목을 추가하고 name·values·검증·안전한 출력·정상/오류 응답을 표로 설명합니다. 회원가입·DB 저장 기능은 요구하지 않습니다.

## 파일로 보는 세부 학습자료

- [01 Actions](01-actions.md)
- [02 내장 객체](02-implicit-objects.md)
- [03 Client Request](03-client-request.md)
- [04 HTTP 상태 코드](04-http-status.md)
- [05 Form Processing](05-form-processing.md)
- [실행 검증 범위](verification.md)
