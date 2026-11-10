package week06.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import week06.common.DBConnection;

public class ProductDAO {

    public List<Product> findAll() throws SQLException {
        List<Product> list = new ArrayList<>();

        String sql =
                "SELECT product_id, product_name, category, "
              + "price, stock, maker "
              + "FROM product ORDER BY product_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(toProduct(rs));
            }
        }

        return list;
    }

    public Product findById(String productId) throws SQLException {
        String sql =
                "SELECT product_id, product_name, category, "
              + "price, stock, maker "
              + "FROM product WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return toProduct(rs);
                }
            }
        }

        return null;
    }

    public int insert(Product p) throws SQLException {
        String sql =
                "INSERT INTO product "
              + "(product_id, product_name, category, price, stock, maker) "
              + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.productId());
            ps.setString(2, p.productName());
            ps.setString(3, p.category());
            ps.setInt(4, p.price());
            ps.setInt(5, p.stock());
            setNullableString(ps, 6, p.maker());

            return ps.executeUpdate();
        }
    }

    public int update(Product p) throws SQLException {
        String sql =
                "UPDATE product "
              + "SET product_name = ?, category = ?, price = ?, "
              + "stock = ?, maker = ? "
              + "WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.productName());
            ps.setString(2, p.category());
            ps.setInt(3, p.price());
            ps.setInt(4, p.stock());
            setNullableString(ps, 5, p.maker());
            ps.setString(6, p.productId());

            return ps.executeUpdate();
        }
    }

    public int delete(String productId) throws SQLException {
        String sql = "DELETE FROM product WHERE product_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, productId);
            return ps.executeUpdate();
        }
    }

    private Product toProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getString("product_id"),
                rs.getString("product_name"),
                rs.getString("category"),
                rs.getInt("price"),
                rs.getInt("stock"),
                rs.getString("maker")
        );
    }

    private void setNullableString(
            PreparedStatement ps,
            int index,
            String value) throws SQLException {

        if (value == null || value.isBlank()) {
            ps.setNull(index, Types.VARCHAR);
        } else {
            ps.setString(index, value);
        }
    }
}
