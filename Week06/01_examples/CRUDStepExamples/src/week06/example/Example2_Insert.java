package week06.example;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import week06.member.Member;
import week06.member.MemberDAO;

/**
 * 예제 2 — 회원 등록 (INSERT)
 * 흐름: 값 준비 → Member 객체 → dao.insert() → executeUpdate() 결과(처리된 행 수) 확인 → 다시 조회
 */
public class Example2_Insert {

    public static void main(String[] args) {
        MemberDAO dao = new MemberDAO();
        Member member = new Member(
                ExampleUtil.EXAMPLE_ID, "example100", "1234", "예제회원",
                "example100@example.com", "010-1000-1000", "컴퓨터소프트웨어과", 1);
        try {
            int count = dao.insert(member);
            System.out.println("INSERT 결과: " + count + "행 처리");
            System.out.println("등록 확인: " + dao.findById(ExampleUtil.EXAMPLE_ID));
        } catch (SQLIntegrityConstraintViolationException e) {
            // 기본키(id) 또는 UNIQUE(user_id) 중복
            System.out.println("이미 " + ExampleUtil.EXAMPLE_ID + "번 회원(또는 같은 아이디)이 있습니다. "
                    + "Example4_Delete를 실행한 후 다시 실행하세요.");
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }
}
