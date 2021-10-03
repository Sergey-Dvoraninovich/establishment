package com.dvoraninovich.establishment.controller.command.impl.admin.user;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.impl.customer.GoToUserPageCommand;
import com.dvoraninovich.establishment.controller.command.validator.UserValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.entity.UserStatus;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.INDEX_PAGE;
import static com.dvoraninovich.establishment.controller.command.PagePath.USER_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class ChangeUserStatusCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToUserPageCommand.class);
    private UserService userService = UserServiceImpl.getInstance();
    private UserValidator userValidator = UserValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(INDEX_PAGE, REDIRECT);
        HttpSession session = request.getSession();

        session.setAttribute(INVALID_USER_STATUS, false);

        String idParameter = request.getParameter(ID);
        String userStatusLine = request.getParameter(USER_STATUS);

        if (userStatusLine.equals("")) {
            return new Router(USER_PAGE + "?id=" + idParameter + "&edit_form=true", REDIRECT);
        }

        if (!userValidator.validateUserStatus(userStatusLine)) {
            session.setAttribute(INVALID_USER_STATUS, true);
            return new Router(USER_PAGE + "?id=" + idParameter + "&edit_form=true", REDIRECT);
        }

        try {
            Long userId = Long.valueOf(idParameter);
            Optional<User> optionalProfileUser = userService.findById(userId);
            if (optionalProfileUser.isPresent()) {
                User user = optionalProfileUser.get();
                user.setStatus(UserStatus.valueOf(userStatusLine));
                Boolean isSuccessful = userService.updateUser(user);
                if (isSuccessful) {
                    session.setAttribute(USER_PROFILE, user);
                    return new Router(USER_PAGE + "?id=" + idParameter, REDIRECT);
                }
            }
        } catch (ServiceException e) {
            logger.info("Impossible to change status for user with id=" + idParameter, e);
            session.setAttribute(INVALID_USER_STATUS, true);
        }
        return new Router(USER_PAGE + "?id=" + idParameter + "&edit_form=true", REDIRECT);
    }
}
