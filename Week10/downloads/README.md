# Week 10 — Student Downloads / 학생 예제 다운로드

| Download | Contents | Use |
|---|---|---|
| [Week10_Student_Examples.zip](https://github.com/itlects/2026-2_javaapp/raw/refs/heads/main/Week10/downloads/Week10_Student_Examples.zip) | All three projects, Korean/English notes, SQL, reports, START_HERE_STUDENT.md | Extract, then Eclipse → Import → Maven → Existing Maven Projects |
| [w10.war](https://github.com/itlects/2026-2_javaapp/raw/refs/heads/main/Week10/downloads/w10.war) | Main Servlet/Session example with compiled classes and JSTL | Copy to Tomcat 10.1 webapps, then open /w10/login |
| [jsp-foundations.war](https://github.com/itlects/2026-2_javaapp/raw/refs/heads/main/Week10/downloads/jsp-foundations.war) | Compiled JavaBean and JSP supplement | Copy to Tomcat 10.1 webapps, start server, open /jsp-foundations/index.jsp |
| [SHA256SUMS.txt](SHA256SUMS.txt) | Download checksums | Optional integrity check |

Google Drive alternatives: [source ZIP](https://drive.google.com/file/d/13zCXK2ped8UX1ccoVXL3ToGXP7RvNeJ7/view?usp=drivesdk), [ready WAR](https://drive.google.com/file/d/16e1_aqDP599xoGyTze6ry5RQPdpzRoQt/view?usp=drivesdk).

## Project selection

- jsp-foundations: Actions, implicit objects, request inspection, statuses, forms. No DB.
- servlet-login: Servlet/Session login and logout. No DB. Account student / Java1234!.
- learning-portal: MySQL member authentication. Run data/setup.sql and configure DB variables using docs/workbook.md first.

## Environment and quick start

Windows 10 / JDK 21 / Eclipse Enterprise 2025-09 / Tomcat 10.1 / UTF-8. MySQL Community Server 8.0.46 and Workbench 8 are needed only for the DB project.

Source path: extract ZIP → open Week10/START_HERE_STUDENT.md → import project(s) by pom.xml → Maven Update → choose JDK 21/Tomcat 10.1 → Run on Server. Initial Maven dependency downloads need internet access.

WAR path: copy jsp-foundations.war to Tomcat webapps → bin/catalina.bat run → http://localhost:8080/jsp-foundations/index.jsp. Do not run duplicate Tomcat instances on port 8080. The JSP supplement WAR needs no Maven or DB at runtime.

The English supplement retains Korean UI text for UTF-8 exercises. The packaged WAR passed 27 HTTP checks on OpenJDK 21/Tomcat 10.1.61/Linux. Windows/Eclipse UI and Maven packaging were not directly tested in that environment; the WAR was assembled from JSP resources and an ECJ-compiled JavaBean.

학생은 ZIP을 압축 해제한 뒤 Maven 프로젝트로 가져오세요. 실행만 할 때는 WAR를 Tomcat webapps에 복사하세요. 자세한 프로젝트별 URL과 환경 설정은 [START_HERE_STUDENT.md](../START_HERE_STUDENT.md)에 있습니다.

The main Eclipse project, Maven artifactId, WAR name, and Context root are `w10`. If an older servlet-login project is already imported, remove its old module from the Eclipse server and import the new w10 folder. The login URL is http://localhost:8080/w10/login and the demonstration credentials remain student / Java1234!. The renamed context passed 16 HTTP checks on OpenJDK 21/Tomcat 10.1.61.
