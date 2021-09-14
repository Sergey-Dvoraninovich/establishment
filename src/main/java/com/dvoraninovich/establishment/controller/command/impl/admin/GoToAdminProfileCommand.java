package com.dvoraninovich.establishment.controller.command.impl.admin;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;

import static com.dvoraninovich.establishment.controller.command.PagePath.ADMIN_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;

public class GoToAdminProfileCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        String idParameter = request.getParameter(ID);
        return new Router(ADMIN_PAGE + "?id=" + idParameter, REDIRECT);
    }
}
