package week05.workbook;
import java.sql.*;
import week05.common.DBConnection;
public class MemberDAO {
 public void findAll() throws SQLException {
  String sql="SELECT id,user_id,name,email FROM member ORDER BY id";
  try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()){
   while(r.next()) System.out.printf("%d | %s | %s | %s%n",r.getInt("id"),r.getString("user_id"),r.getString("name"),r.getString("email"));
  }
 }
 public int insert(String userId,String password,String name,String email)throws SQLException{
  String sql="INSERT INTO member(user_id,password,name,email) VALUES(?,?,?,?)";
  try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql)){
   p.setString(1,userId);p.setString(2,password);p.setString(3,name);p.setString(4,email);return p.executeUpdate();
  }
 }
 public int updateEmail(String userId,String email)throws SQLException{
  try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("UPDATE member SET email=? WHERE user_id=?")){
   p.setString(1,email);p.setString(2,userId);return p.executeUpdate();
  }
 }
 public int delete(String userId)throws SQLException{
  try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement("DELETE FROM member WHERE user_id=?")){
   p.setString(1,userId);return p.executeUpdate();
  }
 }
}