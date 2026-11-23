# w10 노션 본문 따라 하기 점검

점검일: 2026-11-24 (Asia/Seoul). 대상: 10주차 국문 강의 페이지 본문.

노션 코드 블록을 직접 추출하여 빈 w10 프로젝트를 구성했습니다. 다운로드 프로젝트나 기존 배포 WAR를 복사하여 테스트한 결과가 아닙니다.

## 결과

- 기존 프로젝트명 표기는 w10으로 적용되어 있으며 예전 명칭은 본문에 남아 있지 않습니다.
- 본문의 파일 1~15를 추출했습니다. Java 8개, JSP 4개, XML 2개, CSS 1개입니다.
- 실행 코드 15개가 GitHub Week10/w10과 일치합니다(파일 끝 공백 제외).
- 완전한 JDK 21 환경에서 Maven 3.9.9 clean package가 BUILD SUCCESS로 끝났습니다.
- 생성된 target/w10.war의 Servlet 클래스 8개와 JSTL API·구현 라이브러리 포함, Servlet API provided 제외를 확인했습니다.
- 이 Maven WAR를 새로 Tomcat 10.1.61에 배포하여 아래 19개 HTTP 항목이 모두 통과했습니다.

## 본문 보완

처음 작성하는 학생을 위해 패키지·폴더·클래스·JSP 생성 순서, 생성된 기본 코드의 전체 교체, 15개 파일 작성 후 서버 실행, 전체 URL별 기대 결과, 예전 Eclipse 서버 모듈 제거 방법을 추가했습니다. 브라우저 경로 /w10과 애너테이션 내부 매핑 /login을 구분하도록 설명했습니다. START_HERE_STUDENT.md의 잘못된 자동 링크도 바로잡았습니다. pom.xml의 name도 저장소와 맞췄습니다.

## HTTP 실행 결과

| 점검 | 결과 |
|---|---|
| T01 protected | PASS |
| login JSP compiled | PASS |
| T03 invalid password | PASS |
| T02 login PRG | PASS |
| session id rotated | PASS |
| home JSP compiled | PASS |
| T04 UTF8 GET | PASS |
| T05 UTF8 POST | PASS |
| T06 forward | PASS |
| T07 redirect | PASS |
| T10 CSRF | PASS |
| T11 logout GET | PASS |
| T12 direct JSP | PASS |
| T14 escaped output | PASS |
| T08 logout | PASS |
| T13 post logout access | PASS |
| root welcome redirects to login | PASS |
| stylesheet loaded | PASS |
| unknown route is 404 | PASS |

## 실제 검증 환경과 한계

Linux, Microsoft OpenJDK 21.0.13, Maven 3.9.9, Tomcat 10.1.61에서 코드 추출·컴파일·WAR 패키징·HTTP 동작을 확인했습니다. Windows 10의 Eclipse 2025-09 GUI를 직접 조작하여 확인한 것은 아닙니다. GUI 설정은 본문 안내대로 Java 21, Web Module 6.0, Tomcat 10.1, Context root w10을 지정해야 합니다. MySQL 확장 워크북의 DB 연결은 이번 기본 w10 검증 범위에 포함하지 않습니다. 실제 20분 만료 대기는 수행하지 않았으며 수업용 짧은 만료 확인 절차는 본문에 별도로 제시되어 있습니다.

환경의 네트워크 설정과 다운로드 캐시는 검증 실행에만 사용했으며 학생 프로젝트에 포함하지 않았습니다.
