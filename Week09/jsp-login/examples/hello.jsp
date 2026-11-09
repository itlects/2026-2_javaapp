<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.time.LocalDateTime,java.time.format.DateTimeFormatter" %>
<!doctype html><html lang="ko"><head><meta charset="UTF-8"><title>동적 페이지</title></head><body><h1>서버에서 생성한 페이지</h1><p>서버 시각: <%= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) %></p><p>1 + 2 = <%= 1 + 2 %></p><a href="../index.jsp">홈으로</a></body></html>
