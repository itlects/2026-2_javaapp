<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 입력</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<main class="narrow">
    <p class="eyebrow">MEMBER INFORMATION</p>
    <h1>${member.id == 0 ? '회원 등록' : '회원 수정'}</h1>
    <p class="error" role="alert"><c:out value="${error}"/></p>
    <form method="post" action="${pageContext.request.contextPath}/members">
        <input type="hidden" name="action" value="save">
        <c:if test="${member.id != 0}">
            <input type="hidden" name="id" value="${member.id}">
        </c:if>
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <label>이름
            <input name="name" value="<c:out value='${member.name}'/>" maxlength="50" required>
        </label>
        <label>이메일
            <input type="email" name="email" value="<c:out value='${member.email}'/>" maxlength="120" required>
        </label>
        <label>전화번호
            <input name="phone" value="<c:out value='${member.phone}'/>" maxlength="20" placeholder="010-1234-5678">
        </label>
        <button>저장</button>
        <a href="${pageContext.request.contextPath}/members">목록으로</a>
    </form>
</main>
</body>
</html>
