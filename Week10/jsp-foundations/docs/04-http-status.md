## 개발환경과 시작 위치

Windows 10 / Eclipse Enterprise 2025-09 / JDK 21 / Tomcat 10.1.x / JSP 3.1 / UTF-8. MySQL 8.0.46·Workbench 8은 기존 공통 환경이며 이 보충 실습에서는 DB를 사용하지 않습니다. 프로젝트명·Context root는 `jsp-foundations`입니다. 먼저 상위 **JSP 기초 보충 학습**의 환경 설정과 공통 파일을 작성하세요.

JSP 스크립틀릿은 내장 객체와 요청 흐름을 눈으로 확인하기 위한 교육용입니다. 기존 로그인 프로젝트는 Servlet에서 처리하고 JSP는 View로 사용하는 구조를 유지합니다. 두 방식을 같은 기능을 관찰하는 서로 다른 단계로 비교합니다.

## 1. 학습목표

학습 질문: **화면에 "오류"라고 써 있으면 HTTP 응답도 오류일까? 리다이렉트 후 보이는 200은 어느 요청의 결과일까?**

상태 코드의 범주와 실제 Network 결과를 설명하고 setStatus·sendError·sendRedirect를 구분합니다.

## 2. 이론

| 범주 | 의미 | 대표 코드 |
|---|---|---|
| 1xx | 처리 중간 정보 | 100 Continue |
| 2xx | 요청 성공 | 200 OK, 201 Created, 204 No Content |
| 3xx | 이동·조건부 응답 등 | 302 Found, 303 See Other, 304 Not Modified |
| 4xx | 클라이언트 요청 문제 | 400, 401, 403, 404, 405 |
| 5xx | 서버 측 처리 문제 | 500, 503 |

| 코드 | 수업에서의 해석 |
|---|---|
| 400 | 입력값 누락·형식 오류 |
| 401 | 인증 자격이 필요함, HTTP 인증 challenge와 함께 사용하는 의미 |
| 403 | 접근을 허용하지 않음, 기존 CSRF 실패 예제 |
| 404 | 경로·자원이 없거나 공개하지 않음 |
| 405 | 자원은 있으나 요청 메서드 불허, Allow 헤더 확인 |
| 500 | 서버 처리 실패; 실습에서는 의도적으로 설정한 응답도 비교 |
| 503 | 일시적 서비스 불가, 기존 DB 설정 실패 처리와 연결 |

| API | 동작 |
|---|---|
| setStatus(400) | 상태를 설정하고 작성한 본문을 계속 출력 가능 |
| sendError(400, ...) | 오류 처리 메커니즘으로 넘김; 이후 출력하지 않고 return |
| sendRedirect(...) | Tomcat 10.1의 해당 호출은 302와 Location, 이후 새 요청 |

화면 문구만 바꾼다고 상태 코드는 바뀌지 않습니다. 302 뒤 목적지의 200을 구분하려면 Preserve log로 양쪽 문서 요청을 확인합니다. 304는 응답 본문 없이 캐시 재사용을 지시하는 조건부 응답이며 3xx 전체가 페이지 이동은 아닙니다. 204에는 본문을 보내지 않습니다. HTTP/2에서는 HTTP/1.1의 텍스트 상태줄과 메시지 모양을 그대로 가정하지 않습니다.

## 3. 예제


### `src/main/webapp/status.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    if (!"GET".equals(request.getMethod())) {
        response.setHeader("Allow", "GET");
        response.sendError(405, "이 예제는 GET만 허용합니다.");
        return;
    }
    String code = request.getParameter("code");
    int status;
    if (code == null || "200".equals(code)) {
        status = 200;
    } else if ("400".equals(code) || "403".equals(code)
            || "404".equals(code) || "500".equals(code)) {
        status = Integer.parseInt(code);
    } else {
        response.sendError(400, "지원하지 않는 실습 코드입니다.");
        return;
    }
    response.setStatus(status);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>상태 코드 실험</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>상태 코드 실험</h1>
    <p>이번 응답의 실제 상태 코드: <%= status %></p>
    <p>아래 링크를 열고 F12 → Network → 문서 요청의 Status를 확인하세요.</p>
    <p>
        <a href="status.jsp?code=200">200 정상</a> ·
        <a href="status.jsp?code=400">400 요청 오류</a> ·
        <a href="status.jsp?code=403">403 거부</a> ·
        <a href="status.jsp?code=404">404 없음</a> ·
        <a href="status.jsp?code=500">500 서버 오류(의도적 응답)</a>
    </p>
    <form method="post" action="status.jsp">
        <button>POST로 405 확인</button>
    </form>
    <p><a href="redirect.jsp">302와 후속 200 비교</a></p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

`redirect.jsp`와 `target.jsp`는 Actions 실습의 전체 코드를 함께 사용합니다. 오류 500 링크는 실제 예외를 일으키지 않고 상태를 설정한 실험입니다. 실제 예외 처리는 내장 객체의 error-demo.jsp에서 비교합니다.

## 4. 실습 및 과제

1. F12 → Network → Preserve log를 켭니다. status.jsp?code=200 → 400 → 403 → 404 → 500 링크를 차례대로 실행합니다.
2. 모든 페이지가 HTML을 표시해도 Status가 달라짐을 확인합니다. 브라우저의 전체 화면 오류 표시 방식은 버전에 따라 다를 수 있습니다.
3. POST 버튼은 405와 Allow: GET을 반환해야 합니다. status.jsp?code=999는 400으로 거부됩니다.
4. 302 링크를 눌러 redirect.jsp의 302와 target.jsp의 200을 기록합니다.
5. 기존 프로젝트의 로그인 실패가 오류 문구를 표시하면서도 200인 이유를 설명합니다. 인증 실패에 항상 같은 코드를 쓰는 것은 아니며 이 수업 구현의 선택입니다.

## 5. 평가문제

1. 메서드 불허는? ① 400 ② 404 ③ 405. **③**.
2. 본문에 오류라고 썼지만 setStatus를 하지 않았다면 반드시 500인가? **아니다**.
3. 302 응답에서 목적지를 나타내는 헤더는? **Location**.

## 6. 학습요약

상태 코드는 응답 의미이며 화면 문구와 구별합니다. Network에서는 리다이렉트 이전과 이후 요청을 각각 분석합니다.

## 참고 자료

- [요청하신 학습 범위 참고 글](https://www.tutorialspoint.com/jsp/jsp_http_status_codes.htm)
- [수업 코드의 기준: Jakarta Server Pages 3.1 명세](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1)
- [요청 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html) · [응답 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html)
