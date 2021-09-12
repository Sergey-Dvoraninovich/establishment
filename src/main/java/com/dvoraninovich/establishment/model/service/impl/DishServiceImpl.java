package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.dao.DishDao;
import com.dvoraninovich.establishment.model.dao.IngredientDao;
import com.dvoraninovich.establishment.model.dao.impl.DishDaoImpl;
import com.dvoraninovich.establishment.model.dao.impl.IngredientDaoImpl;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.service.DishService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DishServiceImpl implements DishService {
    private static final Logger logger = LogManager.getLogger(DishServiceImpl.class);
    private DishDao dishDao = DishDaoImpl.getInstance();
    private IngredientDao ingredientDao = IngredientDaoImpl.getInstance();
    private static DishServiceImpl instance;

    private DishServiceImpl() {
    }

    public static DishServiceImpl getInstance() {
        if (instance == null) {
            instance = new DishServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Dish> findAll() throws ServiceException {
        try {
            return dishDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Dish> findAllAvailable() throws ServiceException {
        try {
            return dishDao.findAllAvailable();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Dish> findDishById(Long id) throws ServiceException{
        try {
            return dishDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addDish(Dish dish) throws ServiceException {
        try {
            return dishDao.insert(dish);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean editDish(Dish dish) throws ServiceException {
        try {
            return dishDao.update(dish);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean invalidateDish(Long id) throws ServiceException {
        try {
            return dishDao.disable(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean makeValidDish(Long id) throws ServiceException {
        try {
            return dishDao.makeAvailable(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Ingredient> findDishIngredients(Long id) throws ServiceException {
        try {
            return dishDao.findDishIngredients(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Ingredient> findUnusedDishIngredients(Long id) throws ServiceException {
        try {
            List<Ingredient> dishIngredients = dishDao.findDishIngredients(id);
            List<Ingredient> ingredients = ingredientDao.findAll();
            for (Ingredient ingredient: dishIngredients) {
                if (ingredients.contains(ingredient)) {
                    ingredients.remove(ingredient);
                }
            }
            return ingredients;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addDishIngredient(Long dishId, Long ingredientId) throws ServiceException {
        try {
            return dishDao.addDishIngredient(dishId, ingredientId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeDishIngredient(Long dishId, Long ingredientId) throws ServiceException {
        try {
            return dishDao.removeDishIngredient(dishId, ingredientId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
