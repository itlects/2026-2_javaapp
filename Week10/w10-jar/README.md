# w10 실행 가능한 JAR 버전

Servlet·JSP 기본 예제를 내장 Tomcat 10.1.62와 함께 실행합니다. 기존 `Week10/w10` WAR를 JAR 내부에 포함합니다. 기존 WAR 프로젝트의 packaging을 jar로 바꾸는 방식이 아닙니다. MySQL은 필요하지 않습니다.

## Windows에서 완성본 실행

1. JDK 21을 설치하고 새 명령 프롬프트(cmd)에서 `java -version`을 확인합니다.
2. 실행 묶음 ZIP을 예를 들어 `C:\java-labs\w10-jar`에 압축 해제합니다.
3. Eclipse Tomcat 등 8080을 사용하는 서버를 중지합니다.
4. 다음 명령을 실행하거나 `run-w10.bat`를 더블클릭합니다.

```bat
cd /d C:\java-labs\w10-jar
java -jar w10.jar
```

5. `W10 READY: http://localhost:8080/w10/login`을 확인한 뒤 브라우저에서 해당 주소를 엽니다.
6. `student / Java1234!`로 로그인하고 로그아웃 후 `/w10/home` 재접근이 차단되는지 확인합니다.
7. 종료는 실행 중인 명령 창에서 **Ctrl+C**입니다. JAR만 더블클릭하면 로그가 보이지 않으므로 cmd 또는 배치 파일을 사용하세요.

다른 포트로 실행하려면:

```bat
java -jar w10.jar 8081
```

이 경우 접속 주소는 `http://localhost:8081/w10/login`입니다. `run-w10.bat 8081`도 가능합니다. 실행용 JAR에는 필요한 라이브러리가 포함되어 있으며 실행할 때 Maven 다운로드나 별도 Tomcat 설치가 필요하지 않습니다. JDK는 별도 설치합니다.

## Eclipse에서 소스 빌드

소스 ZIP의 `Week10/w10`과 `Week10/w10-jar` 폴더는 같은 부모 폴더 아래에 유지하세요.

1. File → Import → Maven → Existing Maven Projects에서 두 프로젝트를 함께 선택합니다.
2. 두 프로젝트의 JDK를 21로 설정하고 Maven → Update Project를 실행합니다.
3. 먼저 `w10` 우클릭 → Run As → Maven build... → Goals `clean package`를 실행합니다. `w10/target/w10.war`가 필요합니다.
4. 이어서 `w10-jar`에서 Goals `clean package`를 실행합니다.
5. 결과는 `w10-jar/target/w10.jar`입니다. cmd에서 `java -jar target\w10.jar`로 실행합니다. 이 프로젝트는 Run on Server가 아닌 main 진입점으로 서버를 시작합니다.
6. 웹 소스를 수정했다면 반드시 3 → 4 순서로 두 프로젝트를 다시 빌드합니다. 이전 WAR를 내장한 JAR를 재사용하지 마세요.

명령 프롬프트에 Maven이 별도 설치되어 있으면 다음도 가능합니다.

```bat
mvn -f ..\w10\pom.xml clean package
mvn clean package
java -jar target\w10.jar
```

`build-windows.bat`는 위의 두 빌드와 실행용 JAR 복사를 수행합니다. 빌드에는 JDK 21, Maven과 최초 의존성 다운로드를 위한 인터넷이 필요합니다. Eclipse Maven만 사용하는 학생은 위의 Run As 메뉴를 이용하세요.

## 구현 원리

- `pom.xml`: packaging jar, 내장 Tomcat과 JSP 엔진 의존성, Maven Shade로 실행 가능한 JAR 생성.
- `W10Launcher.java`: main 메서드, JAR 안의 `embedded/w10.war`를 임시 폴더로 복사, `/w10` 컨텍스트 배포, 서버 시작.
- 기존 WAR의 Java 클래스와 JSP/JSTL은 웹 앱 클래스 로더에서 실행됩니다. JSP를 일반 JAR 리소스로 읽기만 하는 구성이 아닙니다.
- 기본 바인딩은 `127.0.0.1`로, 실행한 PC에서 학습하는 용도입니다. 로그인 상태는 서버 메모리에 저장되므로 종료 후 다시 로그인해야 합니다. 임시 실행 폴더는 정상 종료 시 정리됩니다.
- 배포 JAR 안에 내장된 WAR와 오픈소스 의존성의 라이선스/NOTICE가 포함됩니다.

## 오류 해결

| 증상 | 확인 |
| --- | --- |
| java 명령을 찾을 수 없음 | JDK 21 설치와 PATH, 새 cmd 실행 |
| UnsupportedClassVersionError | `java -version`이 21인지 확인 |
| Address already in use | 기존 서버 중지 또는 `java -jar w10.jar 8081` |
| Missing embedded/w10.war | w10 WAR를 먼저 빌드한 후 w10-jar 재빌드 |
| 로그인 POST 403 | `/w10/login`에서 폼을 새로 연 뒤 제출 |
| 실행 중인 창을 닫자 접속 불가 | 서버 종료가 정상적으로 반영된 것, 다시 실행 |

검증 기록은 `verification.md`를 확인하세요. Windows 배치/GUI 직접 실행 검증과 Linux JDK 21 실행 검증을 구분합니다.
