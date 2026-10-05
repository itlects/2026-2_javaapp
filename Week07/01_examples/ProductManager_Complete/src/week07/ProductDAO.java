package week07;
import java.sql.*; import java.util.*;
public class ProductDAO {
 public List<Product> findAll() throws SQLException {
  String sql="SELECT product_id,name,category,price,stock FROM products ORDER BY product_id";
  List<Product> list=new ArrayList<>();
  try(Connection c=DB.getConnection(); PreparedStatement ps=c.prepareStatement(sql); ResultSet rs=ps.executeQuery()){
   while(rs.next()) list.add(new Product(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5)));
  } return list;
 }
 public int insert(String n,String cty,int p,int s)throws SQLException{
  String sql="INSERT INTO products(name,category,price,stock) VALUES(?,?,?,?)";
  try(Connection c=DB.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setString(1,n);ps.setString(2,cty);ps.setInt(3,p);ps.setInt(4,s);return ps.executeUpdate();}
 }
 public int update(int id,String n,String cty,int p,int s)throws SQLException{
  String sql="UPDATE products SET name=?,category=?,price=?,stock=? WHERE product_id=?";
  try(Connection c=DB.getConnection();PreparedStatement ps=c.prepareStatement(sql)){ps.setString(1,n);ps.setString(2,cty);ps.setInt(3,p);ps.setInt(4,s);ps.setInt(5,id);return ps.executeUpdate();}
 }
 public int delete(int id)throws SQLException{
  try(Connection c=DB.getConnection();PreparedStatement ps=c.prepareStatement("DELETE FROM products WHERE product_id=?")){ps.setInt(1,id);return ps.executeUpdate();}
 }
}