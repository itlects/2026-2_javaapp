## 개발환경과 시작 위치

Windows 10 / Eclipse Enterprise 2025-09 / JDK 21 / Tomcat 10.1.x / JSP 3.1 / UTF-8. MySQL 8.0.46·Workbench 8은 기존 공통 환경이며 이 보충 실습에서는 DB를 사용하지 않습니다. 프로젝트명·Context root는 `jsp-foundations`입니다. 먼저 상위 **JSP 기초 보충 학습**의 환경 설정과 공통 파일을 작성하세요.

JSP 스크립틀릿은 내장 객체와 요청 흐름을 눈으로 확인하기 위한 교육용입니다. 기존 로그인 프로젝트는 Servlet에서 처리하고 JSP는 View로 사용하는 구조를 유지합니다. 두 방식을 같은 기능을 관찰하는 서로 다른 단계로 비교합니다.

## 1. 학습목표

학습 질문: **new로 만들지 않은 request·session을 JSP에서 바로 쓰는 이유는? 새 페이지로 이동했을 때 어떤 값이 남을까?**

9개 내장 객체의 역할과 page/request/session/application 범위를 구분합니다.

## 2. 이론

아래 타입은 Tomcat 10.1의 HTTP JSP 기준입니다. 참조 글의 javax 계열 표기는 수업에서 jakarta 계열로 읽습니다.

| 내장 객체 | 타입 | 역할·조건 |
|---|---|---|
| request | jakarta.servlet.http.HttpServletRequest | 요청 메서드·파라미터·속성 |
| response | jakarta.servlet.http.HttpServletResponse | 상태·헤더·리다이렉트 |
| out | jakarta.servlet.jsp.JspWriter | JSP 응답 출력, PrintWriter와 구분 |
| session | jakarta.servlet.http.HttpSession | 같은 세션의 상태, session="false" JSP에서는 사용 불가 |
| application | jakarta.servlet.ServletContext | 웹앱 공통 초기화 값·공유 속성 |
| config | jakarta.servlet.ServletConfig | 현재 JSP Servlet의 설정 |
| pageContext | jakarta.servlet.jsp.PageContext | JSP 실행 문맥과 범위별 속성 접근 |
| page | java.lang.Object | 현재 JSP 구현 인스턴스, Java에서 this |
| exception | java.lang.Throwable | isErrorPage="true"인 오류 페이지에서 사용 |

내장 객체 변수의 접근 가능성과 저장한 속성의 범위를 혼동하지 않습니다. pageContext는 실행 문맥이고 pageScope는 EL에서 page 범위 속성을 읽는 Map입니다. `${param.name}`, `${requestScope.message}`, `${sessionScope.loginUser}` 같은 EL 내장 객체와 위의 스크립팅 내장 객체도 구분합니다.

| 속성 저장 위치 | 유지 범위 | 새 링크 클릭 후 |
|---|---|---|
| pageContext.setAttribute | 현재 JSP page 범위(기본) | 사라짐 |
| request.setAttribute | 같은 요청, forward/include 사이 | 새 요청에는 사라짐 |
| session.setAttribute | 같은 브라우저 세션의 여러 요청 | 유지 |
| application.setAttribute | 해당 웹앱 공유 | 유지, 사용자 간 공유 |

application에 개인 로그인 상태를 넣으면 다른 사용자와 섞입니다. 방문 횟수는 세션에 넣으며, 본 예제의 단순 증가식은 순차 새로고침 관찰용입니다. 동시 요청을 처리하는 정확한 통계 카운터가 아닙니다.

## 3. 예제

implicit.jsp → scope-next.jsp 순서로 실행합니다. error-demo.jsp는 의도적으로 예외를 발생시키고 error-page.jsp에서 exception을 확인합니다.

### `src/main/webapp/implicit.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    pageContext.setAttribute("pageNote", "현재 JSP에만 저장");
    request.setAttribute("requestNote", "현재 요청에 저장");
    session.setAttribute("sessionNote", "같은 세션의 다음 요청에서도 유지");
    application.setAttribute("applicationNote", "이 웹앱에서 공유하는 안내");
    Integer visits = (Integer) session.getAttribute("visits");
    visits = visits == null ? 1 : visits + 1;
    session.setAttribute("visits", visits);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>내장 객체 실험</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>내장 객체 실험</h1>
    <table>
        <tr><th>객체</th><th>확인한 값</th></tr>
        <tr><td>request</td><td><%= esc(request.getMethod()) %></td></tr>
        <tr><td>response</td><td><%= response.getStatus() %></td></tr>
        <tr><td>out</td><td><% out.print("JspWriter로 출력한 문장"); %></td></tr>
        <tr><td>session</td><td>방문 횟수: <%= visits %></td></tr>
        <tr><td>application</td><td><%= esc(application.getInitParameter("courseTitle")) %></td></tr>
        <tr><td>config</td><td><%= esc(config.getServletName()) %></td></tr>
        <tr><td>pageContext</td><td><%= esc(pageContext.getAttribute("pageNote")) %></td></tr>
        <tr><td>page</td><td>page == this: <%= page == this %></td></tr>
    </table>
    <p><a href="scope-next.jsp">새 요청에서 범위 비교</a></p>
    <p><a href="error-demo.jsp?fail=yes">exception 오류 페이지 실험</a></p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

### `src/main/webapp/scope-next.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>새 요청의 범위</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>새 요청의 범위</h1>
    <p>page: <%= esc(pageContext.getAttribute("pageNote")) %></p>
    <p>request: <%= esc(request.getAttribute("requestNote")) %></p>
    <p>session: <%= esc(session.getAttribute("sessionNote")) %></p>
    <p>application: <%= esc(application.getAttribute("applicationNote")) %></p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

### `src/main/webapp/error-demo.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error-page.jsp" %>
<%
    if ("yes".equals(request.getParameter("fail"))) {
        throw new IllegalArgumentException("수업용 오류");
    }
%>
<p>오류가 발생하지 않았습니다.</p>
```

### `src/main/webapp/error-page.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/WEB-INF/common.jspf" %>
<% response.setStatus(500); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>오류 처리 결과</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>오류 처리 결과</h1>
    <p>exception 사용 가능: <%= exception != null %></p>
    <p>예외 종류: <%= esc(exception == null ? "없음" : exception.getClass().getSimpleName()) %></p>
    <p>이 화면은 수업용입니다. 스택 추적이나 민감한 내부 설정은 표시하지 않습니다.</p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

## 4. 실습 및 과제

1. implicit.jsp를 열고 새로고침하여 방문 횟수 증가를 확인합니다.
2. "새 요청에서 범위 비교"를 누릅니다. page와 request는 (없음), session과 application은 값이 있어야 합니다.
3. 시크릿 창에서 implicit.jsp를 열면 방문 횟수는 새 세션 기준 1입니다. application 안내는 같은 웹앱에서 공유합니다.
4. 오류 링크를 클릭하면 500과 exception 사용 가능 true, IllegalArgumentException이 표시됩니다. 일반 JSP에서 exception 변수를 바로 사용하면 안 됩니다.
5. 네 범위의 예상·실제 결과표와 오류 페이지 캡처를 제출합니다.

## 5. 평가문제

1. JSP out의 타입은? ① PrintWriter ② JspWriter ③ HttpSession. **②**.
2. exception을 제공하는 page 속성은? **isErrorPage="true"**.
3. 개인 로그인 값을 application에 저장하면 안 되는 이유는? **여러 사용자에게 공유되는 범위이기 때문**.

## 6. 학습요약

내장 객체는 컨테이너가 제공하는 변수입니다. 개인 상태는 session, 한 요청 전달 데이터는 request, 공통 안내는 application으로 목적에 맞게 구분합니다.

## 참고 자료

- [요청하신 학습 범위 참고 글](https://www.tutorialspoint.com/jsp/jsp_implicit_objects.htm)
- [수업 코드의 기준: Jakarta Server Pages 3.1 명세](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1)
- [요청 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html) · [응답 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html)
