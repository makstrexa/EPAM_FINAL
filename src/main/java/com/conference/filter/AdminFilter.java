package com.conference.filter;

import com.conference.model.User;
import com.conference.model.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;

        User user = (User) req.getSession().getAttribute("user");
        if (user != null && user.getRole() == UserRole.ADMIN) {
            chain.doFilter(request, response);
        } else {
            req.setAttribute("message", "Acces Denied!! You are not admin, please, try again");
            req.getRequestDispatcher("/error.jsp").forward(request, response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
