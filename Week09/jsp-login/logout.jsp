<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
request.setCharacterEncoding("UTF-8");
if (!"POST".equals(request.getMethod())) { response.sendError(405); return; }
String expected = (String) session.getAttribute("csrfToken");
if (expected == null || !expected.equals(request.getParameter("csrfToken"))) {
    response.sendError(403); return;
}
session.invalidate();
response.sendRedirect(request.getContextPath() + "/login.jsp");
%>
