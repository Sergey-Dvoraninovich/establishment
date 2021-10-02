package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dvoraninovich.establishment.controller.command.PagePath.INDEX_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;

public class SignOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new Router(INDEX_PAGE, REDIRECT);
    }
}
