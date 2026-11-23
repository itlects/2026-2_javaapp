<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="error-page.jsp" %>
<%
    if ("yes".equals(request.getParameter("fail"))) {
        throw new IllegalArgumentException("수업용 오류");
    }
%>
<p>오류가 발생하지 않았습니다.</p>
