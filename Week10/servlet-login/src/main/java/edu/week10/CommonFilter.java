package edu.week10;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

@WebFilter("/*")
public class CommonFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Cache-Control", "no-store");
        if ("/home".equals(req.getServletPath())) {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("loginUser") == null) {
                Web.redirect(req, resp, "/login");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
