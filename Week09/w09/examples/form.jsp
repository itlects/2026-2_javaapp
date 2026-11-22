<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>폼 입력</title>
    </head>
    <body>
        <h1>이름과 관심 분야 전송</h1>
        <form action="result.jsp" method="post">
            <label>
                이름
                <input name="name" required maxlength="30">
            </label>
            <label>
                관심 분야
                <select name="topic">
                    <option value="JSP">JSP</option>
                    <option value="Java">Java</option>
                </select>
            </label>
            <button>전송</button>
        </form>
        <a href="../index.jsp">홈으로</a>
    </body>
</html>
