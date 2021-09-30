package com.dvoraninovich.establishment.controller.filter;

import com.dvoraninovich.establishment.controller.command.CommandType;
import com.dvoraninovich.establishment.controller.command.RolesCommandTypes;
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
import static com.dvoraninovich.establishment.controller.command.RequestParameter.COMMAND;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.USER;
import static com.dvoraninovich.establishment.model.entity.Role.*;

public class CommandAccessFilter implements Filter {
    private static Logger logger = LogManager.getLogger(CommandAccessFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String commandName = request.getParameter(COMMAND).toUpperCase();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        Role userRole;

        if (user == null) {
            userRole = GUEST;
        } else {
            userRole = user.getRole();
        }

        Boolean isValidCommand;
        try {
            CommandType.valueOf(commandName);
            isValidCommand = true;
        } catch (NullPointerException e) {
            isValidCommand = false;
        }
        catch (IllegalArgumentException e) {
            logger.info("Illegal command " + commandName + " from user " + user);
            response.sendRedirect(INDEX_PAGE);
            return;
        }

        if (isValidCommand) {
            CommandType command = CommandType.valueOf(commandName);

            List<CommandType> guestCommands = RolesCommandTypes.GUEST.getRoleCommandTypesList();
            if (userRole.equals(GUEST) && !guestCommands.contains(command)) {
                logger.info("Illegal access to command " + command + " by user " + user);
                response.sendRedirect(INDEX_PAGE);
                return;
            }

            List<CommandType> customerCommands = RolesCommandTypes.CUSTOMER.getRoleCommandTypesList();
            if (userRole.equals(CUSTOMER) && !customerCommands.contains(command)) {
                logger.info("Illegal access to command " + command + " by user " + user);
                response.sendRedirect(INDEX_PAGE);
                return;
            }

            List<CommandType> adminCommands = RolesCommandTypes.ADMIN.getRoleCommandTypesList();
            if (userRole.equals(ADMIN) && !adminCommands.contains(command)) {
                logger.info("Illegal access to command " + command + " by user " + user);
                response.sendRedirect(INDEX_PAGE);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
