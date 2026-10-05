package week07.member.util;

import java.sql.*;
public class DBConnection {
    private static final String URL="jdbc:mysql://localhost:3306/javaapp?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
    private static final String USER="root";
    private static final String PASSWORD="1234"; // 환경에 맞게 수정
    public static Connection getConnection() throws SQLException { return DriverManager.getConnection(URL,USER,PASSWORD); }
}
