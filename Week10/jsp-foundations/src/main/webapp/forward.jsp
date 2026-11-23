<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    request.setAttribute("message", "forward에서 보낸 요청 속성");
%>
<jsp:forward page="target.jsp">
    <jsp:param name="route" value="jsp-forward" />
</jsp:forward>
