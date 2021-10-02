package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.UserValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.USER_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.Role.ADMIN;

public class ChangeUserPasswordCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ChangeUserPasswordCommand.class);
    private UserService userService = UserServiceImpl.getInstance();
    private UserValidator userValidator = UserValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();

        session.setAttribute(DIFFERENT_PASSWORDS, false);
        session.setAttribute(CHANGE_PASSWORD_ERROR, false);
        session.setAttribute(INVALID_CURRENT_PASSWORD, false);
        session.setAttribute(INVALID_PASSWORD, false);
        session.setAttribute(PASSWORD_SUCCESSFULLY_CHANGED, false);

        session.setAttribute(IS_CHANGING_PASSWORD, true);

        String idParameter = request.getParameter(ID);
        Long userId = Long.valueOf(idParameter);
        String currentPassword = request.getParameter(CURRENT_PASSWORD);
        currentPassword = currentPassword == null ? "" : currentPassword;
        String password = request.getParameter(PASSWORD);
        String repeatPassword = request.getParameter(REPEAT_PASSWORD);

        User user = (User) session.getAttribute(USER);

        if (!user.getRole().equals(ADMIN)) {
            if (!userValidator.validatePassword(currentPassword)){
                session.setAttribute(INVALID_CURRENT_PASSWORD, true);
                return new Router(USER_PAGE + "?id=" + idParameter + "&change_password_form=true", REDIRECT);
            }
            try {
                Optional<User> testUser = userService.authenticate(user.getLogin(), currentPassword);
                if (!testUser.isPresent()) {
                    session.setAttribute(WRONG_PASSWORD, true);
                    return new Router(USER_PAGE + "?id=" + idParameter + "&change_password_form=true", REDIRECT);
                }
            } catch (ServiceException e) {
                session.setAttribute(CHANGE_PASSWORD_ERROR, true);
                return new Router(USER_PAGE + "?id=" + idParameter + "&change_password_form=true", REDIRECT);
            }
        }

        if (!userValidator.validatePassword(password)) {
            session.setAttribute(INVALID_PASSWORD, true);
            return new Router(USER_PAGE + "?id=" + idParameter + "&change_password_form=true", REDIRECT);
        }
        if (!userValidator.validatePassword(repeatPassword)
            || !password.equals(repeatPassword)) {
            session.setAttribute(DIFFERENT_PASSWORDS, true);
            return new Router(USER_PAGE + "?id=" + idParameter + "&change_password_form=true", REDIRECT);
        }

        try {
            Boolean isSuccessful = userService.setPasswordById(userId, password);
            if (isSuccessful) {
                session.setAttribute(IS_CHANGING_PASSWORD, false);
                session.setAttribute(PASSWORD_SUCCESSFULLY_CHANGED, true);
                return new Router(USER_PAGE + "?id=" + idParameter, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.info("Impossible to go to user page", e);
        }
        session.setAttribute(CHANGE_PASSWORD_ERROR, true);
        router = new Router(USER_PAGE + "?id=" + idParameter + "&change_password_form=true", REDIRECT);
        return router;
    }
}
