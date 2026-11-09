<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>학습 일정 관리</title>
  <style>
    body { font:16px sans-serif; max-width:1000px; margin:40px auto; padding:16px; background:#f3f6fa; }
    section, article { background:white; padding:20px; margin:16px 0; border-radius:12px; }
    input, select, button { padding:10px; margin:4px; max-width:90%; }
    button { background:#1952a0; color:white; border:0; cursor:pointer; }
    label { display:inline-block; }
  </style>
</head>
<body>
  <h1>학습 일정 관리</h1>
  <p>JSP · Servlet · MySQL 개인 프로젝트 참고</p>
  <c:url var="endpoint" value="/items" />
  <section>
    <h2>검색</h2>
    <form method="get" action="${endpoint}">
      <label>할 일 <input name="q" maxlength="100" value="<c:out value='${param.q}' />"></label>
      <button>검색</button>
      <a href="${endpoint}">전체 목록</a>
    </form>
  </section>
  <section>
    <h2>새 항목 등록</h2>
    <form method="post" action="${endpoint}">
      <input type="hidden" name="csrf" value="${sessionScope.csrf}">
      <input type="hidden" name="action" value="save">
      <input type="hidden" name="id" value="0">
      <label>할 일 <input name="title" required maxlength="100"></label>
      <label>담당자 <input name="owner" required maxlength="60"></label>
      <label>진행 상태
        <select name="status">
          <c:forEach var="s" items="${statuses}">
            <option value="${s}"><c:out value="${s}" /></option>
          </c:forEach>
        </select>
      </label>
      <button>등록</button>
    </form>
  </section>
  <h2>목록 · 수정 · 삭제</h2>
  <c:if test="${empty items}"><p>조회 결과가 없습니다.</p></c:if>
  <c:forEach var="item" items="${items}">
    <article>
      <h3>#${item.id} <c:out value="${item.title}" /></h3>
      <form method="post" action="${endpoint}">
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="id" value="${item.id}">
        <label>할 일 <input name="title" required maxlength="100" value="<c:out value='${item.title}' />"></label>
        <label>담당자 <input name="owner" required maxlength="60" value="<c:out value='${item.owner}' />"></label>
        <label>진행 상태
          <select name="status">
            <c:forEach var="s" items="${statuses}">
              <option value="${s}" ${s eq item.status ? 'selected' : ''}><c:out value="${s}" /></option>
            </c:forEach>
          </select>
        </label>
        <button>수정 저장</button>
      </form>
      <form method="post" action="${endpoint}" onsubmit="return confirm('삭제하시겠습니까?');">
        <input type="hidden" name="csrf" value="${sessionScope.csrf}">
        <input type="hidden" name="action" value="delete">
        <input type="hidden" name="id" value="${item.id}">
        <button>삭제</button>
      </form>
    </article>
  </c:forEach>
</body>
</html>
