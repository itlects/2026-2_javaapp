## 개발환경과 시작 위치

Windows 10 / Eclipse Enterprise 2025-09 / JDK 21 / Tomcat 10.1.x / JSP 3.1 / UTF-8. MySQL 8.0.46·Workbench 8은 기존 공통 환경이며 이 보충 실습에서는 DB를 사용하지 않습니다. 프로젝트명·Context root는 `jsp-foundations`입니다. 먼저 상위 **JSP 기초 보충 학습**의 환경 설정과 공통 파일을 작성하세요.

JSP 스크립틀릿은 내장 객체와 요청 흐름을 눈으로 확인하기 위한 교육용입니다. 기존 로그인 프로젝트는 Servlet에서 처리하고 JSP는 View로 사용하는 구조를 유지합니다. 두 방식을 같은 기능을 관찰하는 서로 다른 단계로 비교합니다.

## 1. 학습목표

학습 질문: **주소창의 name 값과 브라우저의 Accept-Language는 어디에서 읽을까?**

요청 메서드·URI·query·파라미터·헤더·본문 정보를 구분하고 Network 화면과 대조합니다.

## 2. 이론

| 구분 | 의미 | 읽기 |
|---|---|---|
| 메서드 | GET/POST 등 작업 방법 | getMethod() |
| URI | 컨텍스트 포함 요청 경로, query 제외 | getRequestURI() |
| 컨텍스트 | 현재 웹앱 경로 | getContextPath() |
| Query string | ? 뒤의 인코딩된 원문, 없으면 null | getQueryString() |
| 파라미터 | 컨테이너가 해석한 form/query 값 | getParameter / getParameterValues |
| 헤더 | 요청 부가 정보 | getHeader / getHeaders / getHeaderNames |
| 본문 종류 | form·JSON 등 콘텐츠 형식 | getContentType() |
| 본문 길이 | 알려진 바이트 수, 알 수 없으면 -1 | getContentLengthLong() |
| 언어·접속 정보 | 선호 Locale·직접 접속 주소 | getLocale / getRemoteAddr |

Accept는 처리 가능한 응답 형식, Accept-Language는 선호 언어, User-Agent는 클라이언트 식별 정보입니다. Content-Type은 전송한 본문의 형식을 나타냅니다. Accept-Encoding의 gzip은 압축 방식이며 글자 인코딩 UTF-8과 구별합니다. Content-Length는 POST에만 한정된 개념이 아니며 본문이 있는 요청에 쓰일 수 있습니다.

getParameter는 일반 HTML form의 URL 인코딩 데이터를 처리합니다. 임의의 JSON 본문을 자동으로 같은 방식으로 읽지 않습니다. getRemoteAddr는 프록시 환경에서 최종 사용자의 IP와 다를 수 있고 User-Agent 등 클라이언트 헤더를 인증 증거로 신뢰하지 않습니다.

이 예제는 학습용 헤더만 선택하여 표시합니다. Cookie·Authorization 전체를 화면이나 보고서로 출력하지 않습니다. 임의 요청값은 esc로 HTML 이스케이프합니다.

## 3. 예제


### `src/main/webapp/request.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Client Request 관찰</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>Client Request 관찰</h1>
    <table>
        <tr><th>구분</th><th>값</th></tr>
        <tr><td>getMethod()</td><td><%= esc(request.getMethod()) %></td></tr>
        <tr><td>getRequestURI()</td><td><%= esc(request.getRequestURI()) %></td></tr>
        <tr><td>getContextPath()</td><td><%= esc(request.getContextPath()) %></td></tr>
        <tr><td>getQueryString()</td><td><%= esc(request.getQueryString()) %></td></tr>
        <tr><td>getParameter("name")</td><td><%= esc(request.getParameter("name")) %></td></tr>
        <tr><td>getContentType()</td><td><%= esc(request.getContentType()) %></td></tr>
        <tr><td>getContentLengthLong()</td><td><%= request.getContentLengthLong() %></td></tr>
        <tr><td>getProtocol()</td><td><%= esc(request.getProtocol()) %></td></tr>
        <tr><td>getLocale()</td><td><%= esc(request.getLocale()) %></td></tr>
        <tr><td>getRemoteAddr()</td><td><%= esc(request.getRemoteAddr()) %></td></tr>
    </table>
    <h2>학습용으로 선택한 헤더</h2>
    <table>
        <tr><th>이름</th><th>値</th></tr>
<%
    String[] names = {"Accept", "Accept-Language", "User-Agent", "Content-Type"};
    for (String name : names) {
%>
        <tr><td><%= esc(name) %></td><td><%= esc(request.getHeader(name)) %></td></tr>
<%
    }
%>
    </table>
    <form method="post" action="request.jsp" accept-charset="UTF-8">
        <label>이름 <input name="name" value="김학생"></label>
        <button>POST로 비교</button>
    </form>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

## 4. 실습 및 과제

1. `/jsp-foundations/request.jsp?name=홍길동`을 엽니다. Query에는 인코딩된 문자열이 보일 수 있고 Parameter에는 해석된 홍길동이 표시됩니다.
2. POST 폼에 김학생을 넣습니다. 메서드는 POST, query는 (없음), name은 김학생이어야 합니다. Content-Type과 길이도 확인합니다.
3. F12 → Network의 Headers와 Payload에서 페이지 표의 값과 맞춰 봅니다. 헤더의 대소문자는 구별하지 않으며 도구별 표시가 다를 수 있습니다.
4. name 없이 request.jsp를 열어 getParameter의 null을 (없음)으로 처리하는지 확인합니다.
5. GET·POST 결과를 메서드/URI/query/name/content type 열로 비교하여 제출합니다.

## 5. 평가문제

1. 선호 언어 헤더는? ① Content-Length ② Accept-Language ③ Cookie. **②**.
2. query 원문과 파라미터를 읽는 메서드를 각각 쓰시오. **getQueryString / getParameter**.
3. JSON 요청에서 getParameter만으로 모든 JSON 필드를 읽을 수 있는가? **아니다**.

## 6. 학습요약

메서드·경로·헤더·파라미터·본문은 요청을 구성하는 서로 다른 정보입니다. 서버 화면의 값과 브라우저 Network를 함께 관찰하면 전송 구조를 이해할 수 있습니다.

## 참고 자료

- [요청하신 학습 범위 참고 글](https://www.tutorialspoint.com/jsp/jsp_client_request.htm)
- [수업 코드의 기준: Jakarta Server Pages 3.1 명세](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1)
- [요청 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html) · [응답 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html)
