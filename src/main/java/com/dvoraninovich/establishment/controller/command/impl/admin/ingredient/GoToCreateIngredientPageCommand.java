package com.dvoraninovich.establishment.controller.command.impl.admin.ingredient;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.*;

public class GoToCreateIngredientPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(ADD_INGREDIENT_ERROR, false);
        session.setAttribute(INVALID_INGREDIENT_NAME, false);
        session.setAttribute(INGREDIENT_VALIDATION_ERROR, false);
        return new Router(PagePath.CREATE_INGREDIENT_PAGE, Router.RouterType.REDIRECT);
    }
}
