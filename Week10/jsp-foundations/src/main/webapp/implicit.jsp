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
