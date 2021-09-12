package com.dvoraninovich.establishment.controller.command.impl.admin.ingredient;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.controller.command.SessionAttribute;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.service.IngredientService;
import com.dvoraninovich.establishment.model.service.impl.IngredientServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static com.dvoraninovich.establishment.controller.command.PagePath.INGREDIENTS_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.INGREDIENTS;

public class GoToIngredientsPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToIngredientsPageCommand.class);
    private IngredientService service = IngredientServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        //TODO work with it
        HttpSession session = request.getSession();
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            ingredients = service.findAll();
        } catch (ServiceException e) {
            logger.info("Can't handle data extraction", e);
        }
        session.setAttribute(INGREDIENTS, ingredients);
        return new Router(INGREDIENTS_PAGE, REDIRECT);
    }
}
