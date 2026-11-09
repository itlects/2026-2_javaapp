package edu.week11;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/members")
public class MemberServlet extends HttpServlet {
    private final MemberDao dao = new MemberDao();

    private String value(HttpServletRequest req, String key) {
        String text = req.getParameter(key);
        return text == null ? "" : text.trim();
    }

    private long id(HttpServletRequest req) {
        long id = Long.parseLong(value(req, "id"));
        if (id <= 0) {
            throw new IllegalArgumentException("잘못된 회원 번호");
        }
        return id;
    }

    private void token(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session.getAttribute("csrf") == null) {
            session.setAttribute("csrf", UUID.randomUUID().toString());
        }
    }

    private void form(HttpServletRequest req, HttpServletResponse resp, Member m)
            throws ServletException, IOException {
        req.setAttribute("member", m);
        req.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Cache-Control", "no-store");
        token(req);
        try {
            switch (value(req, "action")) {
                case "new" -> form(req, resp, new Member(0, "", "", ""));
                case "edit" -> {
                    Member m = dao.findById(id(req));
                    if (m == null) {
                        resp.sendError(404);
                    } else {
                        form(req, resp, m);
                    }
                }
                case "", "list" -> {
                    String q = value(req, "q");
                    req.setAttribute("q", q);
                    req.setAttribute("members", dao.findAll(q));
                    req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
                }
                default -> resp.sendError(400);
            }
        } catch (IllegalArgumentException e) {
            resp.sendError(400);
        } catch (SQLException e) {
            throw new ServletException("회원 조회 실패: 서버 로그와 DB 설정을 확인하세요.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || !value(req, "csrf").equals(session.getAttribute("csrf"))) {
            resp.sendError(403);
            return;
        }
        String action = value(req, "action");
        Member m = null;
        try {
            if (action.equals("delete")) {
                if (dao.delete(id(req)) == 0) {
                    resp.sendError(404);
                    return;
                }
            } else if (action.equals("save")) {
                long memberId = value(req, "id").isEmpty() ? 0 : id(req);
                m = new Member(memberId, value(req, "name"),
                    value(req, "email"), value(req, "phone"));
                if (m.getName().isEmpty() || m.getName().length() > 50
                        || m.getEmail().length() > 120
                        || !m.getEmail().matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")
                        || !m.getPhone().matches("[0-9+() -]{0,20}")) {
                    resp.setStatus(400);
                    req.setAttribute("error", "이름(1~50자), 이메일(120자 이하), 전화번호(20자 이하)를 확인하세요.");
                    form(req, resp, m);
                    return;
                }
                int count = memberId == 0 ? dao.insert(m) : dao.update(m);
                if (count == 0) {
                    resp.sendError(404);
                    return;
                }
            } else {
                resp.sendError(400);
                return;
            }
            resp.sendRedirect(req.getContextPath() + "/members");
        } catch (IllegalArgumentException e) {
            resp.sendError(400);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062 && m != null) {
                resp.setStatus(409);
                req.setAttribute("error", "이미 등록된 이메일입니다. 다른 이메일을 입력하세요.");
                form(req, resp, m);
            } else {
                throw new ServletException("회원 변경 실패: 서버 로그를 확인하세요.", e);
            }
        }
    }
}
