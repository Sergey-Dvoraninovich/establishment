package com.dvoraninovich.establishment.controller.command.impl;

import com.dvoraninovich.establishment.controller.command.Command;
import com.dvoraninovich.establishment.controller.command.Router;
import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.model.dao.IngredientDao;
import com.dvoraninovich.establishment.model.dao.impl.IngredientDaoImpl;
import com.dvoraninovich.establishment.model.entity.Ingredient;

import javax.servlet.http.HttpServletRequest;

import static com.dvoraninovich.establishment.controller.command.PagePath.INDEX;
import static com.dvoraninovich.establishment.controller.command.Router.RouterType.REDIRECT;

public class TestCommand implements Command {
    private IngredientDao ingredientDao = IngredientDaoImpl.getInstance();
    @Override
    public Router execute(HttpServletRequest request) {
        Long id;
        Ingredient ingredient = Ingredient.builder().setName("Pear").build();
        try {
            id = ingredientDao.testInsert(ingredient);
            System.out.println(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return new Router(INDEX, REDIRECT);
    }
}
