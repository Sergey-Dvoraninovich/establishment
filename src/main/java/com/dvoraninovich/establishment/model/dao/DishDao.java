package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.model.entity.Ingredient;

import java.util.List;

public interface DishDao extends BaseDao<Long, Dish>{
    // TODO description
    boolean disable(Long id) throws DaoException;

    // TODO description
    boolean makeAvailable(Long id) throws DaoException;

    // TODO description
    List<Dish> findAllAvailable() throws DaoException;

    // TODO description
    List<Ingredient> findDishIngredients(Long id) throws DaoException;

    boolean addDishIngredient(Long dishId, Long ingredientId) throws DaoException;

    boolean removeDishIngredient(Long dishId, Long ingredientId) throws DaoException;
}
