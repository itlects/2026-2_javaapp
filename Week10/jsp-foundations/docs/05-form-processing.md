## 개발환경과 시작 위치

Windows 10 / Eclipse Enterprise 2025-09 / JDK 21 / Tomcat 10.1.x / JSP 3.1 / UTF-8. MySQL 8.0.46·Workbench 8은 기존 공통 환경이며 이 보충 실습에서는 DB를 사용하지 않습니다. 프로젝트명·Context root는 `jsp-foundations`입니다. 먼저 상위 **JSP 기초 보충 학습**의 환경 설정과 공통 파일을 작성하세요.

JSP 스크립틀릿은 내장 객체와 요청 흐름을 눈으로 확인하기 위한 교육용입니다. 기존 로그인 프로젝트는 Servlet에서 처리하고 JSP는 View로 사용하는 구조를 유지합니다. 두 방식을 같은 기능을 관찰하는 서로 다른 단계로 비교합니다.

## 1. 학습목표

학습 질문: **체크박스를 여러 개 고르면 getParameter로 모두 읽을 수 있을까? HTML required가 있으면 서버 검증은 생략해도 될까?**

GET/POST 폼, 단일·다중 파라미터, null·공백·길이 검증과 한글 처리를 구현합니다.

## 2. 이론

| 항목 | 학습 기준 |
|---|---|
| form action | 요청받을 JSP/Servlet 경로 |
| method=get | 일반 조회·검색, query에 입력값 포함 |
| method=post | 일반 form은 body에 입력값 포함 |
| input name | 서버 파라미터 이름, id와 구별 |
| getParameter | 하나의 문자열, 누락되면 null |
| getParameterValues | 같은 이름의 값 배열, 모두 미선택이면 null 가능 |
| getParameterNames | 전송된 파라미터 이름 열거 |
| request.setCharacterEncoding | POST 값을 읽기 전에 UTF-8 지정 |
| HTML required / maxlength | 브라우저의 입력 보조, 서버 검증도 필요 |

GET 요청 문자열에 "항상 1024자"라는 공통 한도가 있는 것은 아닙니다. 브라우저·프록시·서버 설정이 제한을 정합니다. Query string은 일반적인 HTTP 헤더 이름이 아니라 URL의 일부입니다. POST도 크기 제한이 있으며 자체 암호화를 제공하지 않습니다.

미선택 체크박스는 전송되지 않을 수 있습니다. 같은 name="topics"로 여러 값을 보낼 때 getParameterValues를 사용합니다. 숨김 필드도 사용자가 바꿀 수 있으므로 신뢰하지 않습니다. 입력 검증과 HTML 이스케이프는 별개입니다. 검증한 이름도 출력할 때 esc를 적용합니다.

## 3. 예제

공통 파일을 준비하고 forms.jsp와 form-result.jsp를 작성합니다. DB에 저장하지 않는 연습용 학습 신청 폼입니다.

### `src/main/webapp/forms.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>학습 신청 폼</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>학습 신청 폼</h1>
    <form method="get" action="form-result.jsp" accept-charset="UTF-8">
        <label>이름(GET) <input name="studentName" required maxlength="30"></label>
        <button>GET 제출</button>
    </form>
    <hr>
    <form method="post" action="form-result.jsp" accept-charset="UTF-8">
        <label>이름(POST) <input name="studentName" required maxlength="30"></label>
        <label><input type="checkbox" name="topics" value="jsp"> JSP</label>
        <label><input type="checkbox" name="topics" value="servlet"> Servlet</label>
        <label><input type="checkbox" name="topics" value="session"> Session</label>
        <button>POST 제출</button>
    </form>
    <p>체크박스를 선택하지 않은 경우와 여러 개 선택한 경우를 비교하세요.</p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

### `src/main/webapp/form-result.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    request.setCharacterEncoding("UTF-8");
    String studentName = request.getParameter("studentName");
    String[] topics = request.getParameterValues("topics");
    boolean valid = studentName != null && !studentName.isBlank()
            && studentName.strip().length() <= 30;
    if (!valid) {
        response.setStatus(400);
    }
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>폼 처리 결과</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>폼 처리 결과</h1>
    <p>요청 메서드: <%= esc(request.getMethod()) %></p>
<% if (!valid) { %>
    <p>이름은 공백을 제외하여 1~30자로 입력하세요.</p>
<% } else { %>
    <p>신청자: <%= esc(studentName.strip()) %></p>
    <p>선택 수: <%= topics == null ? 0 : topics.length %></p>
    <ul>
<%
    if (topics != null) {
        for (String topic : topics) {
%>
        <li><%= esc(topic) %></li>
<%
        }
    }
%>
    </ul>
<% } %>
    <p>이 예제는 결과만 표시하며 DB 저장이나 로그인 상태 변경을 하지 않습니다.</p>
    <p><a href="forms.jsp">폼으로 돌아가기</a></p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

## 4. 실습 및 과제

1. forms.jsp에서 GET 이름을 홍길동으로 제출합니다. 결과와 주소창 query를 확인합니다.
2. POST 이름을 김학생으로 쓰고 JSP·Session을 체크합니다. 결과는 POST, 선택 수 2와 jsp/session이어야 합니다.
3. 체크박스를 모두 해제한 뒤 제출합니다. 선택 수 0으로 정상 처리되어야 합니다.
4. form-result.jsp를 직접 열거나 이름에 공백만 넣어 제출합니다. 400과 검증 메시지가 표시되어야 합니다. 공백은 required를 통과할 수 있어 서버 검증이 필요합니다.
5. 이름으로 `<script>alert(1)</script>`를 입력해도 실행되지 않고 문자로 표시되는지 확인합니다. 30자 이내 입력입니다.
6. 기존 EchoServlet·LoginServlet과 파라미터 읽기·UTF-8·forward 처리를 비교합니다. 실제 로그인이나 DB 저장은 기존 Controller에서 처리합니다.

| 테스트 | 기대 결과 |
|---|---|
| GET 홍길동 | query 포함, 이름 정상 |
| POST 김학생 + 2개 체크 | query 없음, 선택 수 2 |
| 체크 없음 | 선택 수 0 |
| 이름 누락·공백·30자 초과 | 400 |
| HTML 태그 입력 | 태그 실행 없이 문자 출력 |

## 5. 평가문제

1. 다중 체크 값을 읽는 메서드는? ① getHeader ② getParameterValues ③ getAttribute. **②**.
2. POST 한글 인코딩 설정은 값 읽기 전인가 후인가? **전**.
3. 클라이언트 required만으로 서버 검증을 대체할 수 있는가? **없다**.

## 6. 학습요약

폼은 name으로 값을 전송합니다. 단일값·다중값·누락값을 구분하고, 서버 검증·문자 인코딩·안전한 출력을 순서대로 적용합니다.

## 참고 자료

- [요청하신 학습 범위 참고 글](https://www.tutorialspoint.com/jsp/jsp_form_processing.htm)
- [수업 코드의 기준: Jakarta Server Pages 3.1 명세](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1)
- [요청 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html) · [응답 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html)
