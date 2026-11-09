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

  <h1><c:out value="${post.title}"/></h1>
  <p><c:out value="${post.author}"/> · <c:out value="${post.createdAt}"/></p>
  <pre class="content"><c:out value="${post.content}"/></pre>
  <c:url var="backUrl" value="/board">
    <c:param name="keyword" value="${keyword}"/>
    <c:param name="page" value="${backPage}"/>
  </c:url>
  <a href="<c:out value='${backUrl}'/>">목록으로</a>
</main>
</body>
</html>
