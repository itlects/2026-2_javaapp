## Development Environment and Starting Point

Windows 10 / Eclipse IDE for Enterprise Java and Web Developers 2025-09 / JDK 21 / Tomcat 10.1.x / JSP 3.1 / UTF-8. MySQL Community Server 8.0.46 and Workbench 8 are part of the shared course environment, but this supplementary project does not use a database. The project name and Context root are both `jsp-foundations`.

Start with the parent **Week 10 Supplement — JSP Foundations** page. Create its common files before following this tutorial. JSP scriptlets are used here to inspect objects and request processing directly. The existing login project continues to process requests in Servlets and render views in JSP.

The complete code is shared with the Korean edition and retains its Korean UI strings and sample names for UTF-8 testing. Explanations, instructions, expected results, and review questions are in English. File names, parameters, class names, and control flow are unchanged, so the same downloadable project works for both editions.

## 1. Learning Objectives

**Learning questions: If a page says "error," is its HTTP response necessarily an error? After a redirect, which request produced the visible 200 response?**

Explain status-code categories and distinguish setStatus, sendError, and sendRedirect using actual Network results.

## 2. Theory

| Category | Meaning | Examples |
|---|---|---|
| 1xx | Interim information | 100 Continue |
| 2xx | Successful handling | 200 OK, 201 Created, 204 No Content |
| 3xx | Redirection and conditional response behavior | 302 Found, 303 See Other, 304 Not Modified |
| 4xx | Client request problem | 400, 401, 403, 404, 405 |
| 5xx | Server-side problem | 500, 503 |

| Code | Interpretation in this course |
|---|---|
| 400 | Missing or invalid input |
| 401 | Authentication credentials required; consider HTTP authentication challenge semantics |
| 403 | Access refused; compare the existing CSRF failure |
| 404 | Resource absent or not disclosed |
| 405 | Method not allowed for a resource; inspect Allow |
| 500 | Server failure; compare an intentionally assigned status with a real exception |
| 503 | Service unavailable; compare existing database configuration failure handling |

| API | Behavior |
|---|---|
| setStatus(400) | Assign status and continue writing your own response body |
| sendError(400, ...) | Invoke error handling; stop further output and return |
| sendRedirect(...) | This Tomcat 10.1 overload sends 302 and Location; the browser makes a new request |

Changing body text does not change the status. Enable Preserve log to distinguish a 302 response from its destination's 200 response. Not every 3xx means page navigation: a 304 conditional response allows cache reuse without a response body. A 204 response must not include a body. HTTP/2 does not use the exact textual status-line representation of HTTP/1.1.

## 3. Example

### `src/main/webapp/status.jsp` — Complete Code

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    if (!"GET".equals(request.getMethod())) {
        response.setHeader("Allow", "GET");
        response.sendError(405, "이 예제는 GET만 허용합니다.");
        return;
    }
    String code = request.getParameter("code");
    int status;
    if (code == null || "200".equals(code)) {
        status = 200;
    } else if ("400".equals(code) || "403".equals(code)
            || "404".equals(code) || "500".equals(code)) {
        status = Integer.parseInt(code);
    } else {
        response.sendError(400, "지원하지 않는 실습 코드입니다.");
        return;
    }
    response.setStatus(status);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상태 코드 실험</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>상태 코드 실험</h1>
    <p>이번 응답의 실제 상태 코드: <%= status %></p>
    <p>아래 링크를 열고 F12 → Network → 문서 요청의 Status를 확인하세요.</p>
    <p>
        <a href="status.jsp?code=200">200 정상</a> ·
        <a href="status.jsp?code=400">400 요청 오류</a> ·
        <a href="status.jsp?code=403">403 거부</a> ·
        <a href="status.jsp?code=404">404 없음</a> ·
        <a href="status.jsp?code=500">500 서버 오류(의도적 응답)</a>
    </p>
    <form method="post" action="status.jsp">
        <button>POST로 405 확인</button>
    </form>
    <p><a href="redirect.jsp">302와 후속 200 비교</a></p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

Use the complete redirect.jsp and target.jsp files from the Actions tutorial as well. The 500 link intentionally sets a response status; it does not throw an exception. Compare this with error-demo.jsp in the Implicit Objects tutorial.

## 4. Practice and Assignment

1. Open F12 → Network and enable Preserve log. Follow status.jsp?code=200, then the 400, 403, 404, and 500 links.
2. Confirm that an HTML page can be displayed with different response statuses. A browser's error-page presentation may vary by version.
3. The POST button should produce 405 and Allow: GET. status.jsp?code=999 should be rejected with 400.
4. Follow the redirect link and record redirect.jsp's 302 and target.jsp's subsequent 200 separately.
5. Explain why the existing login form can show an invalid-login message while returning 200. That is a choice in the existing implementation, not a universal rule for authentication failures.

## 5. Review Questions

1. Which means method not allowed? A. 400 B. 404 C. 405. **Answer: C.**
2. Does writing "error" in the body automatically produce status 500? **No.**
3. Which header identifies a 302 redirect destination? **Location.**

## 6. Learning Summary

A status communicates response semantics and is distinct from body text. Analyze the requests before and after a redirect separately in the Network panel.

## References

- [Requested topic reference](https://www.tutorialspoint.com/jsp/jsp_http_status_codes.htm)
- [Implementation reference: Jakarta Server Pages 3.1](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1)
- [Request API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html) · [Response API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html)
