<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>JSP 기초 보충 실습</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>JSP 기초 보충 실습</h1>
    <ol>
        <li><a href="actions.jsp">Actions와 JavaBean</a></li>
        <li><a href="implicit.jsp">내장 객체와 범위</a></li>
        <li><a href="request.jsp?name=홍길동">Client Request</a></li>
        <li><a href="status.jsp?code=200">HTTP 상태 코드</a></li>
        <li><a href="forms.jsp">폼 처리: GET/POST/다중 선택</a></li>
    </ol>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
