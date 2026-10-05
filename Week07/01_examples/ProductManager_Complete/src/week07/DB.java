package week07;
import java.sql.*;
public class DB {
 private static final String URL="jdbc:mysql://localhost:3306/javaapp?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
 private static final String USER="root";
 private static final String PW="1234"; // 자신의 MySQL 암호로 수정
 public static Connection getConnection() throws SQLException { return DriverManager.getConnection(URL,USER,PW); }
}