package com.dvoraninovich.establishment.controller.command.impl.admin.dish;

import com.dvoraninovich.establishment.controller.command.Command;
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
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.controller.command.PagePath.EDIT_DISH_PAGE;
import static com.dvoraninovich.establishment.controller.command.PagePath.ERROR_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.ID;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class GoToEditDishCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToEditDishCommand.class);
    private DishService service = DishServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Optional<Dish> dish;
        List<Ingredient> ingredients;
        List<Ingredient> unusedIngredients;

        String idParameter = request.getParameter(ID);

        try {
            long id = Long.parseLong(idParameter);
            dish = service.findDishById(id);
            ingredients = service.findDishIngredients(id);
            unusedIngredients = service.findUnusedDishIngredients(id);
        } catch (ServiceException e) {
            logger.info("Impossible to go to edit page for dish with id:" + idParameter, e);
            session.setAttribute(EXCEPTION, e);
            return new Router(ERROR_PAGE, FORWARD);
        }

        session.setAttribute(DISH, dish.get());
        session.setAttribute(INGREDIENTS, ingredients);
        session.setAttribute(UNUSED_INGREDIENTS, unusedIngredients);
        session.setAttribute(INVALID_DISH_NAME, false);
        session.setAttribute(INVALID_DISH_PRICE, false);
        session.setAttribute(INVALID_DISH_AMOUNT_GRAMS, false);
        session.setAttribute(INVALID_DISH_CALORIES_AMOUNT, false);
        session.setAttribute(DISH_VALIDATION_ERROR, false);
        session.setAttribute(EDIT_DISH_ERROR, false);

        return new Router(EDIT_DISH_PAGE + "?id=" + idParameter, REDIRECT);
    }
}
