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
    <h1>JAVA 응용 학습 포털</h1>
    <p>10주차 · Servlet / Session</p>
    <h2>로그인</h2>
    <p class="error"><c:out value="${requestScope.error}" /></p>
    <form method="post" action="${pageContext.request.contextPath}/login">
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <label>아이디 <input name="userId" required maxlength="40" autocomplete="username"></label>
        <label>비밀번호 <input type="password" name="password" required maxlength="128" autocomplete="current-password"></label>
        <button type="submit">로그인</button>
    </form>
    <p>교육용 계정: student / Java1234!</p>
    <a href="${pageContext.request.contextPath}/echo?name=Java">GET/POST 예제</a>
</main>
</body>
</html>
