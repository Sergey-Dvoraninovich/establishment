package com.dvoraninovich.establishment.controller.command.impl.admin.user;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;

import static com.dvoraninovich.establishment.controller.command.PagePath.USERS_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;

public class GoToUsersPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(USERS_PAGE, REDIRECT);
    }
}
