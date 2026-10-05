package week06.product;

/**
 * 상품 한 개의 데이터 (5주차 product 테이블의 한 행)
 * - maker(제조사)는 DB에서 NULL일 수 있다.
 */
public record Product(
        String productId,
        String productName,
        String category,
        int price,
        int stock,
        String maker) {
}
