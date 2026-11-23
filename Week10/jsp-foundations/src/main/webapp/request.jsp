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
