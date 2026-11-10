package week06.product;

public class ProductDaoChecker {

    public static void main(String[] args) {

        ProductDAO dao = new ProductDAO();

        String testId = "P099";

        try {
            System.out.println("=== ProductDAO Complete Checker ===");

            System.out.println(
                    dao.findAll() != null
                    ? "[PASS] 1. findAll()"
                    : "[FAIL] 1. findAll()"
            );

            Product sample =
                    dao.findById("P001");

            System.out.println(
                    sample != null
                    ? "[PASS] 2. findById()"
                    : "[FAIL] 2. findById()"
            );

            dao.delete(testId);

            Product inserted = new Product(
                    testId,
                    "검사용상품",
                    "테스트",
                    1000,
                    5,
                    null
            );

            int insertedCount =
                    dao.insert(inserted);

            System.out.println(
                    insertedCount == 1
                    ? "[PASS] 3. insert()"
                    : "[FAIL] 3. insert()"
            );

            Product changed = new Product(
                    testId,
                    "검사용상품수정",
                    "테스트",
                    2000,
                    10,
                    "TestMaker"
            );

            int updatedCount =
                    dao.update(changed);

            Product updated =
                    dao.findById(testId);

            System.out.println(
                    updatedCount == 1
                    && updated != null
                    && updated.price() == 2000
                    && updated.stock() == 10
                    ? "[PASS] 4. update()"
                    : "[FAIL] 4. update()"
            );

            int deletedCount =
                    dao.delete(testId);

            System.out.println(
                    deletedCount == 1
                    && dao.findById(testId) == null
                    ? "[PASS] 5. delete()"
                    : "[FAIL] 5. delete()"
            );

            System.out.println(
                    updated != null
                    ? "[PASS] 6. toProduct()/mapping"
                    : "[FAIL] 6. toProduct()/mapping"
            );

        } catch (Exception e) {

            System.out.println("[FAIL] DB 처리 중 예외 발생");
            e.printStackTrace();
        }
    }
}
