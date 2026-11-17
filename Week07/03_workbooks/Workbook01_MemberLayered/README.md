# Workbook01_MemberLayered

7주차 계층형 회원관리 워크북 스타터 프로젝트입니다.

학생은 Notion 워크북의 순서에 따라 TODO 1~10을 완성합니다.

## 학습 구조
UI -> Service -> DAO -> MySQL  
MemberDTO는 계층 사이에서 회원 데이터를 전달합니다.

## 개발 환경
- Windows 10
- Java 21
- Eclipse 2025.9
- MySQL Community Server 8.0.46
- MySQL Workbench 8
- MySQL Connector/J

## 시작 방법
1. `database/week07_member_layered.sql` 실행
2. Eclipse > File > Import > Existing Projects into Workspace
3. MySQL Connector/J를 Build Path에 추가
4. `DBConnection.java`의 USER/PASSWORD 확인
5. `MemberManagerFrame.java` 실행
6. Notion 워크북의 TODO 1~10을 순서대로 완성

## TODO
- TODO 1~4: MemberDAO CRUD
- TODO 5~7: MemberService 검증/DAO 호출
- TODO 8: 버튼 이벤트
- TODO 9: JTable 선택행 동기화
- TODO 10: 초기 조회/오류 처리

## 중요
이 프로젝트는 학생이 따라하며 완성하는 스타터 프로젝트입니다.
Notion 튜토리얼의 변수명과 메서드명은 이 프로젝트와 동일하게 유지합니다.
