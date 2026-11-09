package edu.javaapp;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DB {
    private static final Properties CONFIG = new Properties();

    static {
        try (InputStream in = DB.class.getResourceAsStream("/db.properties")) {
            if (in == null) {
                throw new IllegalStateException("db.properties가 없습니다.");
            }
            CONFIG.load(in);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private DB() { }

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(
            CONFIG.getProperty("url"),
            CONFIG.getProperty("user"),
            CONFIG.getProperty("password")
        );
    }
}
