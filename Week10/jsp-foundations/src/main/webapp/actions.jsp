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
