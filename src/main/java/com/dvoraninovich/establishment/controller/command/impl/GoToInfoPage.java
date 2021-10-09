package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.model.service.impl.UserServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.INFO_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.ADMINS_INFO;

public class GoToInfoPage implements Command {
    private static final Logger logger = LogManager.getLogger(GoToInfoPage.class);
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<User> adminsInfo =  new ArrayList<>();

        try {
            adminsInfo = userService.getAdminsInfo();
        } catch (ServiceException e) {
            logger.info("Impossible to find admins info", e);
        }
        session.setAttribute(ADMINS_INFO, adminsInfo);
        return new Router(INFO_PAGE, REDIRECT);
    }
}
