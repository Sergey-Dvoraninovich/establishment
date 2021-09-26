package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Role;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.EDIT_FORM;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.model.entity.Role.ADMIN;

public class GoToUserPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToUserPageCommand.class);
    UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(INDEX, REDIRECT);
        HttpSession session = request.getSession();
        String idParameter = request.getParameter(ID);
        String editFormLine = request.getParameter(EDIT_FORM);
        Long userId = Long.valueOf(idParameter);
        Boolean isEditForm = Boolean.valueOf(editFormLine);
        User user = (User) session.getAttribute(USER);

        if (!user.getRole().equals(ADMIN) && user.getId() != userId) {
            return new Router(INDEX, REDIRECT);
        }

        try {
            Optional<User> optionalProfileUser = userService.findById(userId);
            if (optionalProfileUser.isPresent()) {
                User userProfile = optionalProfileUser.get();
                session.setAttribute(IS_EDITING_PAGE, isEditForm);
                session.setAttribute(USER_PROFILE, userProfile);
                router = new Router(USER_PAGE + "?id=" + idParameter, REDIRECT);
            }
        } catch (ServiceException e) {
            logger.info("Impossible to go to user page", e);
        }
        return router;
    }
}
