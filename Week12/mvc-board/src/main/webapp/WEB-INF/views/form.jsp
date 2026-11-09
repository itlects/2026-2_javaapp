<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>학과 공지 게시판</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/style.css">
</head>
<body>
<main>

  <h1>공지 등록</h1>
  <p role="alert"><c:out value="${error}"/></p>
  <form method="post" action="${pageContext.request.contextPath}/board">
    <input type="hidden" name="action" value="create">
    <input type="hidden" name="csrf" value="<c:out value='${sessionScope.csrf}'/>">
    <label for="title">제목</label>
    <input id="title" name="title" required maxlength="100"
           value="<c:out value='${title}'/>">
    <label for="author">작성자</label>
    <input id="author" name="author" required maxlength="30"
           value="<c:out value='${author}'/>">
    <label for="content">본문</label>
    <textarea id="content" name="content" required maxlength="5000" rows="10"><c:out value="${content}"/></textarea>
    <button>등록</button>
    <a href="${pageContext.request.contextPath}/board">취소</a>
  </form>
</main>
</body>
</html>
