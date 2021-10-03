package com.dvoraninovich.establishment.model.dao;


import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.model.entity.Ingredient;

import java.util.List;


/**
 * {@code IngredientDao} interface extends {@link BaseDao}
 * It provides functions to manipulate data of stored {@link Ingredient}
 */
public interface IngredientDao extends BaseDao<Long, Ingredient>{

    /**
     * Find all by name.
     *
     * @param name the name of ingredient
     * @return the list of ingredients
     * @throws DaoException the dao exception
     */
    List<Ingredient> findAllByName(String name) throws DaoException;
}
