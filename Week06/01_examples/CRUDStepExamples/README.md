# Week06_CRUDStepExamples — 예제 1~4

GUI 없이 콘솔에서 DAO의 CRUD 메서드 하나씩 실행하며 결과를 확인합니다.

| 순서 | 클래스 | 확인할 내용 |
|---|---|---|
| 1 | `Example1_SelectAll` | 5주차 SelectMember와 같은 결과 (8명) |
| 2 | `Example2_Insert` | 100번 회원 등록, `executeUpdate()` 결과 1 / 다시 실행하면 중복 오류 안내 |
| 3 | `Example3_Update` | 100번 회원 수정 전·후 비교, 이메일을 null로 → DB NULL |
| 4 | `Example4_Delete` | 100번 회원 삭제, 한 번 더 실행하면 결과 0 |

2 → 3 → 4 → 3 순서로 실행하면 "대상이 없으면 executeUpdate()가 0" 인 것도 확인할 수 있습니다.
