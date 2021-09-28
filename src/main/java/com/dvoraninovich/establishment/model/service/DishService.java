package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Ingredient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DishService {
    List<Dish> findAll() throws ServiceException;
    List<Dish> findAllAvailable() throws ServiceException;
    List<Dish> findOrderDishes(long id) throws ServiceException;

    boolean addDish(Dish dish) throws ServiceException;
    boolean editDish(Dish dish) throws ServiceException;
    boolean invalidateDish(Long id) throws ServiceException;
    boolean makeValidDish(Long id) throws ServiceException;
    Optional<Dish> findDishById(Long id) throws ServiceException;

    List<Ingredient> findDishIngredients(Long id) throws ServiceException;
    List<Ingredient> findUnusedDishIngredients(Long id) throws ServiceException;
    boolean addDishIngredient(Long dishId, Long ingredientId) throws ServiceException;
    boolean removeDishIngredient(Long dishId, Long ingredientId) throws ServiceException;

    Long countFilteredDishes(String name, String minPriceLine, String maxPriceLine,
                             String minCaloriesAmountLine, String maxCaloriesAmountLine,
                             String minAmountGramsLine, String maxAmountGramsLine,
                             String[] dishStates) throws ServiceException;
    List<Dish> findFilteredDishes(String name, String minPriceLine, String maxPriceLine,
                                  String minCaloriesAmountLine, String maxCaloriesAmountLine,
                                  String minAmountGramsLine, String maxAmountGramsLine,
                                  String[] dishStates, long minPos, long maxPos) throws ServiceException;

    Long countFilteredDishes(String name, BigDecimal minPriceLine, BigDecimal maxPriceLine,
                             Integer minCaloriesAmountLine, Integer maxCaloriesAmountLine,
                             Integer minAmountGramsLine, Integer maxAmountGramsLine,
                             String[] dishStates) throws ServiceException;
    List<Dish> findFilteredDishes(String name, BigDecimal minPriceLine, BigDecimal maxPriceLine,
                                  Integer minCaloriesAmountLine, Integer maxCaloriesAmountLine,
                                  Integer minAmountGramsLine, Integer maxAmountGramsLine,
                                  String[] dishStates, long minPos, long maxPos) throws ServiceException;
}
