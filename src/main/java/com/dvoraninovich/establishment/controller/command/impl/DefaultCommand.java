package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(INDEX, REDIRECT);
    }
}
