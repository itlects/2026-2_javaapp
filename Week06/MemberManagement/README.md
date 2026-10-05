# Week06_MemberManagement — 기본 통합 완성본

조회(SELECT) · 등록(INSERT) · 수정(UPDATE) · 삭제(DELETE)를 모두 갖춘 Swing 회원관리 프로그램입니다.

## 실행
1. `database/member.sql` 실행 → 2. Connector/J를 Build Path에 추가 → 3. `DBConnection` 비밀번호 수정
4. `week06.member.MemberManagementFrame` 실행

## 구조
| 파일 | 역할 |
|---|---|
| `common/DBConnection.java` | MySQL 연결 생성 |
| `member/Member.java` | 회원 한 명의 데이터 (record) |
| `member/MemberDAO.java` | findAll / findById / nextId / insert / update / delete |
| `member/MemberManagementFrame.java` | 화면, 입력 검증(readForm), 버튼 이벤트, JTable 갱신(loadMembers) |

## 동작 확인 포인트
- 시작 시 5주차 회원 8명이 표시되고, 번호 칸에 다음 번호(9)가 자동 입력된다.
- 필수값 누락, 이메일·전화 형식 오류는 DB에 보내기 전에 경고한다.
- 이메일·전화·학과·학년을 비우면 DB에 NULL로 저장되고, NULL 행을 선택해도 오류가 나지 않는다.
- 같은 번호/아이디 등록 시 안내 메시지를 보여 준다.
- 삭제는 확인 대화상자에서 [예]를 선택한 경우에만 실행된다.
