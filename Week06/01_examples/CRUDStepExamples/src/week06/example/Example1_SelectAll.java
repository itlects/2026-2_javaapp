package week06.example;

import java.sql.SQLException;
import java.util.List;

import week06.member.Member;
import week06.member.MemberDAO;

/**
 * 예제 1 — 회원 목록 조회 (SELECT)
 * 5주차 SelectMember와 같은 결과를 DAO를 통해 얻는다.
 */
public class Example1_SelectAll {

    public static void main(String[] args) {
        MemberDAO dao = new MemberDAO();
        try {
            List<Member> members = dao.findAll();
            ExampleUtil.printMembers(members);
            System.out.println("총 " + members.size() + "명");
        } catch (SQLException e) {
            System.out.println("DB 오류: " + e.getMessage());
        }
    }
}
