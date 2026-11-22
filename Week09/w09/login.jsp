<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!doctype html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>로그인 | JAVA 학습 포털</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <header>
            <h1>JAVA 학습 포털</h1>
        </header>
        <main>
            <section class="card">
                <h2>로그인</h2>
                <% if ("1".equals(request.getParameter("error"))) { %>
                <p class="error" role="alert">아이디 또는 비밀번호를 확인하세요.</p>
                <% } %>
                <form action="loginAction.jsp" method="post">
                    <label>
                        아이디
                        <input name="userId" required maxlength="30" autocomplete="username">
                    </label>
                    <label>
                        비밀번호
                        <input type="password" name="password" required maxlength="100" autocomplete="current-password">
                    </label>
                    <button type="submit">로그인</button>
                </form>
                <p>실습 계정: student / Java1234!</p>
                <a href="index.jsp">홈으로</a>
            </section>
        </main>
    </body>
</html>
