package com.dvoraninovich.establishment.controller.command.impl.admin.user;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.impl.admin.order.SetOrdersFilterParametersCommand;
import com.dvoraninovich.establishment.controller.command.validator.UserValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.dvoraninovich.establishment.controller.command.PagePath.ADMIN_PAGE;
import static com.dvoraninovich.establishment.controller.command.PagePath.USERS_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class SetUsersFilterParametersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SetOrdersFilterParametersCommand.class);
    private static final Long ORDERS_PAGE_ITEMS_AMOUNT = 10L;
    private UserValidator validator = UserValidator.getInstance();
    private UserService service = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();

        session.setAttribute(INVALID_LOGIN, false);
        session.setAttribute(INVALID_MAIL, false);
        session.setAttribute(INVALID_PHONE_NUM, false);
        session.setAttribute(INVALID_CARD_NUM, false);
        session.setAttribute(INVALID_USER_STATUS, false);
        session.setAttribute(INVALID_USER_ROLE, false);
        session.setAttribute(INVALID_FILTER_PARAMETERS, false);

        String[] userStatusesLines = request.getParameterValues(REQUEST_FILTER_USER_STATUSES);
        userStatusesLines = userStatusesLines == null ? new String[0] : userStatusesLines;
        String[] userRolesLines = request.getParameterValues(REQUEST_FILTER_USER_ROLES);
        userRolesLines = userRolesLines == null ? new String[0] : userRolesLines;
        String loginLine = request.getParameter(REQUEST_FILTER_LOGIN);
        String mailLine = request.getParameter(REQUEST_FILTER_MAIL);
        String phoneNumberLine = request.getParameter(REQUEST_FILTER_PHONE_NUMBER);
        String cardNumberLine = request.getParameter(REQUEST_FILTER_CARD_NUMBER);

        HashMap<String, Boolean> validationResult;
        validationResult = validator.validateFilterParameters(loginLine, mailLine,
                phoneNumberLine, cardNumberLine, userStatusesLines, userRolesLines);

        Set<String> validationMessages = validationResult.keySet();
        HashMap<String, Boolean> finalValidationResult = validationResult;
        validationMessages.forEach(message -> session.setAttribute(message, finalValidationResult.get(message)));

        Collection<Boolean> validationErrors = validationResult.values();
        if (validationErrors.contains(true)) {
            router = new Router(USERS_PAGE, REDIRECT);
            return router;
        }

        try {
            List<User> users;
            Long minPos = 1L;
            Long maxPos = ORDERS_PAGE_ITEMS_AMOUNT;

            Long totalAmount = service.countUsers(loginLine, mailLine,
                    phoneNumberLine, cardNumberLine, userStatusesLines, userRolesLines);
            maxPos = maxPos > totalAmount ? totalAmount : maxPos;
            session.setAttribute(TOTAL_AMOUNT, totalAmount);
            session.setAttribute(PAGE_ITEMS_AMOUNT, ORDERS_PAGE_ITEMS_AMOUNT);

            users = service.findFilteredUsers(loginLine, mailLine, phoneNumberLine, cardNumberLine,
                    userStatusesLines, userRolesLines, minPos, maxPos);

            session.setAttribute(USERS, users);
            session.setAttribute(MIN_POS, minPos);
            session.setAttribute(MAX_POS, maxPos);
            session.setAttribute(USERS_FILTER_USER_STATUSES, Arrays.asList(userStatusesLines));
            session.setAttribute(USERS_FILTER_USER_ROLES, Arrays.asList(userRolesLines));
            session.setAttribute(USERS_FILTER_LOGIN, loginLine.equals("") ? null : loginLine);
            session.setAttribute(USERS_FILTER_MAIL, mailLine.equals("") ? null : mailLine);
            session.setAttribute(USERS_FILTER_PHONE_NUMBER, phoneNumberLine.equals("") ? null : phoneNumberLine);
            session.setAttribute(USERS_FILTER_CARD_NUMBER, cardNumberLine.equals("") ? null : cardNumberLine);

            router = new Router(USERS_PAGE, REDIRECT);
        } catch (ServiceException e) {
            logger.info("Impossible to find user orders", e);
            router = new Router(ADMIN_PAGE, REDIRECT);
        }
        return router;
    }
}
