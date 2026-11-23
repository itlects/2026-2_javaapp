## Development Environment and Starting Point

Windows 10 / Eclipse IDE for Enterprise Java and Web Developers 2025-09 / JDK 21 / Tomcat 10.1.x / JSP 3.1 / UTF-8. MySQL Community Server 8.0.46 and Workbench 8 are part of the shared course environment, but this supplementary project does not use a database. The project name and Context root are both `jsp-foundations`.

Start with the parent **Week 10 Supplement — JSP Foundations** page. Create its common files before following this tutorial. JSP scriptlets are used here to inspect objects and request processing directly. The existing login project continues to process requests in Servlets and render views in JSP.

The complete code is shared with the Korean edition and retains its Korean UI strings and sample names for UTF-8 testing. Explanations, instructions, expected results, and review questions are in English. File names, parameters, class names, and control flow are unchanged, so the same downloadable project works for both editions.

## 1. Learning Objectives

**Learning questions: Can getParameter read every selected checkbox value? Does HTML required eliminate the need for server validation?**

Implement GET/POST forms, single and multiple parameters, missing/blank/length validation, and UTF-8 handling.

## 2. Theory

| Item | Course interpretation |
|---|---|
| form action | Receiving JSP or Servlet path |
| method=get | Normally for retrieval/search; submitted values appear in query |
| method=post | Ordinary form values are submitted in the body |
| input name | Server parameter name; distinguish from id |
| getParameter | One string; null when absent |
| getParameterValues | Array of values with the same name; may be null when none are submitted |
| getParameterNames | Enumerate submitted parameter names |
| request.setCharacterEncoding | Set UTF-8 before reading POST parameters |
| HTML required / maxlength | Browser assistance; server validation remains necessary |

GET does not have a universal fixed 1024-character query limit. Practical limits depend on browsers, proxies, and server configuration. The query string is part of a URL, not an ordinary HTTP header. POST also has size limits and does not itself encrypt input.

Unchecked checkboxes may be omitted from submission. Use getParameterValues for multiple checkboxes with the same name="topics". Hidden fields can be modified by a client and must not be trusted automatically. Validation and HTML escaping solve different problems: even a validated name is escaped when displayed.

## 3. Examples

After preparing the common files, create forms.jsp and form-result.jsp. This is a learning registration form that displays results without writing to a database.
### `src/main/webapp/forms.jsp` — Complete Code

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>학습 신청 폼</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>학습 신청 폼</h1>
    <form method="get" action="form-result.jsp" accept-charset="UTF-8">
        <label>이름(GET) <input name="studentName" required maxlength="30"></label>
        <button>GET 제출</button>
    </form>
    <hr>
    <form method="post" action="form-result.jsp" accept-charset="UTF-8">
        <label>이름(POST) <input name="studentName" required maxlength="30"></label>
        <label><input type="checkbox" name="topics" value="jsp"> JSP</label>
        <label><input type="checkbox" name="topics" value="servlet"> Servlet</label>
        <label><input type="checkbox" name="topics" value="session"> Session</label>
        <button>POST 제출</button>
    </form>
    <p>체크박스를 선택하지 않은 경우와 여러 개 선택한 경우를 비교하세요.</p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

### `src/main/webapp/form-result.jsp` — Complete Code

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    request.setCharacterEncoding("UTF-8");
    String studentName = request.getParameter("studentName");
    String[] topics = request.getParameterValues("topics");
    boolean valid = studentName != null && !studentName.isBlank()
            && studentName.strip().length() <= 30;
    if (!valid) {
        response.setStatus(400);
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>폼 처리 결과</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>폼 처리 결과</h1>
    <p>요청 메서드: <%= esc(request.getMethod()) %></p>
<% if (!valid) { %>
    <p>이름은 공백을 제외하여 1~30자로 입력하세요.</p>
<% } else { %>
    <p>신청자: <%= esc(studentName.strip()) %></p>
    <p>선택 수: <%= topics == null ? 0 : topics.length %></p>
    <ul>
<%
    if (topics != null) {
        for (String topic : topics) {
%>
        <li><%= esc(topic) %></li>
<%
        }
    }
%>
    </ul>
<% } %>
    <p>이 예제는 결과만 표시하며 DB 저장이나 로그인 상태 변경을 하지 않습니다.</p>
    <p><a href="forms.jsp">폼으로 돌아가기</a></p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

## 4. Practice and Assignment

1. In forms.jsp, submit 홍길동 through the GET form. Inspect the result and the query in the address bar.
2. In the POST form, enter 김학생 and select JSP and Session. Expect method POST, **선택 수: 2** (selected count: 2), and the values jsp and session.
3. Clear all checkboxes and submit again. Expect **선택 수: 0**, without an exception.
4. Open form-result.jsp directly, or submit a name consisting only of spaces. Expect 400 and the validation message. Spaces may pass HTML required, which illustrates why the server checks isBlank.
5. Enter `<script>alert(1)</script>` as the name. It is within 30 characters and must be displayed as text rather than executed.
6. Compare parameter access, UTF-8 handling, and dispatch with EchoServlet and LoginServlet. Actual authentication and database operations remain in the existing Controller-based project.

| Test | Expected result |
|---|---|
| GET with 홍길동 | Query present; name displayed correctly |
| POST with 김학생 and two boxes | Query absent; selected count 2 |
| No selected boxes | Selected count 0 |
| Missing, blank, or over-30-character name | 400 |
| HTML tag as input | Escaped text; no script execution |

## 5. Review Questions

1. Which reads multiple values? A. getHeader B. getParameterValues C. getAttribute. **Answer: B.**
2. Should the POST character encoding be set before or after reading parameters? **Before.**
3. Can client-side required replace server validation? **No.**

## 6. Learning Summary

Forms submit values through name attributes. Distinguish single, multiple, and absent values; apply server validation, correct character encoding, and safe output handling.

## References

- [Requested topic reference](https://www.tutorialspoint.com/jsp/jsp_form_processing.htm)
- [Implementation reference: Jakarta Server Pages 3.1](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1)
- [Request API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html) · [Response API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html)
