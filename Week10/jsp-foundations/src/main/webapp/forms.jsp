<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>학습 신청 폼</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>학습 신청 폼</h1>
    <form method="get" action="form-result.jsp" accept-charset="UTF-8">
        <label>이름(GET) <input name="studentName" required maxlength="30"></label>
        <button>GET 제출</button>
    </form>
    <hr>
    <form method="post" action="form-result.jsp" accept-charset="UTF-8">
        <label>이름(POST) <input name="studentName" required maxlength="30"></label>
        <label><input type="checkbox" name="topics" value="jsp"> JSP</label>
        <label><input type="checkbox" name="topics" value="servlet"> Servlet</label>
        <label><input type="checkbox" name="topics" value="session"> Session</label>
        <button>POST 제출</button>
    </form>
    <p>체크박스를 선택하지 않은 경우와 여러 개 선택한 경우를 비교하세요.</p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
