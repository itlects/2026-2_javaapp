package week05.example;
import java.sql.*;
import week05.common.DBConnection;
public class JdbcConnectionTest {
 public static void main(String[] args) {
  try(Connection conn=DBConnection.getConnection()){
   System.out.println("MySQL 연결 성공!");
   System.out.println("DB = "+conn.getCatalog());
  }catch(SQLException e){e.printStackTrace();}
 }
}