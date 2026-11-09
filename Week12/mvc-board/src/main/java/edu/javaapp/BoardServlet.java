package edu.javaapp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
    private final PostDAO dao = new PostDAO();

    private String text(HttpServletRequest req, String name) {
        String value = req.getParameter(name);
        return value == null ? "" : value.trim();
    }

    private void view(HttpServletRequest req, HttpServletResponse resp, String name)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + name + ".jsp").forward(req, resp);
    }

    private void form(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("csrf") == null) {
            session.setAttribute("csrf", UUID.randomUUID().toString());
        }
        view(req, resp, "form");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            switch (text(req, "action")) {
                case "new" -> form(req, resp);
                case "detail" -> {
                    int id;
                    try {
                        id = Integer.parseInt(text(req, "id"));
                    } catch (NumberFormatException e) {
                        resp.sendError(400, "글 번호는 정수여야 합니다.");
                        return;
                    }
                    Post post = dao.find(id);
                    if (post == null) {
                        resp.sendError(404, "글이 없습니다.");
                        return;
                    }
                    req.setAttribute("post", post);
                    req.setAttribute("keyword", text(req, "keyword"));
                    req.setAttribute("backPage", text(req, "page"));
                    view(req, resp, "detail");
                }
                case "", "list" -> {
                    String keyword = text(req, "keyword");
                    if (keyword.length() > 100) {
                        resp.sendError(400, "검색어는 100자 이하입니다.");
                        return;
                    }
                    int total = dao.count(keyword);
                    Page page = new Page(req.getParameter("page"), total);
                    req.setAttribute("keyword", keyword);
                    req.setAttribute("total", total);
                    req.setAttribute("paging", page);
                    req.setAttribute("posts", dao.findAll(keyword, page.getOffset()));
                    view(req, resp, "list");
                }
                default -> resp.sendError(400, "지원하지 않는 요청입니다.");
            }
        } catch (SQLException e) {
            getServletContext().log("게시판 DB 조회 오류", e);
            resp.sendError(500, "DB 연결 및 서버 로그를 확인하세요.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!"create".equals(text(req, "action"))) {
            resp.sendError(400);
            return;
        }
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("csrf") == null
                || !session.getAttribute("csrf").equals(req.getParameter("csrf"))) {
            resp.sendError(403, "작성 화면을 다시 열어 주세요.");
            return;
        }
        String title = text(req, "title");
        String author = text(req, "author");
        String content = text(req, "content");
        if (title.isEmpty() || title.length() > 100
                || author.isEmpty() || author.length() > 30
                || content.isEmpty() || content.length() > 5000) {
            req.setAttribute("error", "제목 1~100자, 작성자 1~30자, 본문 1~5000자를 입력하세요.");
            req.setAttribute("title", title);
            req.setAttribute("author", author);
            req.setAttribute("content", content);
            resp.setStatus(400);
            form(req, resp);
            return;
        }
        try {
            dao.insert(title, author, content);
            // Post/Redirect/Get: 새로고침할 때 같은 글을 다시 등록하지 않는다.
            resp.sendRedirect(req.getContextPath() + "/board");
        } catch (SQLException e) {
            getServletContext().log("게시판 등록 오류", e);
            resp.sendError(500, "등록 실패: 서버 로그를 확인하세요.");
        }
    }
}
