package com.dvoraninovich.establishment.controller.command.impl.admin.ingredient;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.validator.IngredientValidator;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.service.IngredientService;
import com.dvoraninovich.establishment.model.service.impl.IngredientServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.*;
import static com.dvoraninovich.establishment.controller.command.RequestParameter.NAME;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.FORWARD;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class CreateIngredientCommand implements Command {
    private IngredientService service = IngredientServiceImpl.getInstance();
    private IngredientValidator validator = IngredientValidator.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();

        session.setAttribute(ADD_INGREDIENT_ERROR, false);
        session.setAttribute(INVALID_INGREDIENT_NAME, false);
        session.setAttribute(INGREDIENT_VALIDATION_ERROR, false);

        String name = request.getParameter(NAME);

        List<String> validationMessages = new ArrayList<>();
        validationMessages = validator.validateIngredient(name);

        if (!validationMessages.isEmpty()) {
            validationMessages.forEach(validationMessage -> session.setAttribute(validationMessage, true));
            router = new Router(CREATE_INGREDIENT_PAGE, REDIRECT);
            return router;
        }

        Ingredient ingredient = Ingredient.builder()
                .setName(name)
                .build();


        try {
            boolean creationResult = false;
            creationResult = service.addIngredient(ingredient);
            if (creationResult) {
                //TODO work with it
                List<Ingredient> ingredients = service.findAll();
                session.setAttribute("ingredients", ingredients);
                router = new Router(INGREDIENTS_PAGE, REDIRECT);
            }
            else {
                session.setAttribute(ADD_INGREDIENT_ERROR, true);
                router = new Router(CREATE_INGREDIENT_PAGE, REDIRECT);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            session.setAttribute(EXCEPTION, e);
            router = new Router(ERROR_PAGE, FORWARD);
        }
        return router;
    }
}
