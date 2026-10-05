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
 * [워크북 1] TODO 1~5를 순서대로 완성하고, 하나 끝낼 때마다 MemberDaoChecker를 실행한다.
 * - GUI 클래스에는 SQL을 쓰지 않고, DB 처리는 모두 이 클래스에 모은다.
 */
public class MemberDAO {

    /** [SELECT] 전체 회원 조회 — executeQuery() + ResultSet */
    public List<Member> findAll() throws SQLException {
        // TODO 1 : 전체 회원을 번호 순으로 조회하여 List<Member>로 돌려준다.
        //  - executeQuery() + while (rs.next()) + toMember(rs)
        throw new UnsupportedOperationException("TODO 1: findAll()");
    }

    /** [SELECT] 번호로 회원 한 명 조회 — 없으면 null */
    public Member findById(int id) throws SQLException {
        // TODO 2 : 번호(id)로 회원 한 명을 조회한다. 없으면 null.
        //  - 결과가 0행 또는 1행이므로 while 대신 if (rs.next())
        throw new UnsupportedOperationException("TODO 2: findById()");
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
        // TODO 3 : 회원 8개 컬럼을 INSERT하고 처리된 행 수를 돌려준다.
        //  - 컬럼 목록을 반드시 적는다: INSERT INTO member (id, user_id, ...) VALUES (?, ...)
        //  - grade는 setGrade() 사용
        throw new UnsupportedOperationException("TODO 3: insert()");
    }

    /** [UPDATE] 회원 수정 — WHERE id=? 가 없으면 모든 행이 바뀌므로 반드시 조건을 쓴다 */
    public int update(Member m) throws SQLException {
        // TODO 4 : id가 같은 회원의 나머지 7개 컬럼을 UPDATE한다.
        //  - WHERE 조건 필수!
        throw new UnsupportedOperationException("TODO 4: update()");
    }

    /** [DELETE] 회원 삭제 */
    public int delete(int id) throws SQLException {
        // TODO 5 : id가 같은 회원을 DELETE한다.
        throw new UnsupportedOperationException("TODO 5: delete()");
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
