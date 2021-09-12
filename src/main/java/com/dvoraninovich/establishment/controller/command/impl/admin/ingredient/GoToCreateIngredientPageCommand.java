package com.dvoraninovich.establishment.controller.command.impl.admin.ingredient;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.PagePath;
import com.dvoraninovich.establishment.controller.command.Router;

import javax.servlet.http.HttpServletRequest;

public class GoToCreateIngredientPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.CREATE_INGREDIENT_PAGE, Router.RouterType.REDIRECT);
    }
}
