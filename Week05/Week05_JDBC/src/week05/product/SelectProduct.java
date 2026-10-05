package week05.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import week05.common.DBConnection;

public class SelectProduct {
    public static void main(String[] args) {
        String sql =
            "SELECT product_id, product_name, category, price, stock, maker "
          + "FROM product ORDER BY product_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("상품ID | 상품명 | 분류 | 가격 | 재고 | 제조사");
            System.out.println("------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%s | %s | %s | %,d | %d | %s%n",
                    rs.getString("product_id"),
                    rs.getString("product_name"),
                    rs.getString("category"),
                    rs.getInt("price"),
                    rs.getInt("stock"),
                    rs.getString("maker"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
