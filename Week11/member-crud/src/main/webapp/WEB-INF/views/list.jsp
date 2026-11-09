<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<main>
    <p class="eyebrow">JAVA APPLICATION · WEEK 11</p>
    <h1>교육센터 회원 관리</h1>
    <p>회원 정보를 등록하고 검색·수정·삭제합니다.</p>
    <form method="get" action="${pageContext.request.contextPath}/members" class="toolbar">
        <input name="q" value="<c:out value='${q}'/>" placeholder="이름 또는 이메일 검색" aria-label="검색어">
        <button>검색</button>
        <a href="${pageContext.request.contextPath}/members">전체 보기</a>
        <a class="button" href="${pageContext.request.contextPath}/members?action=new">회원 등록</a>
    </form>
    <table>
        <thead><tr><th>번호</th><th>이름</th><th>이메일</th><th>전화번호</th><th>관리</th></tr></thead>
        <tbody>
        <c:forEach var="m" items="${members}">
            <tr>
                <td><c:out value="${m.id}"/></td>
                <td><c:out value="${m.name}"/></td>
                <td><c:out value="${m.email}"/></td>
                <td><c:out value="${m.phone}"/></td>
                <td class="actions">
                    <a href="${pageContext.request.contextPath}/members?action=edit&amp;id=${m.id}">수정</a>
                    <form method="post" action="${pageContext.request.contextPath}/members"
                          onsubmit="return confirm('이 회원을 삭제할까요?');">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${m.id}">
                        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
                        <button class="danger">삭제</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty members}">
            <tr><td colspan="5">조회된 회원이 없습니다.</td></tr>
        </c:if>
        </tbody>
    </table>
</main>
</body>
</html>
