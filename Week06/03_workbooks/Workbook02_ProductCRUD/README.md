# Week06_Workbook02_ProductCRUD — 상품관리로 전이

회원관리에서 익힌 DAO / PreparedStatement / JTable 갱신 패턴을 5주차 product 테이블에 적용합니다.

1. `database/product.sql` 실행
2. `ProductDAO`의 TODO 1~6 완성 → `ProductDaoChecker`로 PASS 확인
3. `ProductManagementFrame`의 TODO A~E 완성 → 상품 등록·조회·수정·삭제 확인

회원관리와 달라지는 점: 기본키가 문자열(product_id), 가격·재고는 `setInt`/`getInt`, 필수 컬럼 category, NULL 가능 컬럼 maker
