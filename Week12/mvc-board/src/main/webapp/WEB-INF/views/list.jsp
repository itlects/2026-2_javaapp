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

  <h1>학과 공지 게시판</h1>
  <p>총 <c:out value="${total}"/>건 · ${paging.number}/${paging.totalPages}페이지</p>
  <form method="get" action="${pageContext.request.contextPath}/board">
    <label for="keyword">제목 검색</label>
    <input id="keyword" name="keyword" maxlength="100"
           value="<c:out value='${keyword}'/>">
    <input type="hidden" name="page" value="1">
    <button>검색</button>
  </form>
  <p><a href="${pageContext.request.contextPath}/board?action=new">글쓰기</a></p>
  <table>
    <thead><tr><th>번호</th><th>제목</th><th>작성자</th><th>작성일</th></tr></thead>
    <tbody>
    <c:forEach var="post" items="${posts}">
      <c:url var="detailUrl" value="/board">
        <c:param name="action" value="detail"/>
        <c:param name="id" value="${post.id}"/>
        <c:param name="keyword" value="${keyword}"/>
        <c:param name="page" value="${paging.number}"/>
      </c:url>
      <tr>
        <td>${post.id}</td>
        <td><a href="<c:out value='${detailUrl}'/>"><c:out value="${post.title}"/></a></td>
        <td><c:out value="${post.author}"/></td>
        <td><c:out value="${post.createdAt}"/></td>
      </tr>
    </c:forEach>
    <c:if test="${empty posts}">
      <tr><td colspan="4">검색 결과가 없습니다.</td></tr>
    </c:if>
    </tbody>
  </table>
  <nav aria-label="페이지 이동">
    <c:if test="${paging.number > 1}">
      <c:url var="prevUrl" value="/board">
        <c:param name="keyword" value="${keyword}"/>
        <c:param name="page" value="${paging.number - 1}"/>
      </c:url>
      <a href="<c:out value='${prevUrl}'/>">이전</a>
    </c:if>
    <c:forEach var="n" begin="${paging.start}" end="${paging.end}">
      <c:url var="pageUrl" value="/board">
        <c:param name="keyword" value="${keyword}"/>
        <c:param name="page" value="${n}"/>
      </c:url>
      <c:choose>
        <c:when test="${n == paging.number}"><strong aria-current="page">${n}</strong></c:when>
        <c:otherwise><a href="<c:out value='${pageUrl}'/>">${n}</a></c:otherwise>
      </c:choose>
    </c:forEach>
    <c:if test="${paging.number < paging.totalPages}">
      <c:url var="nextUrl" value="/board">
        <c:param name="keyword" value="${keyword}"/>
        <c:param name="page" value="${paging.number + 1}"/>
      </c:url>
      <a href="<c:out value='${nextUrl}'/>">다음</a>
    </c:if>
  </nav>
</main>
</body>
</html>
