package com.example.members.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.example.members.model.Member;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Member> mapper = (rs, rowNum) -> new Member(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email"));

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Member> findAll() {
        String sql = "SELECT id, name, email FROM members ORDER BY id";
        return jdbcTemplate.query(sql, mapper);
    }

    public List<Member> searchByName(String keyword) {
        // LOCATE는 %, _도 와일드카드가 아닌 일반 문자로 검색한다.
        String sql = "SELECT id, name, email FROM members "
                + "WHERE LOCATE(?, name) > 0 ORDER BY id";
        return jdbcTemplate.query(sql, mapper, keyword);
    }

    public Optional<Member> findById(Long id) {
        String sql = "SELECT id, name, email FROM members WHERE id = ?";
        return jdbcTemplate.query(sql, mapper, id).stream().findFirst();
    }
}
