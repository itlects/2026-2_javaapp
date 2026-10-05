package week05.common;
import java.sql.*;
public class DBConnection {
 private static final String URL="jdbc:mysql://localhost:3306/javaapp";
 private static final String USER="root";
 private static final String PASSWORD="본인의_MYSQL_비밀번호";
 private DBConnection(){}
 public static Connection getConnection() throws SQLException {
  return DriverManager.getConnection(URL,USER,PASSWORD);
 }
}