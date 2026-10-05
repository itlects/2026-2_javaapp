package week06.example;

import java.sql.SQLException;

import week06.member.Member;
import week06.member.MemberDAO;

/**
 * 예제 3 — 회원 수정 (UPDATE ... WHERE id=?)
 * 대상이 없으면 executeUpdate()가 0을 돌려준다는 것을 확인한다.
 */
public class Example3_Update {

    public static void main(String[] args) {
        MemberDAO dao = new MemberDAO();
        try {
            Member before = dao.findById(ExampleUtil.EXAMPLE_ID);
            System.out.println("수정 전: " + before);
            if (before == null) {
                System.out.println("수정할 회원이 없습니다. Example2_Insert를 먼저 실행하세요.");
                return;
            }

            Member changed = new Member(
                    before.id(), before.userId(), before.password(), "예제회원(수정)",
                    null,                 // 이메일을 비움 → DB에 NULL 저장
                    "010-1000-2000", before.department(), 2);
            int count = dao.update(changed);

            System.out.println("UPDATE 결과: " + count + "행 처리");
            System.out.println("수정 후: " + dao.findById(ExampleUtil.EXAMPLE_ID));
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }
}
