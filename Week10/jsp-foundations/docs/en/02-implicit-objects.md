## Development Environment and Starting Point

Windows 10 / Eclipse IDE for Enterprise Java and Web Developers 2025-09 / JDK 21 / Tomcat 10.1.x / JSP 3.1 / UTF-8. MySQL Community Server 8.0.46 and Workbench 8 are part of the shared course environment, but this supplementary project does not use a database. The project name and Context root are both `jsp-foundations`.

Start with the parent **Week 10 Supplement — JSP Foundations** page. Create its common files before following this tutorial. JSP scriptlets are used here to inspect objects and request processing directly. The existing login project continues to process requests in Servlets and render views in JSP.

The complete code is shared with the Korean edition and retains its Korean UI strings and sample names for UTF-8 testing. Explanations, instructions, expected results, and review questions are in English. File names, parameters, class names, and control flow are unchanged, so the same downloadable project works for both editions.

## 1. Learning Objectives

**Learning questions: Why can a JSP use request and session without explicitly creating them? Which stored values remain after navigating to another page?**

Explain the nine scripting implicit objects and distinguish page, request, session, and application attribute scopes.

## 2. Theory

The following types apply to HTTP JSP pages on Tomcat 10.1. Read older javax references as the corresponding jakarta APIs in this course.

| Object | Type | Purpose / condition |
|---|---|---|
| request | jakarta.servlet.http.HttpServletRequest | Method, parameters, request attributes |
| response | jakarta.servlet.http.HttpServletResponse | Status, headers, redirect |
| out | jakarta.servlet.jsp.JspWriter | JSP output; distinguish from PrintWriter |
| session | jakarta.servlet.http.HttpSession | State for the same session; unavailable when session="false" |
| application | jakarta.servlet.ServletContext | Web application initialization data and shared attributes |
| config | jakarta.servlet.ServletConfig | Configuration of the current JSP Servlet |
| pageContext | jakarta.servlet.jsp.PageContext | JSP execution context and scoped attribute access |
| page | java.lang.Object | Current JSP implementation instance; this in Java |
| exception | java.lang.Throwable | Available in an error JSP declared with isErrorPage="true" |

Do not confuse the availability of an implicit variable with the lifetime of attributes stored through it. pageContext is the execution context; the EL object pageScope is a map of page-scoped attributes. EL objects such as `${param.name}`, `${requestScope.message}`, and `${sessionScope.loginUser}` also differ from the scripting implicit objects listed above.

| Storage API | Attribute lifetime | After clicking a new link |
|---|---|---|
| pageContext.setAttribute | Current JSP page scope by default | Absent |
| request.setAttribute | Same request, including internal forward/include | Absent in the new request |
| session.setAttribute | Multiple requests in the same session | Retained |
| application.setAttribute | Shared within the web application | Retained and shared across users |

Do not store an individual's login state in application scope. The visit counter uses session scope. Its simple read/increment/write operation is for sequential refresh demonstrations, not an accurate concurrent statistics counter.

## 3. Examples

Run implicit.jsp, then follow the link to scope-next.jsp. error-demo.jsp intentionally throws an exception; error-page.jsp demonstrates the exception implicit object.
### `src/main/webapp/implicit.jsp` — Complete Code

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

### `src/main/webapp/scope-next.jsp` — Complete Code

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

### `src/main/webapp/error-demo.jsp` — Complete Code

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error-page.jsp" %>
<%
    if ("yes".equals(request.getParameter("fail"))) {
        throw new IllegalArgumentException("수업용 오류");
    }
%>
<p>오류가 발생하지 않았습니다.</p>
```

### `src/main/webapp/error-page.jsp` — Complete Code

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

## 4. Practice and Assignment

1. Open implicit.jsp and refresh it. The session's visit count increases.
2. Click **새 요청에서 범위 비교** (compare scopes in a new request). page and request should display **(없음)** (absent). session and application should retain their values.
3. Open implicit.jsp in a private/incognito window. A new session normally starts at visit 1, while the application-level notice remains shared in the same deployed application.
4. Follow the error link. Confirm status 500, **exception 사용 가능: true** (exception available), and IllegalArgumentException. Do not use the exception variable directly in an ordinary JSP.
5. Submit a predicted-versus-observed table for the four scopes and a screenshot of the error page.

## 5. Review Questions

1. What is the type of JSP out? A. PrintWriter B. JspWriter C. HttpSession. **Answer: B.**
2. Which page directive attribute makes exception available? **isErrorPage="true".**
3. Why must personal login state not be stored in application scope? **That scope is shared across users.**

## 6. Learning Summary

The container supplies implicit variables. Store individual state in session, data passed within one request in request, and shared application information in application, according to their intended lifetimes.

## References

- [Requested topic reference](https://www.tutorialspoint.com/jsp/jsp_implicit_objects.htm)
- [Implementation reference: Jakarta Server Pages 3.1](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1)
- [Request API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html) · [Response API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html)
