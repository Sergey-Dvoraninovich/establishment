package com.dvoraninovich.establishment.controller.filter;

import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.SessionAttribute;
import com.dvoraninovich.establishment.model.entity.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(
        urlPatterns = "/admin/*"
)
public class AdminPagesAccessFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

//        User user = (User) session.getAttribute(SessionAttribute.USER);
//        if (user == null || user.getRole() != Role.ADMIN) {
//            httpResponse.sendRedirect(PagePath.ERROR_PAGE);
//        } else {
//            chain.doFilter(request, response);
//        }
    }
}
