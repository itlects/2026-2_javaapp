package week06.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/javaapp"
          + "?serverTimezone=Asia/Seoul"
          + "&characterEncoding=UTF-8";

    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
