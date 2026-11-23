## 개발환경과 시작 위치

Windows 10 / Eclipse Enterprise 2025-09 / JDK 21 / Tomcat 10.1.x / JSP 3.1 / UTF-8. MySQL 8.0.46·Workbench 8은 기존 공통 환경이며 이 보충 실습에서는 DB를 사용하지 않습니다. 프로젝트명·Context root는 `jsp-foundations`입니다. 먼저 상위 **JSP 기초 보충 학습**의 환경 설정과 공통 파일을 작성하세요.

JSP 스크립틀릿은 내장 객체와 요청 흐름을 눈으로 확인하기 위한 교육용입니다. 기존 로그인 프로젝트는 Servlet에서 처리하고 JSP는 View로 사용하는 구조를 유지합니다. 두 방식을 같은 기능을 관찰하는 서로 다른 단계로 비교합니다.

## 1. 학습목표

학습 질문: **공통 배너를 매번 작성하지 않고 포함하려면? JSP에서 다른 페이지로 제어를 넘기면 주소창도 바뀔까?**

표준 액션과 지시자를 구분하고 include·forward·param·JavaBean을 실행합니다.

## 2. 이론

JSP 표준 액션은 서버의 요청 처리 중 실행됩니다. `jsp:`는 브라우저의 HTML 기능이 아닙니다.

| 항목 | 기능 | 실습 |
|---|---|---|
| `jsp:include` | 실행 시 다른 자원의 출력 포함, 실행 후 원래 페이지 계속 | 배너 포함 |
| `jsp:forward` | 같은 요청을 목적지로 넘김, 호출 페이지의 이후 흐름 종료 | target.jsp 이동 |
| `jsp:param` | include·forward 대상에 요청 파라미터 추가 | topic / route 전달 |
| `jsp:useBean` | 지정 범위에서 Bean 조회, 없으면 생성 | student 생성 |
| `jsp:setProperty` | Bean의 setter로 속성 설정 | setName |
| `jsp:getProperty` | getter 결과를 응답에 출력 | getName |
| `jsp:element`, `jsp:attribute`, `jsp:body`, `jsp:text` | 동적 요소·속성·본문·템플릿 텍스트 구성 | 확장 개념 |
| `jsp:plugin`, `jsp:params`, `jsp:fallback` | Java 브라우저 플러그인 관련 과거 방식 | 개념만 확인, 실행 과제 제외 |

`id`와 `scope`는 모든 액션에 공통으로 쓸 수 있는 속성이 아닙니다. 여기서는 `jsp:useBean`의 id가 객체 이름이고 scope는 page(기본)·request·session·application 중 하나입니다. setProperty/getProperty는 name으로 Bean을 참조합니다. Bean은 public 클래스·public 기본 생성자·getter/setter를 갖춥니다.

| 비교 | include 지시자 | include 액션 |
|---|---|---|
| 문법 | `<%@ include file="..." %>` | `<jsp:include page="..." />` |
| 시점 | JSP 번역 시 소스 결합 | 요청 처리 중 대상 실행 결과 포함 |
| 목적 | 공통 선언 조각 결합 | 동적으로 생성하는 배너·메뉴 출력 |

include 대상은 전체 HTML 문서를 중첩하지 않고 조각을 출력합니다. forward는 응답이 이미 전송된 뒤에는 할 수 없으므로 출력·flush 이전에 실행합니다. `jsp:forward`와 Servlet의 RequestDispatcher.forward는 같은 내부 전달 개념입니다. Redirect는 별도의 브라우저 요청입니다.

`jsp:getProperty` 자체는 HTML 이스케이프를 하지 않습니다. 본 실습은 고정된 안전한 Bean 이름만 출력하며 임의 입력은 공통 esc 함수나 기존 예제의 c:out으로 출력합니다. property="*"를 이용한 무분별한 자동 바인딩은 사용하지 않습니다.

## 3. 예제

공통 파일 작성 후 아래 6개 JSP와 JavaBean을 순서대로 작성합니다. banner.jsp는 조각 페이지입니다. actions.jsp에서 포함한 결과를 관찰합니다.

### `src/main/java/edu/week10/StudentBean.java` — 전체 코드

```java
package edu.week10;

public class StudentBean {
    private String name;

    public StudentBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### `src/main/webapp/actions.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<jsp:useBean id="student" class="edu.week10.StudentBean" scope="request" />
<jsp:setProperty name="student" property="name" value="학습학생" />
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Actions 실험</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>Actions 실험</h1>
    <jsp:include page="banner.jsp">
        <jsp:param name="topic" value="JSP 표준 액션" />
    </jsp:include>
    <p>고정된 안전한 Bean 값: <jsp:getProperty name="student" property="name" /></p>
    <p><a href="forward.jsp">jsp:forward 실행</a></p>
    <p><a href="redirect.jsp">response.sendRedirect 실행</a></p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

### `src/main/webapp/banner.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<aside>포함된 배너: <%= esc(request.getParameter("topic")) %></aside>
```

### `src/main/webapp/forward.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    request.setAttribute("message", "forward에서 보낸 요청 속성");
%>
<jsp:forward page="target.jsp">
    <jsp:param name="route" value="jsp-forward" />
</jsp:forward>
```

### `src/main/webapp/redirect.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<%
    if (!response.isCommitted()) {
        request.setAttribute("message", "redirect 이전 요청 속성");
        response.sendRedirect(request.getContextPath() + "/target.jsp?route=redirect");
        return;
    }
%>
```

### `src/main/webapp/target.jsp` — 전체 코드

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>이동 결과</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>이동 결과</h1>
    <p>파라미터 route: <%= esc(request.getParameter("route")) %></p>
    <p>요청 속성 message: <%= esc(request.getAttribute("message")) %></p>
    <p>서버가 보는 요청 URI: <%= esc(request.getRequestURI()) %></p>
    <p>주소창 URL과 Network 요청 수를 별도로 확인하세요.</p>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

## 4. 실습 및 과제

1. Run on Server로 실행하고 `/jsp-foundations/actions.jsp`를 엽니다. 배너에 "JSP 표준 액션", Bean 값에 "학습학생"이 표시되는지 확인합니다.
2. "jsp:forward 실행"을 누릅니다. 주소창은 forward.jsp를 유지하고 message에는 요청 속성이 표시되어야 합니다. 서버의 getRequestURI 값과 주소창은 동일한 개념이 아닙니다.
3. actions.jsp로 돌아와 Redirect를 누릅니다. 주소창은 target.jsp?route=redirect로 바뀌고 message는 (없음)입니다.
4. F12 → Network → Preserve log를 켜고 내부 forward와 302 후속 GET을 비교합니다. favicon·CSS 요청은 문서 요청과 분리하여 셉니다.
5. Bean 이름과 배너의 topic을 바꾸어 결과 캡처 2장을 제출합니다.

## 5. 평가문제

1. 요청 시 대상의 출력을 포함하는 것은? ① include 지시자 ② jsp:include ③ sendRedirect. **정답 ②**.
2. Bean 값을 읽는 Java 메서드는 getter인가 setter인가? **getter**.
3. jsp:forward 뒤 request 속성과 주소창은 어떻게 되는가? **속성은 유지되고 주소창은 유지된다**.

## 6. 학습요약

include는 출력 조합, forward는 내부 제어 전달입니다. useBean/setProperty/getProperty는 객체 생성·설정·조회 과정과 연결합니다. 실무 로그인 흐름에서는 기존 Servlet Controller 구조로 같은 동작을 구현합니다.

## 참고 자료

- [요청하신 학습 범위 참고 글](https://www.tutorialspoint.com/jsp/jsp_actions.htm)
- [수업 코드의 기준: Jakarta Server Pages 3.1 명세](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1)
- [요청 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html) · [응답 API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html)
