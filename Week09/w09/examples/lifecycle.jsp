<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%!
    public void jspInit() {
        getServletContext().log("lifecycle.jsp: jspInit");
    }

    public void jspDestroy() {
        getServletContext().log("lifecycle.jsp: jspDestroy");
    }
%>
<%
    application.log("lifecycle.jsp: request received");
%>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>JSP Lifecycle</title>
    </head>
    <body>
        <h1>JSP Lifecycle</h1>
        <p>Refresh this page and check the server log.</p>
    </body>
</html>
