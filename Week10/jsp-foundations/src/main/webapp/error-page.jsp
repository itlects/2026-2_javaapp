<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ include file="/WEB-INF/common.jspf" %>
<% response.setStatus(500); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>오류 처리 결과</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>오류 처리 결과</h1>
    <p>exception 사용 가능: <%= exception != null %></p>
    <p>예외 종류: <%= esc(exception == null ? "없음" : exception.getClass().getSimpleName()) %></p>
    <p>이 화면은 수업용입니다. 스택 추적이나 민감한 내부 설정은 표시하지 않습니다.</p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
