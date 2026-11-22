<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%!
    private int add(int a, int b) {
        return a + b;
    }
%>
<%
    int score = 85;
    request.setAttribute("course", "JAVA Application");
%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>JSP Syntax Lab</title>
    </head>
    <body>
        <%-- Server-side JSP comment: not sent to the browser. --%>
        <!-- HTML comment: visible in View Page Source. -->

        <h1>JSP Syntax Lab</h1>
        <p>Sum: <%= add(10, 20) %></p>
        <p>Course: ${requestScope.course}</p>

        <% if (score >= 60) { %>
            <p>Result: Pass</p>
        <% } else { %>
            <p>Result: Retry</p>
        <% } %>

        <ul>
            <% for (int i = 1; i <= 3; i++) { %>
                <li>Practice <%= i %></li>
            <% } %>
        </ul>
    </body>
</html>
