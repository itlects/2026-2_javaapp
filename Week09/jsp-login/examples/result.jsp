<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
private String escapeHtml(String value) {
    if (value == null) return "";
    return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
}
%>
<%
request.setCharacterEncoding("UTF-8");
if (!"POST".equals(request.getMethod())) { response.sendError(405); return; }
String name = request.getParameter("name");
String topic = request.getParameter("topic");
if (name == null || name.isBlank() || name.length() > 30 ||
    !("JSP".equals(topic) || "Java".equals(topic))) { response.sendError(400); return; }
%>
<!doctype html><html lang="ko"><head><meta charset="UTF-8"><title>요청 결과</title></head><body><h1>요청 결과</h1><p>이름: <%= escapeHtml(name) %></p><p>관심 분야: <%= escapeHtml(topic) %></p><p>메서드: <%= request.getMethod() %></p><a href="form.jsp">다시 입력</a></body></html>
