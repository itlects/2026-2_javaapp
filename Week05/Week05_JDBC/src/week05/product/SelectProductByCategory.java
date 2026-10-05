package week05.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import week05.common.DBConnection;

public class SelectProductByCategory {
    public static void main(String[] args) {
        String sql =
            "SELECT product_id, product_name, category, price, stock "
          + "FROM product WHERE category = ? ORDER BY price";

        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("조회할 상품 분류 > ");
            String category = sc.nextLine();

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, category);

                try (ResultSet rs = pstmt.executeQuery()) {
                    boolean found = false;
                    while (rs.next()) {
                        found = true;
                        System.out.printf("%s | %s | %,d원 | 재고 %d%n",
                            rs.getString("product_id"),
                            rs.getString("product_name"),
                            rs.getInt("price"),
                            rs.getInt("stock"));
                    }
                    if (!found) {
                        System.out.println("조회 결과가 없습니다.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
