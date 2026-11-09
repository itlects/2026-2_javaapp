# 검증 범위

- Java 21 런타임 + Eclipse Compiler 3.38.0의 -21 옵션으로 3개 프로젝트의 DAO/Servlet 컴파일 성공. serialVersionUID 관련 비기능 경고 1건씩 존재.
- Tomcat 10.1.61에서 별도 검증용 요청으로 각 JSP를 forward하여 HTTP 200, 한글 표시, 사용자 입력 이스케이프, CSRF 폼 필드 생성 확인.
- JSP 검증에는 설치되어 있던 JSTL 3.0.0/3.0.1 사용. 검증용 진입 페이지는 배포 자료에 포함하지 않음.
- Maven 패키징은 저장소 DNS 접근 실패로 완료하지 못함. pom.xml XML 구문과 의존성/소스 경로 확인.
- MySQL 8.0.46 서버 및 Windows 10/Eclipse GUI가 이 실행환경에 없어 실제 DB CRUD 통합과 해당 OS에서의 실행은 미검증. 워크북의 동작 확인 절차로 수업 환경에서 검증해야 함.
- 데이터베이스 성공을 가정한 캡처나 실제 CRUD 완료 주장은 포함하지 않음.
