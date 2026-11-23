## Development Environment and Starting Point

Windows 10 / Eclipse IDE for Enterprise Java and Web Developers 2025-09 / JDK 21 / Tomcat 10.1.x / JSP 3.1 / UTF-8. MySQL Community Server 8.0.46 and Workbench 8 are part of the shared course environment, but this supplementary project does not use a database. The project name and Context root are both `jsp-foundations`.

Start with the parent **Week 10 Supplement — JSP Foundations** page. Create its common files before following this tutorial. JSP scriptlets are used here to inspect objects and request processing directly. The existing login project continues to process requests in Servlets and render views in JSP.

The complete code is shared with the Korean edition and retains its Korean UI strings and sample names for UTF-8 testing. Explanations, instructions, expected results, and review questions are in English. File names, parameters, class names, and control flow are unchanged, so the same downloadable project works for both editions.

## 1. Learning Objectives

**Learning question: Where do you read the name value in the URL and the browser's Accept-Language preference?**

Distinguish method, URI, query, parameters, headers, and body information, and compare server values with the browser's Network panel.

## 2. Theory

| Information | Meaning | API |
|---|---|---|
| Method | Request method such as GET or POST | getMethod() |
| URI | Request path including context, excluding query | getRequestURI() |
| Context path | Current web application path | getContextPath() |
| Query string | Encoded text after ?, null if absent | getQueryString() |
| Parameters | Parsed query/form values | getParameter / getParameterValues |
| Headers | Request metadata | getHeader / getHeaders / getHeaderNames |
| Content type | Body format, such as form data or JSON | getContentType() |
| Content length | Known body length in bytes, -1 if unknown | getContentLengthLong() |
| Locale and peer address | Preferred locale and directly connected address | getLocale / getRemoteAddr |

Accept describes acceptable response media types. Accept-Language expresses language preferences. User-Agent identifies the client, while Content-Type describes the submitted body. Accept-Encoding values such as gzip concern compression, not a character encoding such as UTF-8. Content-Length is not exclusive to POST; it can describe a request body where applicable.

getParameter handles query parameters and the supported HTML form encoding. It does not automatically parse arbitrary JSON fields. A proxy may cause getRemoteAddr to differ from the end user's address. Client-supplied headers such as User-Agent are not proof of identity.

The example displays only selected instructional headers. Do not print complete Cookie or Authorization values in a page or report. Arbitrary displayed values are escaped with esc.

## 3. Example

### `src/main/webapp/request.jsp` — Complete Code

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Client Request 관찰</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>Client Request 관찰</h1>
    <table>
        <tr><th>구분</th><th>값</th></tr>
        <tr><td>getMethod()</td><td><%= esc(request.getMethod()) %></td></tr>
        <tr><td>getRequestURI()</td><td><%= esc(request.getRequestURI()) %></td></tr>
        <tr><td>getContextPath()</td><td><%= esc(request.getContextPath()) %></td></tr>
        <tr><td>getQueryString()</td><td><%= esc(request.getQueryString()) %></td></tr>
        <tr><td>getParameter("name")</td><td><%= esc(request.getParameter("name")) %></td></tr>
        <tr><td>getContentType()</td><td><%= esc(request.getContentType()) %></td></tr>
        <tr><td>getContentLengthLong()</td><td><%= request.getContentLengthLong() %></td></tr>
        <tr><td>getProtocol()</td><td><%= esc(request.getProtocol()) %></td></tr>
        <tr><td>getLocale()</td><td><%= esc(request.getLocale()) %></td></tr>
        <tr><td>getRemoteAddr()</td><td><%= esc(request.getRemoteAddr()) %></td></tr>
    </table>
    <h2>학습용으로 선택한 헤더</h2>
    <table>
        <tr><th>이름</th><th>値</th></tr>
<%
    String[] names = {"Accept", "Accept-Language", "User-Agent", "Content-Type"};
    for (String name : names) {
%>
        <tr><td><%= esc(name) %></td><td><%= esc(request.getHeader(name)) %></td></tr>
<%
    }
%>
    </table>
    <form method="post" action="request.jsp" accept-charset="UTF-8">
        <label>이름 <input name="name" value="김학생"></label>
        <button>POST로 비교</button>
    </form>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

## 4. Practice and Assignment

1. Open `/jsp-foundations/request.jsp?name=홍길동`. The query may contain encoded text; the parsed parameter should display 홍길동.
2. Submit 김학생 using the POST form. Expect method POST, query **(없음)** (absent), and name 김학생. Also inspect the content type and body length.
3. Compare the page table with F12 → Network → Headers and Payload. Header names are case-insensitive; tools may display them differently.
4. Open request.jsp without name. Confirm that a null parameter is displayed as **(없음)**.
5. Submit a GET-versus-POST table with method, URI, query, name, and content type columns.

## 5. Review Questions

1. Which header expresses preferred languages? A. Content-Length B. Accept-Language C. Cookie. **Answer: B.**
2. Which methods read the raw query and a parsed parameter? **getQueryString and getParameter.**
3. Can getParameter alone read every field of an arbitrary JSON body? **No.**

## 6. Learning Summary

Method, path, headers, parameters, and body represent different parts of a request. Inspecting both the server page and the Network panel makes their relationships visible.

## References

- [Requested topic reference](https://www.tutorialspoint.com/jsp/jsp_client_request.htm)
- [Implementation reference: Jakarta Server Pages 3.1](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1)
- [Request API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html) · [Response API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html)
