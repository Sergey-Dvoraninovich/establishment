package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.model.entity.Ingredient;

import java.util.List;

/**
 * {@code DishDao} interface extends {@link BaseDao} It provides functions to manipulate data of stored {@link Dish}
 */
public interface DishDao extends BaseDao<Long, Dish> {
    /**
     * Disable.
     *
     * @param id the dish id
     * @return the boolean, which describes the success of dish disabling process
     * @throws DaoException the dao exception
     */
    boolean disable(Long id) throws DaoException;

    /**
     * Make available.
     *
     * @param id the dish id
     * @return the boolean, which describes the success of making dish available  process
     * @throws DaoException the dao exception
     */
    boolean makeAvailable(Long id) throws DaoException;

    /**
     * Find all available.
     *
     * @return the list of all available dishes
     * @throws DaoException the dao exception
     */
    List<Dish> findAllAvailable() throws DaoException;

    /**
     * Find order dishes.
     *
     * @param id the order id
     * @return the list of dishes for order with specified id
     * @throws DaoException the dao exception
     */
    List<Dish> findOrderDishes(long id) throws DaoException;

    /**
     * Find dish ingredients.
     *
     * @param id the dish id
     * @return the list of ingredients for specified dish
     * @throws DaoException the dao exception
     */
    List<Ingredient> findDishIngredients(Long id) throws DaoException;

    /**
     * Add dish ingredient.
     *
     * @param dishId       the dish id
     * @param ingredientId the ingredient id
     * @return the boolean, which describes success of adding new ingredient for specified dish
     * @throws DaoException the dao exception
     */
    boolean addDishIngredient(Long dishId, Long ingredientId) throws DaoException;

    /**
     * Remove dish ingredient.
     *
     * @param dishId       the dish id
     * @param ingredientId the ingredient id
     * @return the boolean, which describes success of removing ingredient for specified dish
     * @throws DaoException the dao exception
     */
    boolean removeDishIngredient(Long dishId, Long ingredientId) throws DaoException;

    /**
     * Count dishes according to the specified filter parameters.
     *
     * @param name                  the name
     * @param minPriceLine          the min price line
     * @param maxPriceLine          the max price line
     * @param minCaloriesAmountLine the min calories amount line
     * @param maxCaloriesAmountLine the max calories amount line
     * @param minAmountGramsLine    the min amount grams line
     * @param maxAmountGramsLine    the max amount grams line
     * @param dishStates            the dish states
     * @return the long. Total amount of suitable dishes
     */
    Long countFilteredDishes(String name, String minPriceLine, String maxPriceLine,
                             String minCaloriesAmountLine, String maxCaloriesAmountLine,
                             String minAmountGramsLine, String maxAmountGramsLine,
                             Boolean[] dishStates) throws DaoException;


    /**
     * Find dishes according to the specified filter parameters.
     *
     * @param name                  the name
     * @param minPriceLine          the min price line
     * @param maxPriceLine          the max price line
     * @param minCaloriesAmountLine the min calories amount line
     * @param maxCaloriesAmountLine the max calories amount line
     * @param minAmountGramsLine    the min amount grams line
     * @param maxAmountGramsLine    the max amount grams line
     * @param dishStates            the dish states
     * @param minPos                the min pos in pagination
     * @param maxPos                the max pos in pagination
     * @return the list of dishes with suitable parameters
     */
    List<Dish> findFilteredDishes(String name, String minPriceLine, String maxPriceLine,
                                  String minCaloriesAmountLine, String maxCaloriesAmountLine,
                                  String minAmountGramsLine, String maxAmountGramsLine,
                                  Boolean[] dishStates, long minPos, long maxPos) throws DaoException;
}
