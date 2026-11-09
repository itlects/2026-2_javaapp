package edu.javaapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {
    // !를 ESCAPE 문자로 지정하여 %, _도 일반 검색 문자로 취급한다.
    private String pattern(String keyword) {
        return "%" + keyword.replace("!", "!!")
            .replace("%", "!%").replace("_", "!_") + "%";
    }

    private Post map(ResultSet rs) throws SQLException {
        return new Post(
            rs.getInt("id"), rs.getString("title"),
            rs.getString("author"), rs.getString("content"),
            rs.getString("created_at")
        );
    }

    public int count(String keyword) throws SQLException {
        String sql = "SELECT COUNT(*) FROM posts WHERE title LIKE ? ESCAPE '!'";
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pattern(keyword));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public List<Post> findAll(String keyword, int offset) throws SQLException {
        String sql = "SELECT id,title,author,content,created_at FROM posts "
            + "WHERE title LIKE ? ESCAPE '!' "
            + "ORDER BY id DESC LIMIT ? OFFSET ?";
        List<Post> posts = new ArrayList<>();
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pattern(keyword));
            ps.setInt(2, Page.SIZE);
            ps.setInt(3, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    posts.add(map(rs));
                }
            }
        }
        return posts;
    }

    public Post find(int id) throws SQLException {
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM posts WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public void insert(String title, String author, String content) throws SQLException {
        String sql = "INSERT INTO posts(title,author,content) VALUES(?,?,?)";
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, content);
            ps.executeUpdate();
        }
    }
}
