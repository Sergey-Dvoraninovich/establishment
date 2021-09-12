package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.exception.DaoException;

import java.util.List;

public interface IngredientDao extends BaseDao<Long, Ingredient>{
    List<Ingredient> findDishIngredients(Dish dish) throws DaoException;
    Long testInsert(Ingredient ingredient) throws DaoException;
}
