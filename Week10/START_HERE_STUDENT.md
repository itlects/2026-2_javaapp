# Week 10 — Student Example Projects / 학생용 예제 프로젝트

## Choose a project / 프로젝트 선택

| Folder | Exercise | Database | Start URL |
|---|---|---|---|
| jsp-foundations | JSP Actions, implicit objects, request, HTTP status, forms | None | http://localhost:8080/jsp-foundations/index.jsp |
| w10 | Servlet/Session login, GET/POST, forward/redirect | None | http://localhost:8080/w10/login |
| learning-portal | MySQL member authentication learning portal | MySQL 8.0.46 | http://localhost:8080/learning-portal/login |

Environment: Windows 10 / Eclipse IDE for Enterprise Java and Web Developers 2025-09 / JDK 21 / Tomcat 10.1.x / UTF-8. For learning-portal, use MySQL Community Server 8.0.46 and Workbench 8.

## Run the source in Eclipse

1. Download and extract Week10_Student_Examples.zip. Extract the ZIP first; do not import the ZIP as a project.
2. Eclipse → File → Import → Maven → Existing Maven Projects.
3. Select the extracted Week10 folder. Choose one or all three pom.xml projects and Finish.
4. Right-click each project → Maven → Update Project. An internet connection is needed for the initial Maven dependency download.
5. Set JDK/Compiler 21 and Tomcat 10.1 runtime. Check Dynamic Web Module 6.0 and each project's Context root (same as folder name).
6. Run As → Run on Server → Tomcat 10.1. Open the selected project's URL in the table above. Run JSP through Tomcat, not by double-clicking the file.
7. w10 uses student / Java1234! without a DB. learning-portal uses the same credentials after data/setup.sql and its DB environment variables have been configured. Follow docs/workbook.md for those settings.

## Run the ready-to-deploy JSP supplement WAR

1. Install JDK 21 and unpack Tomcat 10.1.
2. Copy jsp-foundations.war into Tomcat's webapps folder.
3. Start Tomcat (Windows: bin/catalina.bat run).
4. Open http://localhost:8080/jsp-foundations/index.jsp.
5. To stop the foreground server, press Ctrl+C. Do not start this manual server while an Eclipse Tomcat is using the same port.

The WAR includes the compiled StudentBean and the JSP examples. It needs neither MySQL nor Maven to run after JDK 21 and Tomcat are installed. Maven is still needed for the Eclipse source-import path described above.

## Where to study

- jsp-foundations/docs/en/index.md: English supplement and five tutorials.
- jsp-foundations/docs/index.md: Korean supplement.
- docs/lecture.md and docs/workbook.md: original Servlet/Session lecture and MySQL workbook.
- jsp-foundations/docs/verification.md: validation scope; Windows/Eclipse UI has not been directly tested here.

The English tutorials retain Korean names and UI strings for UTF-8 testing and explain the expected text in English. The 400/403/404/405/500 responses in the status exercise are intentional.

## Quick checks / 실행 확인

- index.jsp opens the five-topic menu.
- actions.jsp shows the included banner and bean value.
- the POST form accepts 김학생 and two checked topics.
- logout in w10 prevents a fresh /home request.
- for MySQL connection errors, follow the workbook rather than changing JSP files.

## 학생 안내

ZIP을 압축 해제한 후 Eclipse에서 Maven 프로젝트로 가져오세요. JSP 기초 실습은 jsp-foundations, 세션 로그인은 w10, DB 인증은 learning-portal을 선택합니다. 바로 실행만 할 때에는 별도 jsp-foundations.war를 Tomcat webapps에 복사합니다. 코드 편집·학습에는 ZIP의 원본 프로젝트를 사용하세요.

## Ready main example WAR

Copy w10.war into Tomcat 10.1 webapps, start Tomcat with JDK 21, and open http://localhost:8080/w10/login. Use student / Java1234!. For an existing Eclipse workspace, remove the old module from the server and import Week10/w10 as a Maven project.
