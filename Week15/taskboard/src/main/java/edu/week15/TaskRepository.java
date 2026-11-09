package edu.week15;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbc;

    public TaskRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Task> find(String keyword, int page) {
        // LOCATE로 %, _도 일반 문자로 검색한다. 정렬은 고정한다.
        return jdbc.query("""
                SELECT id, title, status FROM tasks
                WHERE LOCATE(?, title) > 0
                ORDER BY id DESC LIMIT 5 OFFSET ?
                """, (rs, row) -> new Task(rs.getLong("id"),
                rs.getString("title"), rs.getString("status")),
                keyword, page * 5);
    }

    public long count(String keyword) {
        return jdbc.queryForObject(
                "SELECT COUNT(*) FROM tasks WHERE LOCATE(?, title) > 0",
                Long.class, keyword);
    }

    public Task get(long id) {
        return jdbc.query("SELECT id, title, status FROM tasks WHERE id = ?",
                (rs, row) -> new Task(rs.getLong("id"),
                        rs.getString("title"), rs.getString("status")), id)
                .stream().findFirst().orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void save(Long id, TaskForm form) {
        if (id == null) {
            jdbc.update("INSERT INTO tasks(title, status) VALUES (?, ?)",
                    form.getTitle().strip(), form.getStatus());
        } else {
            // 같은 값으로 저장하더라도 존재하는 행은 정상 처리한다.
            get(id);
            jdbc.update("UPDATE tasks SET title = ?, status = ? WHERE id = ?",
                    form.getTitle().strip(), form.getStatus(), id);
        }
    }

    public void delete(long id) {
        if (jdbc.update("DELETE FROM tasks WHERE id = ?", id) != 1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
