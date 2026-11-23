package edu.week10;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/flow")
public class FlowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("name", "요청에 저장한 값");
        req.setAttribute("method", "FORWARD 실험");
        if ("redirect".equals(req.getParameter("mode"))) {
            Web.redirect(req, resp, "/echo");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/echo.jsp").forward(req, resp);
    }
}
