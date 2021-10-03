package com.dvoraninovich.establishment.controller.command.impl.admin.ingredient;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.RequestParameter;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.impl.admin.order.SetOrdersFilterParametersCommand;
import com.dvoraninovich.establishment.controller.command.validator.IngredientValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.service.IngredientService;
import com.dvoraninovich.establishment.model.service.impl.IngredientServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.INGREDIENTS_PAGE;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.*;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.INGREDIENTS;

public class SetIngredientsFilterParametersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(SetOrdersFilterParametersCommand.class);
    private IngredientValidator validator = IngredientValidator.getInstance();
    private IngredientService service = IngredientServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();

        session.setAttribute(INVALID_INGREDIENT_NAME, false);
        session.setAttribute(INVALID_FILTER_PARAMETERS, false);
        session.setAttribute(FILTER_ERROR, false);

        String ingredientName = request.getParameter(REQUEST_FILTER_NAME);
        if (!ingredientName.equals("")) {
            List<String> ingredientsErrors = validator.validateIngredient(ingredientName);
            if (!ingredientsErrors.isEmpty()) {
                if (ingredientsErrors.contains(INVALID_INGREDIENT_NAME)) {
                    session.setAttribute(INVALID_INGREDIENT_NAME, true);
                }
                if (ingredientsErrors.contains(INGREDIENT_VALIDATION_ERROR)) {
                    session.setAttribute(FILTER_ERROR, true);
                }
                return new Router(INGREDIENTS_PAGE, REDIRECT);
            }
        }

        try {
            List<Ingredient> ingredients = service.findAllByName(ingredientName);

            session.setAttribute(INGREDIENTS, ingredients);
            session.setAttribute(INGREDIENTS_FILTER_NAME, ingredientName);
        } catch (ServiceException e) {
            session.setAttribute(FILTER_ERROR, true);
            logger.info("Impossible to find ingredients by name: " + ingredientName, e);
        }
        return new Router(INGREDIENTS_PAGE, REDIRECT);
    }
}
