package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.*;
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

import static com.dvoraninovich.establishment.controller.command.PagePath.EDIT_DISH_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.DISH;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.INGREDIENTS;

public class GoToEditDishCommand implements Command {
    DishService service = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        String idParameter = request.getParameter(ID);
        //TODO work with it
        Optional<Dish> dish = Optional.empty();
        List<Ingredient> ingredients = new ArrayList<>();
        List<Ingredient> unusedIngredients = new ArrayList<>();
        try {
            dish = service.findDishById(Long.valueOf(idParameter));
            Long id = Long.valueOf(idParameter);
            ingredients = service.findDishIngredients(id);
            unusedIngredients = service.findUnusedDishIngredients(id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        session.setAttribute(DISH, dish.get());
        session.setAttribute(INGREDIENTS, ingredients);
        session.setAttribute("unused_ingredients", unusedIngredients);
        return new Router(EDIT_DISH_PAGE + "?id=" + idParameter, REDIRECT);
    }
}
