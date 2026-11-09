package edu.javaapp.member;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {
    private final JdbcTemplate jdbc;
    private final RowMapper<Member> mapper = (rs, rowNum) -> new Member(
            rs.getLong("id"), rs.getString("name"),
            rs.getString("email"), rs.getString("phone"));

    public MemberDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Member> findAll() {
        return jdbc.query("SELECT id, name, email, phone FROM members ORDER BY id DESC", mapper);
    }

    public Optional<Member> findById(long id) {
        return jdbc.query("SELECT id, name, email, phone FROM members WHERE id = ?", mapper, id)
                .stream().findFirst();
    }

    public int insert(MemberForm form) {
        return jdbc.update("INSERT INTO members(name, email, phone) VALUES (?, ?, ?)",
                form.getName(), form.getEmail(), form.getPhone());
    }

    public int update(long id, MemberForm form) {
        return jdbc.update("UPDATE members SET name = ?, email = ?, phone = ? WHERE id = ?",
                form.getName(), form.getEmail(), form.getPhone(), id);
    }

    public int delete(long id) {
        return jdbc.update("DELETE FROM members WHERE id = ?", id);
    }
}
