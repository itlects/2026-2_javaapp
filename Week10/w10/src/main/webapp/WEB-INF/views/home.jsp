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
    <h1><c:out value="${requestScope.courseTitle}" /></h1>
    <p><c:out value="${sessionScope.loginUser}" />님, 환영합니다.</p>
    <section>
        <h2>나의 학습</h2>
        <ul>
            <li>9주차: JSP와 웹 개발환경 복습</li>
            <li>10주차: Servlet과 Session으로 로그인 흐름 구현</li>
            <li>실습 과제: Forward와 Redirect 비교 보고서</li>
        </ul>
    </section>
    <form method="post" action="${pageContext.request.contextPath}/logout">
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <button type="submit">로그아웃</button>
    </form>
</main>
</body>
</html>
