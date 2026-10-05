package week06.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import week06.common.DBConnection;

/**
 * 워크북 1 자가 점검 — MemberDAO의 TODO를 완성한 뒤 실행한다.
 * 각 단계를 PASS / FAIL / TODO 로 표시한다. (점검용 회원 번호 900번을 사용하고 끝나면 지운다)
 */
public class MemberDaoChecker {

    private static final int TEST_ID = 900;
    private static int pass = 0;
    private static int fail = 0;

    public static void main(String[] args) {
        MemberDAO dao = new MemberDAO();
        cleanUp(); // 이전 점검에서 남은 900번 회원 정리

        check("1. findAll() — 5주차 회원 목록 조회", () -> {
            List<Member> list = dao.findAll();
            expect(!list.isEmpty(), "회원이 1명 이상 조회되어야 합니다. (member.sql 실행 여부 확인)");
            expect(list.get(0).userId() != null, "user_id 값을 읽지 못했습니다.");
            System.out.println("     조회된 회원 수: " + list.size());
        });

        check("2. findById() — 번호로 한 명 조회", () -> {
            int firstId = rawInt("SELECT MIN(id) FROM member");
            Member m = dao.findById(firstId);
            expect(m != null && m.id() == firstId, firstId + "번 회원을 찾지 못했습니다.");
            expect(dao.findById(-1) == null, "없는 번호는 null을 돌려주어야 합니다.");
        });

        check("3. insert() — 회원 등록", () -> {
            Member m = new Member(TEST_ID, "check900", "1234", "점검회원",
                    "check900@example.com", null, "컴퓨터소프트웨어과", null);
            expect(dao.insert(m) == 1, "insert()는 1을 돌려주어야 합니다.");
            Member saved = dao.findById(TEST_ID);
            expect(saved != null && "점검회원".equals(saved.name()), "등록한 회원을 다시 조회하지 못했습니다.");
            expect(saved.phone() == null && saved.grade() == null, "빈 값(전화, 학년)은 NULL로 저장되어야 합니다.");
        });

        check("4. update() — 회원 수정", () -> {
            Member m = new Member(TEST_ID, "check900", "5678", "수정회원",
                    null, "010-9000-9000", "AI소프트웨어과", 2);
            expect(dao.update(m) == 1, "update()는 1을 돌려주어야 합니다. (1보다 크면 WHERE 조건을 확인하세요)");
            Member saved = dao.findById(TEST_ID);
            expect("수정회원".equals(saved.name()) && "010-9000-9000".equals(saved.phone()),
                    "수정한 값이 DB에 반영되지 않았습니다.");
            expect(saved.grade() != null && saved.grade() == 2, "학년이 2로 수정되어야 합니다.");
            expect(saved.email() == null, "이메일을 null로 수정하면 DB에도 NULL이어야 합니다.");
            expect(rawInt("SELECT COUNT(*) FROM member WHERE name='수정회원'") == 1,
                    "WHERE 조건이 없어 다른 회원까지 수정된 것 같습니다! (member.sql을 다시 실행해 데이터를 복구하세요)");
        });

        check("5. delete() — 회원 삭제", () -> {
            expect(dao.delete(TEST_ID) == 1, "delete()는 1을 돌려주어야 합니다.");
            expect(dao.findById(TEST_ID) == null, "삭제한 회원이 아직 조회됩니다.");
            expect(dao.delete(TEST_ID) == 0, "없는 회원을 삭제하면 0을 돌려주어야 합니다.");
        });

        cleanUp();
        System.out.println("\n결과: PASS " + pass + " / FAIL " + fail
                + (fail == 0 ? "  → 모든 점검 통과! MemberManagementFrame을 실행해 보세요." : ""));
    }

    // ------------------------------------------------------------ 점검 도구

    private interface Step {
        void run() throws Exception;
    }

    private static void check(String title, Step step) {
        try {
            step.run();
            pass++;
            System.out.println("[PASS] " + title);
        } catch (UnsupportedOperationException e) {
            fail++;
            System.out.println("[TODO] " + title + " — 아직 구현하지 않았습니다. (" + e.getMessage() + ")");
        } catch (AssertionError e) {
            fail++;
            System.out.println("[FAIL] " + title + " — " + e.getMessage());
        } catch (SQLException e) {
            fail++;
            System.out.println("[FAIL] " + title + " — SQL 오류: " + e.getMessage());
            if (title.contains("update") && e instanceof java.sql.SQLIntegrityConstraintViolationException) {
                System.out.println("       → UPDATE 문에 WHERE 조건이 있는지 확인하세요. (모든 행을 같은 값으로 바꾸려다 중복 오류 발생)");
            }
        } catch (Exception e) {
            fail++;
            System.out.println("[FAIL] " + title + " — " + e);
        }
    }

    private static void expect(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    /** 학생 DAO와 무관하게 정수 하나를 조회한다 (각 단계를 독립적으로 점검하기 위해) */
    private static int rawInt(String sql) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }

    /** 학생 DAO와 무관하게 점검용 데이터를 직접 지운다 */
    private static void cleanUp() {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM member WHERE id=? OR user_id=?")) {
            ps.setInt(1, TEST_ID);
            ps.setString(2, "check900");
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("DB 연결 실패: " + e.getMessage());
            System.out.println("→ DBConnection의 비밀번호와 MySQL 실행 여부를 확인하세요.");
            System.exit(1);
        }
    }
}
