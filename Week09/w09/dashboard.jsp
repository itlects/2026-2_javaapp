<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/auth.jspf" %>
<!doctype html>
<html lang="ko">
    <head>
        <meta charset="UTF-8">
        <title>나의 학습 현황</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <header>
            <span class="badge">MY LEARNING</span>
            <h1>나의 학습 현황</h1>
        </header>
        <main>
            <section class="card">
                <h2><%= session.getAttribute("displayName") %>님, 환영합니다</h2>
                <p>로그인 계정:<%= session.getAttribute("loginUser") %></p>
                <p>출력 이름과 계정은 서버가 지정한 고정 문자열입니다.</p>
                <table>
                    <thead>
                        <tr>
                            <th>
                                학습 항목
                            </th>
                            <th>이번 주 목표</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>WEB · Tomcat</td>
                            <td>요청과 응답 흐름 설명하기</td>
                        </tr>
                        <tr>
                            <td>HTML Form</td>
                            <td>POST로 매개변수 보내기</td>
                        </tr>
                        <tr>
                            <td>JSP 로그인</td>
                            <td>세션 확인과 로그아웃 구현하기</td>
                        </tr>
                    </tbody>
                </table>
                <form action="logout.jsp" method="post">
                    <input type="hidden" name="csrfToken" value="<%= session.getAttribute("csrfToken") %>">
                    <p>
                        <button type="submit">
                            로그아웃
                        </button>
                        <a href="index.jsp">
                            홈으로
                        </a>
                    </p>
                </form>
            </section>
        </main>
    </body>
</html>
