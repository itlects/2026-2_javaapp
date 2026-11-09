<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
request.setCharacterEncoding("UTF-8");
if (!"POST".equals(request.getMethod())) { response.sendError(405); return; }
String userId = request.getParameter("userId");
String password = request.getParameter("password");
// 수업용 고정 계정. 운영 서비스에서는 DB + 비밀번호 해시 검증으로 교체한다.
if ("student".equals(userId) && "Java1234!".equals(password)) {
    request.changeSessionId();
    session.setAttribute("loginUser", "student");
    session.setAttribute("displayName", "자바 학습자");
    session.setAttribute("csrfToken", java.util.UUID.randomUUID().toString());
    response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
} else {
    response.sendRedirect(request.getContextPath() + "/login.jsp?error=1");
}
%>
