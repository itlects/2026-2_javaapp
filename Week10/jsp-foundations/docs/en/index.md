## Development Environment

Windows 10 / Eclipse IDE for Enterprise Java and Web Developers 2025-09 / JDK 21 / Tomcat 10.1.x / Servlet 6.0 / JSP 3.1 / UTF-8. MySQL Community Server 8.0.46 and Workbench 8 remain the shared course environment; this standalone supplement does not use a database.

**Learning question: How do JSP request access, page inclusion, and response status handling connect to the existing Servlet login flow?**

This supplement adds five JSP topics to the existing Week 10 Servlet/Session material. It reorganizes the requested reading into English explanations and hands-on exercises, with code aligned to the course environment and official APIs.

## Study Sequence

| Order | Topic | Result | Suggested time |
|---|---|---|---|
| 1 | JSP Actions | Shared banner, bean, and forward comparison | 25 min |
| 2 | Implicit Objects | Scoped values and an error page | 25 min |
| 3 | Client Request | Request and header inspection table | 15 min |
| 4 | HTTP Status Codes | 200, 302, 400, 403, 404, 405, 500 | 20 min |
| 5 | Form Processing | UTF-8 registration form, multiple selections, validation | 25 min |

This is 110 minutes of supplementary/self-study material. Do not automatically add it as required time to the existing 180-minute lesson. You can demonstrate requests, forms, and statuses during class and assign Actions and Implicit Objects as follow-up practice.

## Connection to the Existing Lesson

| Supplement exercise | Existing implementation |
|---|---|
| jsp:forward | Same internal dispatch concept as RequestDispatcher.forward |
| request / session implicit variables | Connect to storing and reading loginUser |
| request.jsp | Observe request data before comparing with EchoServlet |
| HTTP statuses | Analyze successful login 302→200, CSRF 403, GET logout 405 |
| Form processing | Compare parameter handling with LoginServlet and EchoServlet |

Scriptlets are used for introductory observation. Keep the existing Servlet Controller + JSP View design for authentication. This exercise does not move login or database logic into JSP.

The shared executable files retain Korean UI strings and sample names to test UTF-8. The English pages explain their meanings and expected results. The shared ZIP and source code are the same verified examples used by the Korean edition.

## Reading Notes and Corrections

| Expression or topic in the reading | Course interpretation |
|---|---|
| javax.servlet APIs | Use jakarta.servlet for Tomcat 10.1 |
| out described as PrintWriter | The JSP scripting variable is JspWriter |
| Capitalized Exception | The variable is exception, of type Throwable, available in error JSPs |
| id/scope described as universal action attributes | Attributes depend on each action; useBean uses id and scope |
| Fixed 1024-character GET limit | Practical limits depend on products and settings |
| Query string treated as a header | Distinguish URL query from HTTP headers |
| Content-Length restricted to POST | It describes body length where applicable, not exclusively POST |
| Java browser plug-in examples | Historical background; excluded from modern-browser exercises |

Implementation references: [JSP 3.1 specification](https://jakarta.ee/specifications/pages/3.1/jakarta-server-pages-spec-3.1), [Request API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletRequest.html), [Response API](https://tomcat.apache.org/tomcat-10.1-doc/servletapi/jakarta/servlet/http/HttpServletResponse.html).

## Start Here — Create the Eclipse Project

1. To use the complete project, choose File → Import → Maven → Existing Maven Projects and select `Week10/jsp-foundations`.
2. To build it yourself, choose File → New → Maven Project → Create a simple project. Set Group Id edu.week10, Artifact Id jsp-foundations, Packaging war, Version 1.0.0.
3. Replace pom.xml with the complete code below. Right-click the project → Maven → Update Project.
4. Check Java Compiler 21, Project Facets Java 21 / Dynamic Web Module 6.0, Tomcat 10.1 runtime, and Context root jsp-foundations.
5. Create the common files below, then follow the five topic pages. JavaBean source belongs in src/main/java; JSP files belong in src/main/webapp.
6. Choose Run As → Run on Server → Tomcat 10.1. Open `http://localhost:8080/jsp-foundations/index.jsp`.
7. For Maven packaging, run `mvn clean package` in the project folder and deploy target/jsp-foundations.war to Tomcat webapps. Use either Eclipse deployment or manual server execution, without starting duplicate servers.

### Purpose of the Common Files

pom.xml configures Java 21 and WAR packaging. web.xml provides a context initialization value and session settings. common.jspf contains an educational HTML escaping function. Its own UTF-8 pageEncoding directive is required to preserve Korean literals in the included fragment. No external JSTL library is required by this standalone project.

esc supports the HTML text and quoted-attribute contexts used by these examples. It is not a universal encoder for URLs, JavaScript strings, or every other output context. Preserve code indentation and line breaks when copying complete files.
### `pom.xml` — Complete Code

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>edu.week10</groupId>
    <artifactId>jsp-foundations</artifactId>
    <version>1.0.0</version>
    <packaging>war</packaging>
    <properties>
        <maven.compiler.release>21</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <build>
        <finalName>jsp-foundations</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.4.0</version>
            </plugin>
        </plugins>
    </build>
</project>
```

### `src/main/webapp/WEB-INF/web.xml` — Complete Code

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee" version="6.0">
    <context-param>
        <param-name>courseTitle</param-name>
        <param-value>JAVA 응용 10주차</param-value>
    </context-param>
    <session-config>
        <session-timeout>20</session-timeout>
        <cookie-config><http-only>true</http-only></cookie-config>
        <tracking-mode>COOKIE</tracking-mode>
    </session-config>
</web-app>
```

### `src/main/webapp/WEB-INF/common.jspf` — Complete Code

```html
<%@ page pageEncoding="UTF-8" %>
<%!
    // HTML 텍스트·따옴표로 감싼 속성 출력에 사용하는 교육용 함수입니다.
    private String esc(Object value) {
        if (value == null) {
            return "(없음)";
        }
        return value.toString()
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>
```

### `src/main/webapp/index.jsp` — Complete Code

```html
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common.jspf" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>JSP 기초 보충 실습</title>
    <style>
        body { font-family: "Malgun Gothic", sans-serif; max-width: 900px; margin: 36px auto; padding: 16px; line-height: 1.7; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #bbc; padding: 10px; text-align: left; overflow-wrap: anywhere; }
        label { display: block; margin: 12px 0; }
        button { padding: 8px 16px; }
    </style>
</head>
<body>
    <h1>JSP 기초 보충 실습</h1>
    <ol>
        <li><a href="actions.jsp">Actions와 JavaBean</a></li>
        <li><a href="implicit.jsp">내장 객체와 범위</a></li>
        <li><a href="request.jsp?name=홍길동">Client Request</a></li>
        <li><a href="status.jsp?code=200">HTTP 상태 코드</a></li>
        <li><a href="forms.jsp">폼 처리: GET/POST/다중 선택</a></li>
    </ol>
    <p><a href="<%= request.getContextPath() %>/index.jsp">실습 목록</a></p>
</body>
</html>
```

## Report and Troubleshooting

For each exercise, record URL, input, expected result, actual output, HTTP status, and your explanation.

| Symptom | Check |
|---|---|
| 404 | Context root jsp-foundations, deployment, URL, and file location |
| StudentBean not found | src/main/java package edu.week10, Maven Update, Java 21 |
| JSP returns unexpected 500 | First root cause in the server log, quotes, semicolons, declarations |
| Deliberate 500 | status.jsp?code=500 and error-demo.jsp?fail=yes intentionally demonstrate errors |
| Garbled Korean | JSP and fragment pageEncoding UTF-8; set request encoding before POST access |
| Request attribute disappeared | Determine whether a link or redirect created a new request |

Integrated assignment: add a single-choice course field to the learning registration form. Explain its name, possible values, server validation, safe output, and success/error responses. Membership registration and database storage are not required.

## Downloads and Verification

- [Shared runnable project ZIP](https://drive.google.com/file/d/1sbXCkaxRyxtyOLR_k3F8CXA2AIYfR7gc/view?usp=drivesdk)
- [GitHub source](https://github.com/itlects/2026-2_javaapp/tree/main/Week10/jsp-foundations)
- [Execution verification record](https://github.com/itlects/2026-2_javaapp/blob/main/Week10/jsp-foundations/docs/verification.md)

The shared examples passed 27 request-processing checks on OpenJDK 21 / Tomcat 10.1.61 / Linux. Windows/Eclipse UI and Maven packaging were not directly executed in that environment. The English edition preserves the executable code; translation alone is not a new runtime verification.
