<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    if (!"GET".equals(request.getMethod())) {
        response.setHeader("Allow", "GET");
        response.sendError(405, "이 예제는 GET만 허용합니다.");
        return;
    }
    String code = request.getParameter("code");
    int status;
    if (code == null || "200".equals(code)) {
        status = 200;
    } else if ("400".equals(code) || "403".equals(code)
            || "404".equals(code) || "500".equals(code)) {
        status = Integer.parseInt(code);
    } else {
        response.sendError(400, "지원하지 않는 실습 코드입니다.");
        return;
    }
    response.setStatus(status);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상태 코드 실험</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>상태 코드 실험</h1>
    <p>이번 응답의 실제 상태 코드: <%= status %></p>
    <p>아래 링크를 열고 F12 → Network → 문서 요청의 Status를 확인하세요.</p>
    <p>
        <a href="status.jsp?code=200">200 정상</a> ·
        <a href="status.jsp?code=400">400 요청 오류</a> ·
        <a href="status.jsp?code=403">403 거부</a> ·
        <a href="status.jsp?code=404">404 없음</a> ·
        <a href="status.jsp?code=500">500 서버 오류(의도적 응답)</a>
    </p>
    <form method="post" action="status.jsp">
        <button>POST로 405 확인</button>
    </form>
    <p><a href="redirect.jsp">302와 후속 200 비교</a></p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
