package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.DishValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.DISH_ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.DISH;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.EXCEPTION;

public class DisableDishCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DisableDishCommand.class);
    private DishService service = DishServiceImpl.getInstance();
    private DishValidator validator = DishValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();

        String dishIdLine = request.getParameter(DISH_ID);
        long dishId;
        if (validator.validateDishId(dishIdLine)) {
            dishId = Long.parseLong(dishIdLine);
        }
        else {
            return new Router(DISH_PAGE + "?id=" + dishIdLine, REDIRECT);
        }

        try {
            Optional<Dish> optionalDish = service.findDishById(dishId);
            if (optionalDish.isPresent()){
                service.invalidateDish(dishId);
                Dish dish = optionalDish.get();
                dish.setIsAvailable(false);
                session.setAttribute(DISH, dish);
            }
            router = new Router(DISH_PAGE + "?id=" + dishIdLine, REDIRECT);
        } catch (ServiceException e) {
            logger.info("Impossible to disable dish with id:" + dishIdLine, e);
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
