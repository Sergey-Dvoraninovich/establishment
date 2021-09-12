package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class GoToLogInPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(USER_ALREADY_AUTHENTICATED, false);
        session.setAttribute(INVALID_LOGIN, false);
        session.setAttribute(INVALID_PASSWORD, false);
        session.setAttribute(AUTH_ERROR, false);
        return new Router(PagePath.LOGIN_PAGE, Router.RouterType.REDIRECT);
    }
}