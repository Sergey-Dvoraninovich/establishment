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
    private static final Long ORDERS_PAGE_ITEMS_AMOUNT = Long.valueOf(10);
    UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        List<User> users = new ArrayList<>();
        String minPosLine = request.getParameter(NEXT_MIN_POS);
        String maxPosLine = request.getParameter(NEXT_MAX_POS);
        String newTotalAmountLine = request.getParameter(NEW_TOTAL_AMOUNT);
        Long totalAmount;

        List<String> userStatusesList = (List<String>) session.getAttribute(USERS_FILTER_USER_STATUSES);
        List<String> userRolesList = (List<String>) session.getAttribute(USERS_FILTER_USER_ROLES);
        String loginLine = (String) session.getAttribute(USERS_FILTER_LOGIN);
        String mailLine = (String) session.getAttribute(USERS_FILTER_MAIL);
        String phoneNumberLine = (String) session.getAttribute(USERS_FILTER_PHONE_NUMBER);
        String cardNumberLine = (String) session.getAttribute(USERS_FILTER_CARD_NUMBER);

        try {
            Long minPos = Long.valueOf(minPosLine);
            Long maxPos = Long.valueOf(maxPosLine);
            String[] userStatusesLines = userStatusesList == null
                    ? new String[0]
                    : (String[]) userStatusesList.toArray();
            String[] userRolesLines = userRolesList == null
                    ? new String[0]
                    : (String[]) userRolesList.toArray();
            loginLine = loginLine == null ? "" : loginLine;
            mailLine = mailLine == null ? "" : mailLine;
            phoneNumberLine = phoneNumberLine == null ? "" : phoneNumberLine;
            cardNumberLine = cardNumberLine == null ? "" : cardNumberLine;

            if (newTotalAmountLine != null) {
                totalAmount = userService.countUsers(loginLine, mailLine,
                        phoneNumberLine, cardNumberLine, userStatusesLines, userRolesLines);
                session.setAttribute(TOTAL_AMOUNT, totalAmount);
                session.setAttribute(PAGE_ITEMS_AMOUNT, ORDERS_PAGE_ITEMS_AMOUNT);
            } else {
                totalAmount = (Long) session.getAttribute(TOTAL_AMOUNT);
            }

            maxPos = maxPos > totalAmount ? totalAmount : maxPos;
            users = userService.findFilteredUsers(loginLine, mailLine, phoneNumberLine, cardNumberLine,
                    userStatusesLines, userRolesLines, minPos, maxPos);

            session.setAttribute(USERS, users);
            session.setAttribute(MIN_POS, minPos);
            session.setAttribute(MAX_POS, maxPos);
            router = new Router(USERS_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.info("Impossible to find user orders", e);
            router = new Router(ADMIN_PAGE, REDIRECT);
        }
        return router;
    }
}
