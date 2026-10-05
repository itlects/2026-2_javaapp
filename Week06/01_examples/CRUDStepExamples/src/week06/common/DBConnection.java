package week06.common;
import java.sql.*;
public class DBConnection {
  private static final String URL="jdbc:mysql://localhost:3306/javaapp?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
  private static final String USER="root";
  private static final String PASSWORD="1234"; // 자신의 MySQL 비밀번호로 수정
  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
  }
}