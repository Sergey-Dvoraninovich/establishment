package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.model.dao.IngredientDao;
import com.dvoraninovich.establishment.model.dao.impl.IngredientDaoImpl;
import com.dvoraninovich.establishment.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.dvoraninovich.establishment.controller.command.PagePath.INDEX_PAGE;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.IS_AUTHENTICATED;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.USER;
import static com.dvoraninovich.establishment.model.entity.Role.GUEST;

public class SignOutCommand implements Command {
    private IngredientDao ingredientDao = IngredientDaoImpl.getInstance();
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new Router(INDEX_PAGE, REDIRECT);
    }
}
