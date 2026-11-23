package edu.week10;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Web.csrf(req.getSession());
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!Web.validCsrf(req)) {
            resp.sendError(403, "폼을 새로 열고 다시 시도하세요.");
            return;
        }
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        String displayName;
        try {
            displayName = AuthService.authenticate(userId, password);
        } catch (SQLException ex) {
            getServletContext().log("Login DB error", ex);
            resp.sendError(503, "DB 설정과 서버 로그를 확인하세요.");
            return;
        }
        if (displayName == null) {
            req.setAttribute("error", "아이디 또는 비밀번호를 확인하세요.");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }
        HttpSession session = req.getSession();
        req.changeSessionId();
        session.setAttribute("loginUser", displayName);
        session.setMaxInactiveInterval(20 * 60);
        session.removeAttribute("csrf");
        Web.csrf(session);
        Web.redirect(req, resp, "/home");
    }
}
