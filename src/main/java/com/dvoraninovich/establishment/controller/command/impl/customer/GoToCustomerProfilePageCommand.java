package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;

import static com.dvoraninovich.establishment.controller.command.PagePath.CUSTOMER_PROFILE_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;

public class GoToCustomerProfilePageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        String idParameter = request.getParameter(ID);
        return new Router(CUSTOMER_PROFILE_PAGE + "?id=" + idParameter, REDIRECT);
    }
}
