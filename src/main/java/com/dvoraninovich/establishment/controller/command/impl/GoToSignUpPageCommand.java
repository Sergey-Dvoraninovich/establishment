package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;

import static com.dvoraninovich.establishment.controller.command.PagePath.SIGN_UP_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;

public class GoToSignUpPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(SIGN_UP_PAGE, REDIRECT);
    }
}
