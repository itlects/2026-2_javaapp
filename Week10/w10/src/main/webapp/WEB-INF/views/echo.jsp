<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Week10 학습 포털</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<main>
    <h1>Parameter 관찰</h1>
    <p>요청 메서드: <c:out value="${requestScope.method}" /></p>
    <p>이름: <c:out value="${requestScope.name}" /></p>
    <form method="get" action="${pageContext.request.contextPath}/echo">
        <label>GET 이름 <input name="name" value="홍길동"></label>
        <button>GET 전송</button>
    </form>
    <form method="post" action="${pageContext.request.contextPath}/echo">
        <label>POST 이름 <input name="name" value="김학생"></label>
        <button>POST 전송</button>
    </form>
    <p><a href="${pageContext.request.contextPath}/flow">Forward 실험</a></p>
    <p><a href="${pageContext.request.contextPath}/flow?mode=redirect">Redirect 실험</a></p>
    <p><a href="${pageContext.request.contextPath}/login">로그인으로</a></p>
</main>
</body>
</html>
