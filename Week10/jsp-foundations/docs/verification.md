# JSP 보충 실습 검증

검증: OpenJDK 21, ECJ 3.38(Java 21 모드), Tomcat 10.1.61, Linux.

JavaBean 컴파일 및 JSP의 실제 요청 처리에서 27개 점검을 통과했습니다. 포함 배너, Bean 설정·출력, JSP Forward, Redirect 302와 후속 요청, 내장 객체, 새 요청의 범위, exception 오류 페이지, 한글 GET/POST, 체크박스 다중값·미선택, 이름 누락·공백·길이 검증, HTML 이스케이프, 200/400/403/404/500 응답, 405와 Allow, 미지원 상태 코드 처리를 확인했습니다.

Windows 10/Eclipse UI 및 Maven 패키징은 이 환경에서 직접 실행하지 않았습니다. DB는 사용하지 않습니다. 세부 결과는 verification-results.json에 있습니다.
