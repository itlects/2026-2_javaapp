package week06.example;

import java.sql.SQLException;

import week06.member.MemberDAO;

/**
 * 예제 4 — 회원 삭제 (DELETE ... WHERE id=?)
 * GUI에서는 삭제 전에 JOptionPane.showConfirmDialog()로 한 번 더 확인한다.
 */
public class Example4_Delete {

    public static void main(String[] args) {
        MemberDAO dao = new MemberDAO();
        try {
            int count = dao.delete(ExampleUtil.EXAMPLE_ID);
            System.out.println("DELETE 결과: " + count + "행 처리");
            if (count == 0) {
                System.out.println(ExampleUtil.EXAMPLE_ID + "번 회원이 없어 삭제된 행이 없습니다.");
            }
            System.out.println("삭제 확인(findById): " + dao.findById(ExampleUtil.EXAMPLE_ID));
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }
}
