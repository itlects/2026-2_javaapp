package edu.javaapp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/items")
public class ItemServlet extends HttpServlet {
    private final ItemDao dao = new ItemDao();
    private static final List<String> STATUSES = List.of("접수","처리중","완료");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        String q = Objects.toString(req.getParameter("q"), "").strip();
        if (q.length() > 100) {
            resp.sendError(400, "검색어는 100자 이하입니다.");
            return;
        }
        HttpSession session = req.getSession();
        if (session.getAttribute("csrf") == null) {
            session.setAttribute("csrf", UUID.randomUUID().toString());
        }
        try {
            req.setAttribute("items", dao.list(q));
            req.setAttribute("statuses", STATUSES);
            req.getRequestDispatcher("/WEB-INF/views/items.jsp").forward(req, resp);
        } catch (SQLException e) {
            getServletContext().log("Database read failed", e);
            resp.sendError(500, "DB 연결과 서버 로그를 확인하세요.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        String token = req.getParameter("csrf");
        if (session == null || token == null || !token.equals(session.getAttribute("csrf"))) {
            resp.sendError(403, "화면을 새로 열어 다시 시도하세요.");
            return;
        }
        try {
            String action = req.getParameter("action");
            long id = Long.parseLong(Objects.toString(req.getParameter("id"), "0"));
            if (id < 0) throw new IllegalArgumentException();
            int changed;
            if ("delete".equals(action) && id > 0) {
                changed = dao.delete(id);
            } else if ("save".equals(action)) {
                String title = Objects.toString(req.getParameter("title"), "").strip();
                String owner = Objects.toString(req.getParameter("owner"), "").strip();
                String status = Objects.toString(req.getParameter("status"), "");
                if (title.isEmpty() || title.length() > 100 || owner.isEmpty()
                        || owner.length() > 60 || !STATUSES.contains(status)) {
                    throw new IllegalArgumentException();
                }
                changed = dao.save(id, title, owner, status);
            } else {
                throw new IllegalArgumentException();
            }
            if (changed == 0) {
                resp.sendError(404, "대상이 없습니다. 목록을 새로 확인하세요.");
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/items");
        } catch (IllegalArgumentException e) {
            resp.sendError(400, "입력값을 확인하세요. 제목 1~100자, 작성자 1~60자입니다.");
        } catch (SQLException e) {
            getServletContext().log("Database write failed", e);
            resp.sendError(500, "저장하지 못했습니다. 서버 로그를 확인하세요.");
        }
    }
}
