package week06.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import week06.common.DBConnection;

/**
 * [워크북 2] product 테이블 CRUD
 * 회원관리 MemberDAO를 "복사"하지 말고, 같은 패턴을 상품 테이블에 맞게 다시 작성한다.
 * 컬럼: product_id VARCHAR(10) PK, product_name, category, price INT, stock INT, maker(NULL 가능)
 * TODO 1~6을 완성할 때마다 ProductDaoChecker를 실행한다.
 */
public class ProductDAO {

    public List<Product> findAll() throws SQLException {
        // TODO 1 : 전체 상품을 product_id 순으로 조회
        throw new UnsupportedOperationException("TODO 1: findAll()");
    }

    public Product findById(String productId) throws SQLException {
        // TODO 2 : 상품ID로 한 개 조회, 없으면 null
        throw new UnsupportedOperationException("TODO 2: findById()");
    }

    public int insert(Product p) throws SQLException {
        // TODO 3 : 6개 컬럼 INSERT (가격·재고는 setInt)
        throw new UnsupportedOperationException("TODO 3: insert()");
    }

    public int update(Product p) throws SQLException {
        // TODO 4 : product_id가 같은 상품의 나머지 5개 컬럼 UPDATE (WHERE 필수)
        throw new UnsupportedOperationException("TODO 4: update()");
    }

    public int delete(String productId) throws SQLException {
        // TODO 5 : product_id가 같은 상품 DELETE
        throw new UnsupportedOperationException("TODO 5: delete()");
    }

    private Product toProduct(ResultSet rs) throws SQLException {
        // TODO 6 : ResultSet의 현재 행 → Product 객체 (findAll, findById에서 사용)
        throw new UnsupportedOperationException("TODO 6: toProduct()");
    }
}
