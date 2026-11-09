package edu.week10;

import java.sql.*;
import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public final class AuthService {
    private AuthService() { }

    public static String authenticate(String userId, String password)
            throws SQLException {
        if (userId == null || password == null
                || userId.length() > 40 || password.length() > 128) {
            return null;
        }
        String sql = "SELECT display_name, password_hash FROM members WHERE user_id = ?";
        try (Connection con = DriverManager.getConnection(
                    required("W10_DB_URL"), required("W10_DB_USER"),
                    required("W10_DB_PASSWORD"));
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                byte[] stored = HexFormat.of().parseHex(rs.getString("password_hash"));
                if (MessageDigest.isEqual(stored, sha256(password))) {
                    return rs.getString("display_name");
                }
                return null;
            }
        }
    }

    private static String required(String name) throws SQLException {
        String value = System.getenv(name);
        if (value == null || value.isBlank()) {
            throw new SQLException("Missing environment variable: " + name);
        }
        return value;
    }

    private static byte[] sha256(String value) throws SQLException {
        try {
            return MessageDigest.getInstance("SHA-256")
                    .digest(value.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            throw new SQLException(ex);
        }
    }
}
