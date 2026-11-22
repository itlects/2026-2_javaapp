<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         import="java.time.LocalDate"
         session="true"
         isELIgnored="false" %>
<%@ taglib prefix="ui" tagdir="/WEB-INF/tags" %>
<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>JSP Directives</title>
    </head>
    <body>
        <%@ include file="/WEB-INF/jspf/courseHeader.jspf" %>
        <ui:courseBadge />
        <p>Date: <%= LocalDate.now() %></p>
        <p>EL result: ${10 + 20}</p>
    </body>
</html>
