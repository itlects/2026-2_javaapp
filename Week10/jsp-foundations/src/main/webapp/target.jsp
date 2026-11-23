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
