## Development Environment and Starting Point

Windows 10 / Eclipse IDE for Enterprise Java and Web Developers 2025-09 / JDK 21 / Tomcat 10.1.x / JSP 3.1 / UTF-8. MySQL Community Server 8.0.46 and Workbench 8 are part of the shared course environment, but this supplementary project does not use a database. The project name and Context root are both `jsp-foundations`.

Start with the parent **Week 10 Supplement — JSP Foundations** page. Create its common files before following this tutorial. JSP scriptlets are used here to inspect objects and request processing directly. The existing login project continues to process requests in Servlets and render views in JSP.

The complete code is shared with the Korean edition and retains its Korean UI strings and sample names for UTF-8 testing. Explanations, instructions, expected results, and review questions are in English. File names, parameters, class names, and control flow are unchanged, so the same downloadable project works for both editions.

## 1. Learning Objectives

**Learning questions: How can you include a common banner without rewriting it? When a JSP forwards to another page, does the browser address change?**

Distinguish standard actions from directives and run include, forward, parameter, and JavaBean examples.

## 2. Theory

Standard JSP actions run on the server while processing a request. The `jsp:` prefix does not refer to a browser HTML feature.

| Action | Purpose | Exercise |
|---|---|---|
| `jsp:include` | Include another resource's output at request time, then continue the caller | Shared banner |
| `jsp:forward` | Transfer the same request to a target and end the caller's processing | target.jsp |
| `jsp:param` | Add request parameters for an include or forward target | topic / route |
| `jsp:useBean` | Find a bean in the specified scope or create it | student bean |
| `jsp:setProperty` | Set a property through its setter | setName |
| `jsp:getProperty` | Write the getter result to the response | getName |
| `jsp:element`, `jsp:attribute`, `jsp:body`, `jsp:text` | Construct elements, attributes, bodies, and template text | Extension concepts |
| `jsp:plugin`, `jsp:params`, `jsp:fallback` | Historical Java browser plug-in support | Concept only; no browser exercise |

`id` and `scope` are not universal attributes of every JSP action. In `jsp:useBean`, id names the object and scope selects page (default), request, session, or application. setProperty and getProperty refer to the bean through their name attribute. A bean in this example is a public class with a public no-argument constructor, a getter, and a setter.

| Comparison | Include directive | Include action |
|---|---|---|
| Syntax | `<%@ include file="..." %>` | `<jsp:include page="..." />` |
| When | Source is combined during JSP translation | Target output is included during request processing |
| Typical use | Shared declarations | Dynamic banner or menu |

The included banner outputs a fragment rather than nesting a whole HTML document. A forward must occur before the response is committed; avoid writing or flushing the response first. `jsp:forward` demonstrates the same internal dispatch concept as RequestDispatcher.forward in a Servlet. Redirect causes a new browser request.

`jsp:getProperty` does not automatically HTML-escape its output. This exercise prints a fixed, safe bean value; use esc or c:out when displaying arbitrary user input. Do not introduce uncontrolled property="*" binding.

## 3. Examples

After preparing the common files, create the JavaBean and six JSP files below in order. banner.jsp is a fragment included by actions.jsp. The redirect code checks response.isCommitted before redirecting and returning; this also avoids an unconditional top-level return followed by generated JSP output.
### `src/main/java/edu/week10/StudentBean.java` — Complete Code

```java
package edu.week10;

public class StudentBean {
    private String name;

    public StudentBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### `src/main/webapp/actions.jsp` — Complete Code

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<jsp:useBean id="student" class="edu.week10.StudentBean" scope="request" />
<jsp:setProperty name="student" property="name" value="학습학생" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Actions 실험</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>Actions 실험</h1>
    <jsp:include page="banner.jsp">
        <jsp:param name="topic" value="JSP 표준 액션" />
    </jsp:include>
    <p>고정된 안전한 Bean 값: <jsp:getProperty name="student" property="name" /></p>
    <p><a href="forward.jsp">jsp:forward 실행</a></p>
    <p><a href="redirect.jsp">response.sendRedirect 실행</a></p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

### `src/main/webapp/banner.jsp` — Complete Code

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<aside>포함된 배너: <%= esc(request.getParameter("topic")) %></aside>
```

### `src/main/webapp/forward.jsp` — Complete Code

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    request.setAttribute("message", "forward에서 보낸 요청 속성");
%>
<jsp:forward page="target.jsp">
    <jsp:param name="route" value="jsp-forward" />
</jsp:forward>
```

### `src/main/webapp/redirect.jsp` — Complete Code

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    if (!response.isCommitted()) {
        request.setAttribute("message", "redirect 이전 요청 속성");
        response.sendRedirect(request.getContextPath() + "/target.jsp?route=redirect");
        return;
    }
%>
```

### `src/main/webapp/target.jsp` — Complete Code

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>이동 결과</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>이동 결과</h1>
    <p>파라미터 route: <%= esc(request.getParameter("route")) %></p>
    <p>요청 속성 message: <%= esc(request.getAttribute("message")) %></p>
    <p>서버가 보는 요청 URI: <%= esc(request.getRequestURI()) %></p>
    <p>주소창 URL과 Network 요청 수를 별도로 확인하세요.</p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

## 4. Practice and Assignment

1. Use Run on Server, then open `/jsp-foundations/actions.jsp`. The banner should show **JSP 표준 액션** (JSP standard actions), and the bean value should be **학습학생** (student).
2. Click **jsp:forward 실행** (run jsp:forward). The address bar remains on forward.jsp. The message field displays **forward에서 보낸 요청 속성**, the request attribute set before forwarding. The server's getRequestURI value is not the same concept as the original browser address.
3. Return to actions.jsp and run Redirect. The address changes to target.jsp?route=redirect. The message field becomes **(없음)**, meaning no value.
4. In F12 → Network, enable Preserve log. Compare internal forward with the 302 response followed by a GET. Count document requests separately from favicon or stylesheet requests.
5. Change the bean's name value and the banner's topic. Submit two screenshots and explain the changes.

## 5. Review Questions

1. Which includes a target's output at request time? A. Include directive B. jsp:include C. sendRedirect. **Answer: B.**
2. Does reading a bean property call a getter or a setter? **A getter.**
3. What happens to the request attribute and browser address after jsp:forward? **The attribute is retained and the browser address is unchanged.**

## 6. Learning Summary

Include combines output; forward transfers control internally. useBean, setProperty, and getProperty correspond to finding/creating an object, setting a value, and reading it. Compare these operations with the existing Servlet Controller implementation.

## References

- [Requested topic reference](https://www.tutorialspoint.com/jsp/jsp_actions.htm)
- [Implementation reference: Jakarta Server Pages 3.1](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1)
- [Request API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html) · [Response API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html)
