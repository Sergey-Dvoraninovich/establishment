package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dvoraninovich.establishment.controller.command.PagePath.VERIFICATION_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.VERIFICATION_ERROR;

public class GoToVerifyCodePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(USER_IS_NOT_AUTHENTICATED, false);
        session.setAttribute(USER_ALREADY_VERIFIED, false);
        session.setAttribute(USER_BLOCKED, false);
        session.setAttribute(INCORRECT_USER_CODE, false);
        session.setAttribute(INVALID_USER_CODE, false);
        session.setAttribute(VERIFICATION_ERROR, false);
        return new Router(VERIFICATION_PAGE, REDIRECT);
    }
}

