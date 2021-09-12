package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;

public class GoToDishesPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToDishesPageCommand.class);
    private DishService service = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //TODO work with it
        List<Dish> dishes = new ArrayList<>();
        try {
            dishes = service.findAll();
        } catch (ServiceException e) {
            logger.info("Can't handle dishes select");
        }
        session.setAttribute("dishes", dishes);
        return new Router(DISHES_PAGE, REDIRECT);
    }
}
