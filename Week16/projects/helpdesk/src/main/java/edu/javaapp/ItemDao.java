package edu.javaapp;

import java.sql.*;
import java.util.*;

public class ItemDao {
    public static class Item {
        private final long id;
        private final String title, owner, status;
        public Item(long id, String title, String owner, String status) {
            this.id = id;
            this.title = title;
            this.owner = owner;
            this.status = status;
        }
        public long getId() { return id; }
        public String getTitle() { return title; }
        public String getOwner() { return owner; }
        public String getStatus() { return status; }
    }

    private Connection open() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC driver missing", e);
        }
        String password = System.getenv("DB_PASSWORD");
        if (password == null || password.isBlank()) {
            throw new SQLException("DB_PASSWORD is required");
        }
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/week16_helpdesk?characterEncoding=UTF-8&connectionTimeZone=Asia/Seoul",
            "javaapp16", password);
    }

    public List<Item> list(String query) throws SQLException {
        List<Item> result = new ArrayList<>();
        String sql = "SELECT id,title,owner,status FROM items "
                   + "WHERE LOCATE(?,title)>0 ORDER BY id DESC";
        try (Connection c = open(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, query);
            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    result.add(new Item(rs.getLong("id"), rs.getString("title"),
                        rs.getString("owner"), rs.getString("status")));
                }
            }
        }
        return result;
    }

    public int save(long id, String title, String owner, String status) throws SQLException {
        String sql = id == 0
            ? "INSERT INTO items(title,owner,status) VALUES(?,?,?)"
            : "UPDATE items SET title=?,owner=?,status=? WHERE id=?";
        try (Connection c = open(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, title);
            p.setString(2, owner);
            p.setString(3, status);
            if (id != 0) p.setLong(4, id);
            return p.executeUpdate();
        }
    }

    public int delete(long id) throws SQLException {
        try (Connection c = open();
             PreparedStatement p = c.prepareStatement("DELETE FROM items WHERE id=?")) {
            p.setLong(1, id);
            return p.executeUpdate();
        }
    }
}
