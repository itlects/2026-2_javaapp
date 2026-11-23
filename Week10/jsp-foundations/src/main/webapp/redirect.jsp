<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    if (!response.isCommitted()) {
        request.setAttribute("message", "redirect 이전 요청 속성");
        response.sendRedirect(request.getContextPath() + "/target.jsp?route=redirect");
        return;
    }
%>
