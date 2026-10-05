package week06.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import week06.common.DBConnection;

/**
 * member 테이블의 SELECT / INSERT / UPDATE / DELETE를 담당하는 DAO
 * [실습] SELECT(findAll, findById, nextId)는 완성되어 있다. INSERT / UPDATE / DELETE를 완성한다.
 * - GUI 클래스에는 SQL을 쓰지 않고, DB 처리는 모두 이 클래스에 모은다.
 */
public class MemberDAO {

    /** [SELECT] 전체 회원 조회 — executeQuery() + ResultSet */
    public List<Member> findAll() throws SQLException {
        List<Member> list = new ArrayList<>();
        String sql = "SELECT id, user_id, password, name, email, phone, department, grade "
                   + "FROM member ORDER BY id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {          // 다음 행이 있으면 true, 커서를 한 행 이동
                list.add(toMember(rs));
            }
        }
        return list;
    }

    /** [SELECT] 번호로 회원 한 명 조회 — 없으면 null */
    public Member findById(int id) throws SQLException {
        String sql = "SELECT id, user_id, password, name, email, phone, department, grade "
                   + "FROM member WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {         // 결과가 0행 또는 1행이므로 while 대신 if
                    return toMember(rs);
                }
            }
        }
        return null;
    }

    /** [SELECT] 다음 회원 번호 — 가장 큰 id + 1 (회원이 없으면 1) */
    public int nextId() throws SQLException {
        String sql = "SELECT COALESCE(MAX(id), 0) + 1 FROM member";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            return rs.getInt(1);
        }
    }

    /** [INSERT] 회원 등록 — executeUpdate()는 처리된 행 수를 돌려준다 */
    public int insert(Member m) throws SQLException {
        // TODO 실습 3-1 : 회원 등록
        // ① SQL  : INSERT INTO member (id, user_id, password, name, email, phone, department, grade)
        //           VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        // ② try-with-resources로 Connection, PreparedStatement 생성
        // ③ ps.setInt(1, m.id()); ps.setString(2, m.userId()); ... 순서대로 8개 설정
        //    학년(8번)은 setGrade(ps, 8, m.grade()); 를 사용 (null이면 NULL 저장)
        // ④ return ps.executeUpdate();   // 처리된 행 수
        throw new UnsupportedOperationException("TODO 실습 3-1: MemberDAO.insert() 구현");
    }

    /** [UPDATE] 회원 수정 — WHERE id=? 가 없으면 모든 행이 바뀌므로 반드시 조건을 쓴다 */
    public int update(Member m) throws SQLException {
        // TODO 실습 4-1 : 회원 수정
        // ① SQL  : UPDATE member SET user_id=?, password=?, name=?, email=?, phone=?, department=?, grade=?
        //           WHERE id=?
        //    ★ WHERE id=? 를 빠뜨리면 모든 회원이 같은 값으로 바뀐다!
        // ② ?의 번호 순서대로 값 설정 (마지막 8번이 WHERE의 id)
        // ③ return ps.executeUpdate();   // 대상이 없으면 0
        throw new UnsupportedOperationException("TODO 실습 4-1: MemberDAO.update() 구현");
    }

    /** [DELETE] 회원 삭제 */
    public int delete(int id) throws SQLException {
        // TODO 실습 5-1 : 회원 삭제
        // ① SQL  : DELETE FROM member WHERE id=?
        // ② ps.setInt(1, id);
        // ③ return ps.executeUpdate();
        throw new UnsupportedOperationException("TODO 실습 5-1: MemberDAO.delete() 구현");
    }

    // ---------------------------------------------------------------- 보조 메서드

    /** ResultSet의 현재 행을 Member 객체로 변환 */
    private Member toMember(ResultSet rs) throws SQLException {
        return new Member(
            rs.getInt("id"),
            rs.getString("user_id"),
            rs.getString("password"),
            rs.getString("name"),
            rs.getString("email"),                     // NULL이면 null
            rs.getString("phone"),
            rs.getString("department"),
            rs.getObject("grade", Integer.class));     // NULL이면 null (getInt는 0을 돌려줌)
    }

    /** 학년이 null이면 DB에 NULL, 아니면 정수로 저장 */
    private void setGrade(PreparedStatement ps, int index, Integer grade) throws SQLException {
        if (grade == null) {
            ps.setNull(index, Types.INTEGER);
        } else {
            ps.setInt(index, grade);
        }
    }
}
