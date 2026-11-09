package edu.week11;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDao {
    private Member map(ResultSet rs) throws SQLException {
        return new Member(rs.getLong("id"), rs.getString("name"),
            rs.getString("email"), rs.getString("phone"));
    }

    public List<Member> findAll(String keyword) throws SQLException {
        String sql = "SELECT id, name, email, phone FROM members "
            + "WHERE name LIKE ? OR email LIKE ? ORDER BY id DESC";
        List<Member> members = new ArrayList<>();
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    members.add(map(rs));
                }
            }
        }
        return members;
    }

    public Member findById(long id) throws SQLException {
        String sql = "SELECT id, name, email, phone FROM members WHERE id=?";
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public int insert(Member m) throws SQLException {
        String sql = "INSERT INTO members(name, email, phone) VALUES (?, ?, ?)";
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getName());
            ps.setString(2, m.getEmail());
            ps.setString(3, m.getPhone());
            return ps.executeUpdate();
        }
    }

    public int update(Member m) throws SQLException {
        String sql = "UPDATE members SET name=?, email=?, phone=? WHERE id=?";
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getName());
            ps.setString(2, m.getEmail());
            ps.setString(3, m.getPhone());
            ps.setLong(4, m.getId());
            return ps.executeUpdate();
        }
    }

    public int delete(long id) throws SQLException {
        try (Connection con = DB.open();
             PreparedStatement ps = con.prepareStatement("DELETE FROM members WHERE id=?")) {
            ps.setLong(1, id);
            return ps.executeUpdate();
        }
    }
}
