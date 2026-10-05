package week06.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import week06.common.DBConnection;

/**
 * 워크북 2 자가 점검 — ProductDAO의 TODO를 완성한 뒤 실행한다.
 * (점검용 상품ID P900을 사용하고 끝나면 지운다)
 */
public class ProductDaoChecker {

    private static final String TEST_ID = "P900";
    private static int pass = 0;
    private static int fail = 0;

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO();
        cleanUp();

        check("1. findAll() — 5주차 상품 목록 조회", () -> {
            List<Product> list = dao.findAll();
            expect(!list.isEmpty(), "상품이 1개 이상 조회되어야 합니다. (product.sql 실행 여부 확인)");
            expect(list.get(0).category() != null, "category 값을 읽지 못했습니다.");
            System.out.println("     조회된 상품 수: " + list.size());
        });

        check("2. findById() — 상품ID로 한 개 조회", () -> {
            String firstId = rawString("SELECT MIN(product_id) FROM product");
            Product p = dao.findById(firstId);
            expect(p != null && p.productId().equals(firstId), firstId + " 상품을 찾지 못했습니다.");
            expect(dao.findById("NONE") == null, "없는 상품ID는 null을 돌려주어야 합니다.");
        });

        check("3. insert() — 상품 등록", () -> {
            expect(dao.insert(new Product(TEST_ID, "점검상품", "주변기기", 15000, 10, null)) == 1,
                    "insert()는 1을 돌려주어야 합니다.");
            Product saved = dao.findById(TEST_ID);
            expect(saved != null && saved.price() == 15000 && saved.stock() == 10, "등록한 값이 다릅니다.");
            expect(saved.maker() == null, "빈 제조사는 NULL로 저장되어야 합니다.");
        });

        check("4. update() — 상품 수정", () -> {
            expect(dao.update(new Product(TEST_ID, "수정상품", "액세서리", 17000, 7, "TestMaker")) == 1,
                    "update()는 1을 돌려주어야 합니다. (1보다 크면 WHERE 조건을 확인하세요)");
            Product saved = dao.findById(TEST_ID);
            expect("수정상품".equals(saved.productName()) && saved.price() == 17000 && saved.stock() == 7,
                    "수정한 값이 DB에 반영되지 않았습니다.");
            expect(rawString("SELECT COUNT(*) FROM product WHERE product_name='수정상품'").equals("1"),
                    "WHERE 조건이 없어 다른 상품까지 수정된 것 같습니다! (product.sql의 DROP 주석을 풀고 다시 실행해 복구하세요)");
        });

        check("5. delete() — 상품 삭제", () -> {
            expect(dao.delete(TEST_ID) == 1, "delete()는 1을 돌려주어야 합니다.");
            expect(dao.findById(TEST_ID) == null, "삭제한 상품이 아직 조회됩니다.");
        });

        cleanUp();
        System.out.println("\n결과: PASS " + pass + " / FAIL " + fail
                + (fail == 0 ? "  → 모든 점검 통과! ProductManagementFrame을 완성해 보세요." : ""));
    }

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

    /** 학생 DAO와 무관하게 값 하나를 조회한다 */
    private static String rawString(String sql) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             java.sql.ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getString(1);
        }
    }

    private static void cleanUp() {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM product WHERE product_id=?")) {
            ps.setString(1, TEST_ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("DB 연결 실패: " + e.getMessage());
            System.out.println("→ DBConnection의 비밀번호와 MySQL 실행 여부를 확인하세요.");
            System.exit(1);
        }
    }
}
