package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.DISH_ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.EXCEPTION;

public class DisableDishCommand implements Command {
    private DishService service = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();

        String dishId = request.getParameter(DISH_ID);
        try {
            service.invalidateDish(Long.valueOf(dishId));
            router = new Router(DISHES_PAGE, REDIRECT);
        } catch (ServiceException e) {
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
