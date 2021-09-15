package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.SessionAttribute;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.service.DishService;
import com.dvoraninovich.establishment.model.service.impl.DishServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.INGREDIENTS;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class RemoveDishIngredientCommand implements Command {
    private DishService service = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        List<Ingredient> ingredients = new ArrayList<>();
        Optional<Dish> dish = Optional.empty();

        String idParameter = request.getParameter(ID);
        String ingredientIdParameter = request.getParameter(INGREDIENTS);

        try {
            boolean creationResult = false;
            Long dishId = Long.valueOf(idParameter);
            Long ingredientId = Long.valueOf(ingredientIdParameter);
            creationResult = service.removeDishIngredient(dishId, ingredientId);
            if (creationResult) {
                dish = service.findDishById(dishId);
                ingredients = service.findDishIngredients(dishId);
                session.setAttribute(DISH, dish.get());
                session.setAttribute(SessionAttribute.INGREDIENTS, ingredients);
                router = new Router(DISH_PAGE + "?id:" + dishId, REDIRECT);
            }
            else {
                session.setAttribute(REMOVE_DISH_INGREDIENT_ERROR, true);
                router = new Router(EDIT_DISH_PAGE + "?id:" + dishId, REDIRECT);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
