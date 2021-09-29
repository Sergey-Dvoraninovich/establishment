package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Ingredient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * The interface Dish service.
 */
public interface DishService {
    /**
     * Find all.
     *
     * @return the list od dishes
     * @throws ServiceException the service exception
     */
    List<Dish> findAll() throws ServiceException;

    /**
     * Find order dishes.
     *
     * @param orderId the order id
     * @return the list of dishes for specified order
     * @throws ServiceException the service exception
     */
    List<Dish> findOrderDishes(long orderId) throws ServiceException;

    /**
     * Add dish.
     *
     * @param dish the dish
     * @return the boolean of adding
     * @throws ServiceException the service exception
     */
    boolean addDish(Dish dish) throws ServiceException;

    /**
     * Edit dish.
     *
     * @param dish the dish
     * @return the boolean result of editing
     * @throws ServiceException the service exception
     */
    boolean editDish(Dish dish) throws ServiceException;

    /**
     * Invalidate dish.
     *
     * @param dishId the dish id
     * @return the boolean result of dish invalidation
     * @throws ServiceException the service exception
     */
    boolean invalidateDish(long dishId) throws ServiceException;

    /**
     * Make valid dish.
     *
     * @param dishId the dish id
     * @return the boolean result of making dish available
     * @throws ServiceException the service exception
     */
    boolean makeValidDish(long dishId) throws ServiceException;

    /**
     * Find dish by id.
     *
     * @param dishId the dish id
     * @return the optional dish. Empty if there is no such dish
     * @throws ServiceException the service exception
     */
    Optional<Dish> findDishById(long dishId) throws ServiceException;

    /**
     * Find dish ingredients.
     *
     * @param dishId the dish id
     * @return the list of ingredient for specified dish
     * @throws ServiceException the service exception
     */
    List<Ingredient> findDishIngredients(long dishId) throws ServiceException;

    /**
     * Find unused dish ingredients.
     *
     * @param dishId the dish id
     * @return the list of unused ingredient for specified dish
     * @throws ServiceException the service exception
     */
    List<Ingredient> findUnusedDishIngredients(long dishId) throws ServiceException;

    /**
     * Add dish ingredient.
     *
     * @param dishId       the dish id
     * @param ingredientId the ingredient id
     * @return the boolean result of adding ingredient for dish
     * @throws ServiceException the service exception
     */
    boolean addDishIngredient(long dishId, long ingredientId) throws ServiceException;

    /**
     * Remove dish ingredient.
     *
     * @param dishId       the dish id
     * @param ingredientId the ingredient id
     * @return the boolean result of removing ingredient for dish
     * @throws ServiceException the service exception
     */
    boolean removeDishIngredient(long dishId, long ingredientId) throws ServiceException;

    /**
     * Count filtered dishes.
     *
     * @param name                  the name
     * @param minPriceLine          the min price line
     * @param maxPriceLine          the max price line
     * @param minCaloriesAmountLine the min calories amount line
     * @param maxCaloriesAmountLine the max calories amount line
     * @param minAmountGramsLine    the min amount grams line
     * @param maxAmountGramsLine    the max amount grams line
     * @param dishStates            the dish states
     * @return the long amount of dishes
     * @throws ServiceException the service exception
     */
    Long countFilteredDishes(String name, String minPriceLine, String maxPriceLine,
                             String minCaloriesAmountLine, String maxCaloriesAmountLine,
                             String minAmountGramsLine, String maxAmountGramsLine,
                             String[] dishStates) throws ServiceException;

    /**
     * Find filtered dishes.
     *
     * @param name                  the name
     * @param minPriceLine          the min price line
     * @param maxPriceLine          the max price line
     * @param minCaloriesAmountLine the min calories amount line
     * @param maxCaloriesAmountLine the max calories amount line
     * @param minAmountGramsLine    the min amount grams line
     * @param maxAmountGramsLine    the max amount grams line
     * @param dishStates            the dish states
     * @param minPos                the min pos
     * @param maxPos                the max pos
     * @return the list of dishes with specified parameters
     * @throws ServiceException the service exception
     */
    List<Dish> findFilteredDishes(String name, String minPriceLine, String maxPriceLine,
                                  String minCaloriesAmountLine, String maxCaloriesAmountLine,
                                  String minAmountGramsLine, String maxAmountGramsLine,
                                  String[] dishStates, long minPos, long maxPos) throws ServiceException;

    /**
     * Count filtered dishes long.
     * Specified for counting dishes with specified filter data form session
     *
     * @param name                  the name
     * @param minPriceLine          the min price line
     * @param maxPriceLine          the max price line
     * @param minCaloriesAmountLine the min calories amount line
     * @param maxCaloriesAmountLine the max calories amount line
     * @param minAmountGramsLine    the min amount grams line
     * @param maxAmountGramsLine    the max amount grams line
     * @param dishStates            the dish states
     * @return the long
     * @throws ServiceException the service exception
     */
    Long countFilteredDishes(String name, BigDecimal minPriceLine, BigDecimal maxPriceLine,
                             Integer minCaloriesAmountLine, Integer maxCaloriesAmountLine,
                             Integer minAmountGramsLine, Integer maxAmountGramsLine,
                             String[] dishStates) throws ServiceException;

    /**
     * Find filtered dishes list.
     * Specified for finding dishes with specified filter data form session
     *
     * @param name                  the name
     * @param minPriceLine          the min price line
     * @param maxPriceLine          the max price line
     * @param minCaloriesAmountLine the min calories amount line
     * @param maxCaloriesAmountLine the max calories amount line
     * @param minAmountGramsLine    the min amount grams line
     * @param maxAmountGramsLine    the max amount grams line
     * @param dishStates            the dish states
     * @param minPos                the min pos
     * @param maxPos                the max pos
     * @return the list of dishes  with specified parameters
     * @throws ServiceException the service exception
     */
    List<Dish> findFilteredDishes(String name, BigDecimal minPriceLine, BigDecimal maxPriceLine,
                                  Integer minCaloriesAmountLine, Integer maxCaloriesAmountLine,
                                  Integer minAmountGramsLine, Integer maxAmountGramsLine,
                                  String[] dishStates, long minPos, long maxPos) throws ServiceException;
}
