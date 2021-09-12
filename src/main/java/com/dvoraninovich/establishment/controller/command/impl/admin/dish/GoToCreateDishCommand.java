package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;

import static com.dvoraninovich.establishment.controller.command.PagePath.CREATE_DISH_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;

public class GoToCreateDishCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(CREATE_DISH_PAGE, REDIRECT);
    }
}
