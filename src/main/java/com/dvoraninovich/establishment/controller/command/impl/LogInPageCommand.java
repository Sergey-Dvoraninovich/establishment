package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.SessionAttribute;
import com.dvoraninovich.establishment.controller.command.validator.IngredientValidator;
import com.dvoraninovich.establishment.controller.command.validator.UserValidator;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.Role;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.service.OrderService;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.OrderServiceImpl;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class LogInPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogInPageCommand.class);
    private UserService service = UserServiceImpl.getInstance();
    private OrderService orderService = OrderServiceImpl.getInstance();
    private UserValidator validator = UserValidator.getInstance();


    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(SessionAttribute.USER);

        if (sessionUser != null) {
            session.setAttribute(USER_ALREADY_AUTHENTICATED, true);
            router = new Router(LOGIN_PAGE, REDIRECT);
            return router;
        }

        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        if (!validator.validateLogin(login)) {
            session.setAttribute(INVALID_LOGIN, true);
            router = new Router(LOGIN_PAGE, REDIRECT);
            return router;
        }

        if (!validator.validatePassword(password)) {
            session.setAttribute(USER_ALREADY_AUTHENTICATED, true);
            router = new Router(LOGIN_PAGE, REDIRECT);
            return router;
        }

        try {
            Optional<User> optionalUser = service.authenticate(login, password);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                session.setAttribute(USER, user);
                session.setAttribute(IS_AUTHENTICATED, true);
                Role role = user.getRole();

                switch (role) {
                    case ADMIN: {
                        router = new Router(ADMIN_PAGE, REDIRECT);
                        break;
                    }
                    case CUSTOMER: {
                        Order basket = orderService.getCustomerBasket(user.getId()).get();
                        Long dishesAmount = orderService.countDishesAmount(basket.getId());
                        session.setAttribute(DISHES_IN_BASKET, dishesAmount);
                        router = new Router(INDEX, REDIRECT);
                        break;
                    }
                    default: {
                        logger.info("Authentication user with unsupported role " + role);
                        session.setAttribute(AUTH_ERROR, true);
                        router = new Router(LOGIN_PAGE, REDIRECT);
                    }
                }

            } else {
                session.setAttribute(AUTH_ERROR, true);
                router = new Router(LOGIN_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.info("Exception occurred", e);
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
