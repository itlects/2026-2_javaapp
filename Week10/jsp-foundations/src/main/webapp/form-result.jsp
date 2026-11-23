<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    request.setCharacterEncoding("UTF-8");
    String studentName = request.getParameter("studentName");
    String[] topics = request.getParameterValues("topics");
    boolean valid = studentName != null && !studentName.isBlank()
            && studentName.strip().length() <= 30;
    if (!valid) {
        response.setStatus(400);
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>폼 처리 결과</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>폼 처리 결과</h1>
    <p>요청 메서드: <%= esc(request.getMethod()) %></p>
<% if (!valid) { %>
    <p>이름은 공백을 제외하여 1~30자로 입력하세요.</p>
<% } else { %>
    <p>신청자: <%= esc(studentName.strip()) %></p>
    <p>선택 수: <%= topics == null ? 0 : topics.length %></p>
    <ul>
<%
    if (topics != null) {
        for (String topic : topics) {
%>
        <li><%= esc(topic) %></li>
<%
        }
    }
%>
    </ul>
<% } %>
    <p>이 예제는 결과만 표시하며 DB 저장이나 로그인 상태 변경을 하지 않습니다.</p>
    <p><a href="forms.jsp">폼으로 돌아가기</a></p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
