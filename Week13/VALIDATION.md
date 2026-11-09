# 검증 상태

작성 환경 Java 21 확인. Maven 및 MySQL 미설치, Maven Central 네트워크 접속 제한으로 빌드·자동 테스트·실제 MySQL 실행 미수행.
정적 점검: pom XML, Java 패키지/파일명, View 이름과 템플릿, 리소스 경로, SQL 샘플과 예상 결과 대응.
제공 테스트: MemberWebTest (H2). 실행은 mvn test.
강의실 MySQL 8.0.46에서 스키마·계정·초기 데이터 5명·검색 2명·상세·404·400 수동 검증 필요.
자료의 화면 표와 결과는 예상값이며 실제 실행 캡처가 아님.
