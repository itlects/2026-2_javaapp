package edu.week10;

import java.sql.SQLException;

public final class AuthService {
    private AuthService() { }

    public static String authenticate(String userId, String password)
            throws SQLException {
        return "student".equals(userId) && "Java1234!".equals(password)
                ? "실습학생" : null;
    }
}
