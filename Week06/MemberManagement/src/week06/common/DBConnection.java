package week06.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQL 연결을 만드는 클래스 (5주차 DBConnection과 같은 역할)
 * - 접속 정보를 한 곳에 모아 두면 다른 클래스는 getConnection()만 호출하면 된다.
 */
public class DBConnection {

    private static final String URL =
        "jdbc:mysql://localhost:3306/javaapp?serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "1234"; // ★ 자신의 MySQL 비밀번호로 수정

    private DBConnection() {
        // 객체를 만들지 않고 DBConnection.getConnection()으로만 사용
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
