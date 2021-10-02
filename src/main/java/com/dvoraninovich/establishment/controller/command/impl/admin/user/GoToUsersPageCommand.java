package com.dvoraninovich.establishment.controller.command.impl.admin.user;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.impl.admin.order.GoToOrdersPageCommand;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.OrderService;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.MAX_POS;

public class GoToUsersPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToUsersPageCommand.class);
    private static final Long ORDERS_PAGE_ITEMS_AMOUNT = 10L;
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        List<User> users;
        String minPosLine = request.getParameter(NEXT_MIN_POS);
        String maxPosLine = request.getParameter(NEXT_MAX_POS);
        Long totalAmount = (Long) session.getAttribute(TOTAL_AMOUNT);

        List<String> userStatusesList = (List<String>) session.getAttribute(USERS_FILTER_USER_STATUSES);
        List<String> userRolesList = (List<String>) session.getAttribute(USERS_FILTER_USER_ROLES);
        String loginLine = (String) session.getAttribute(USERS_FILTER_LOGIN);
        String mailLine = (String) session.getAttribute(USERS_FILTER_MAIL);
        String phoneNumberLine = (String) session.getAttribute(USERS_FILTER_PHONE_NUMBER);
        String cardNumberLine = (String) session.getAttribute(USERS_FILTER_CARD_NUMBER);

        Long minPos;
        Long maxPos;
        if (minPosLine == null || maxPosLine == null) {
            minPos = 1L;
            maxPos = ORDERS_PAGE_ITEMS_AMOUNT;
        }
        else {
            minPos = Long.valueOf(minPosLine);
            maxPos = Long.valueOf(maxPosLine);
        }

        try {
            if (minPos.equals(1L) || totalAmount != null) {
                totalAmount = userService.countUsers(loginLine, mailLine,
                        phoneNumberLine, cardNumberLine, userStatusesList, userRolesList);
                session.setAttribute(TOTAL_AMOUNT, totalAmount);
                session.setAttribute(PAGE_ITEMS_AMOUNT, ORDERS_PAGE_ITEMS_AMOUNT);
            } else {
                totalAmount = (Long) session.getAttribute(TOTAL_AMOUNT);
            }

            maxPos = maxPos > totalAmount ? totalAmount : maxPos;
            users = userService.findFilteredUsers(loginLine, mailLine, phoneNumberLine, cardNumberLine,
                    userStatusesList, userRolesList, minPos, maxPos);

            session.setAttribute(USERS, users);
            session.setAttribute(MIN_POS, minPos);
            session.setAttribute(MAX_POS, maxPos);
            router = new Router(USERS_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.info("Impossible to find users", e);
            router = new Router(ADMIN_PAGE, REDIRECT);
        }
        return router;
    }
}
