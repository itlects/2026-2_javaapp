package week05.example;
import java.sql.*;
import week05.common.DBConnection;
public class SelectMember {
 public static void main(String[] args) {
  String sql="SELECT id,user_id,name,email FROM member ORDER BY id";
  try(Connection c=DBConnection.getConnection(); PreparedStatement p=c.prepareStatement(sql); ResultSet r=p.executeQuery()){
   while(r.next()) System.out.printf("%d | %s | %s | %s%n",r.getInt("id"),r.getString("user_id"),r.getString("name"),r.getString("email"));
  }catch(SQLException e){e.printStackTrace();}
 }
}