package com.dvoraninovich.establishment.controller.command.impl.customer;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;

import static com.dvoraninovich.establishment.controller.command.PagePath.DISHES_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;

public class BuyBasketCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(DISHES_PAGE, REDIRECT);
    }
}
