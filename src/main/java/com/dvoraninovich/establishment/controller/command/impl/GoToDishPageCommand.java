package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.DISH_PAGE;
import static com.dvoraninovich.establishment.controller.command.PagePath.ERROR_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class GoToDishPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToDishPageCommand.class);
    private DishService service = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String idParameter = request.getParameter(ID);
        Optional<Dish> dish;
        List<Ingredient> ingredients;
        List<Ingredient> unusedIngredients;
        try {
            long id = Long.parseLong(idParameter);
            dish = service.findDishById(id);
            ingredients = service.findDishIngredients(id);
            unusedIngredients = service.findUnusedDishIngredients(id);
        } catch (ServiceException e) {
            logger.error("Impossible to go to dish page", e);
            session.setAttribute(EXCEPTION, e);
            return new Router(ERROR_PAGE, FORWARD);
        }
        session.setAttribute(DISH, dish.get());
        session.setAttribute(INGREDIENTS, ingredients);
        session.setAttribute(UNUSED_INGREDIENTS, unusedIngredients);
        return new Router(DISH_PAGE + "?id=" + idParameter, REDIRECT);
    }
}
