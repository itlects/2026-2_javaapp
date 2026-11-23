package edu.week10;

import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.http.*;

public final class Web {
    private Web() { }

    public static String csrf(HttpSession session) {
        String token = (String) session.getAttribute("csrf");
        if (token == null) {
            token = UUID.randomUUID().toString();
            session.setAttribute("csrf", token);
        }
        return token;
    }

    public static boolean validCsrf(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null
                && session.getAttribute("csrf") != null
                && session.getAttribute("csrf").equals(req.getParameter("csrf"));
    }

    public static void redirect(HttpServletRequest req,
            HttpServletResponse resp, String path) throws IOException {
        resp.sendRedirect(req.getContextPath() + path);
    }
}
