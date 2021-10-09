package com.dvoraninovich.establishment.controller.filter;

import com.dvoraninovich.establishment.controller.command.RolePagePath;
import com.dvoraninovich.establishment.model.entity.Role;
import com.dvoraninovich.establishment.model.entity.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.INDEX_PAGE;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.USER;
import static com.dvoraninovich.establishment.model.entity.Role.*;

public class JspAccessFilter implements Filter{
    private static Logger logger = LogManager.getLogger(CommandAccessFilter.class);
    private static final String JSP_EXTENSION = ".jsp";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestLine = request.getRequestURI();

        int extensionPos = requestLine.lastIndexOf(JSP_EXTENSION);
        if (extensionPos != -1) {
            String jspName = requestLine.substring(0, extensionPos + JSP_EXTENSION.length());

            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER);
            Role userRole = user == null ? GUEST : user.getRole();

            List<String> guestPages = RolePagePath.GUEST.getRolePagesList();
            if (userRole.equals(GUEST) && !guestPages.contains(jspName)) {
                logger.info("Illegal access to jsp " + jspName + " by user " + user);
                response.sendRedirect(INDEX_PAGE);
                return;
            }

            List<String> customerPages = RolePagePath.CUSTOMER.getRolePagesList();
            if (userRole.equals(CUSTOMER) && !customerPages.contains(jspName)) {
                logger.info("Illegal access to jsp " + jspName + " by user " + user);
                response.sendRedirect(INDEX_PAGE);
                return;
            }

            List<String> adminPages = RolePagePath.ADMIN.getRolePagesList();
            if (userRole.equals(ADMIN) && !adminPages.contains(jspName)) {
                logger.info("Illegal access to jsp " + jspName + " by user " + user);
                response.sendRedirect(INDEX_PAGE);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
