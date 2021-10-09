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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DishServiceImpl implements DishService {
    private static final Logger logger = LogManager.getLogger(DishServiceImpl.class);
    private static final String DISH_AVAILABLE = "AVAILABLE";
    private static final String DISH_DISABLED = "DISABLED";
    private static DishServiceImpl instance;
    private DishDao dishDao = DishDaoImpl.getInstance();
    private IngredientDao ingredientDao = IngredientDaoImpl.getInstance();

    private DishServiceImpl() {
    }

    public static DishServiceImpl getInstance() {
        if (instance == null) {
            instance = new DishServiceImpl();
            instance.dishDao = DishDaoImpl.getInstance();
            instance.ingredientDao = IngredientDaoImpl.getInstance();
        }
        return instance;
    }

    public static DishServiceImpl getInstance(DishDao dishDao, IngredientDao ingredientDao) {
        if (instance == null) {
            instance = new DishServiceImpl();
        }
        instance.dishDao = dishDao;
        instance.ingredientDao = ingredientDao;
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
    public List<Dish> findOrderDishes(long id) throws ServiceException {
        try {
            return dishDao.findOrderDishes(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Dish> findDishById(long id) throws ServiceException{
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
    public boolean invalidateDish(long id) throws ServiceException {
        try {
            return dishDao.disable(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean makeValidDish(long id) throws ServiceException {
        try {
            return dishDao.makeAvailable(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Ingredient> findDishIngredients(long id) throws ServiceException {
        try {
            return dishDao.findDishIngredients(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Ingredient> findUnusedDishIngredients(long id) throws ServiceException {
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
            logger.error("Impossible to find unused dish ingredients", e);
            throw new ServiceException("Impossible to find unused dish ingredients", e);
        }
    }

    @Override
    public boolean addDishIngredient(long dishId, long ingredientId) throws ServiceException {
        try {
            return dishDao.addDishIngredient(dishId, ingredientId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeDishIngredient(long dishId, long ingredientId) throws ServiceException {
        try {
            return dishDao.removeDishIngredient(dishId, ingredientId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long countFilteredDishes(String name, String minPriceLine, String maxPriceLine, String minCaloriesAmountLine, String maxCaloriesAmountLine, String minAmountGramsLine, String maxAmountGramsLine, String[] dishStates) throws ServiceException {
        try {
            Boolean[] states = new Boolean[dishStates.length];
            Integer pos = 0;
            for (String line : dishStates) {
                if (line.equals(DISH_AVAILABLE)) {
                    states[pos] = true;
                }
                if (line.equals(DISH_DISABLED)){
                    states[pos] = false;
                }
                pos += 1;
            }
            return dishDao.countFilteredDishes(name, minPriceLine, maxPriceLine, minCaloriesAmountLine, maxCaloriesAmountLine,
                    minAmountGramsLine, maxAmountGramsLine, states);
        } catch (DaoException e) {
            logger.error("Impossible to count filtered dishes", e);
            throw new ServiceException("Impossible to count filtered dishes", e);
        }
    }

    @Override
    public List<Dish> findFilteredDishes(String name, String minPriceLine, String maxPriceLine, String minCaloriesAmountLine, String maxCaloriesAmountLine, String minAmountGramsLine, String maxAmountGramsLine, String[] dishStates, long minPos, long maxPos) throws ServiceException {
        try {
            Boolean[] states = new Boolean[dishStates.length];
            Integer pos = 0;
            for (String line : dishStates) {
                if (line.equals(DISH_AVAILABLE)) {
                    states[pos] = true;
                }
                if (line.equals(DISH_DISABLED)){
                    states[pos] = false;
                }
                pos += 1;
            }
            return dishDao.findFilteredDishes(name, minPriceLine, maxPriceLine, minCaloriesAmountLine, maxCaloriesAmountLine,
                    minAmountGramsLine, maxAmountGramsLine, states, minPos, maxPos);
        } catch (DaoException e) {
            logger.error("Impossible to find filtered dishes", e);
            throw new ServiceException("Impossible to find filtered dishes", e);
        }
    }

    @Override
    public Long countFilteredDishes(String name, BigDecimal minPrice, BigDecimal maxPrice, Integer minCaloriesAmount, Integer maxCaloriesAmount, Integer minAmountGrams, Integer maxAmountGrams, String[] dishStates) throws ServiceException {
        name = name == null ? "" : name;
        String minPriceLine = minPrice == null ? "" : minPrice.toString();
        String maxPriceLine = maxPrice == null ? "" : maxPrice.toString();
        String minCaloriesAmountLine = minCaloriesAmount == null ? "" : minCaloriesAmount.toString();
        String maxCaloriesAmountLine = maxCaloriesAmount == null ? "" : maxCaloriesAmount.toString();
        String minAmountGramsLine = minAmountGrams == null ? "" : minAmountGrams.toString();
        String maxAmountGramsLine = maxAmountGrams == null ? "" : maxAmountGrams.toString();
        Boolean[] states = new Boolean[dishStates.length];
        Integer pos = 0;
        for (String line : dishStates) {
            if (line.equals(DISH_AVAILABLE)) {
                states[pos] = true;
            }
            if (line.equals(DISH_DISABLED)){
                states[pos] = false;
            }
            pos += 1;
        }
        try {
            return dishDao.countFilteredDishes(name, minPriceLine, maxPriceLine, minCaloriesAmountLine, maxCaloriesAmountLine,
                    minAmountGramsLine, maxAmountGramsLine, states);
        } catch (DaoException e) {
            logger.error("Impossible to count filtered dishes", e);
            throw new ServiceException("Impossible to count filtered dishes", e);
        }
    }

    @Override
    public List<Dish> findFilteredDishes(String name, BigDecimal minPrice, BigDecimal maxPrice, Integer minCaloriesAmount, Integer maxCaloriesAmount, Integer minAmountGrams, Integer maxAmountGrams, String[] dishStates, long minPos, long maxPos) throws ServiceException {
        name = name == null ? "" : name;
        String minPriceLine = minPrice == null ? "" : minPrice.toString();
        String maxPriceLine = maxPrice == null ? "" : maxPrice.toString();
        String minCaloriesAmountLine = minCaloriesAmount == null ? "" : minCaloriesAmount.toString();
        String maxCaloriesAmountLine = maxCaloriesAmount == null ? "" : maxCaloriesAmount.toString();
        String minAmountGramsLine = minAmountGrams == null ? "" : minAmountGrams.toString();
        String maxAmountGramsLine = maxAmountGrams == null ? "" : maxAmountGrams.toString();
        Boolean[] states = new Boolean[dishStates.length];
        Integer pos = 0;
        for (String line : dishStates) {
            if (line.equals(DISH_AVAILABLE)) {
                states[pos] = true;
            }
            if (line.equals(DISH_DISABLED)){
                states[pos] = false;
            }
            pos += 1;
        }
        try {
            return dishDao.findFilteredDishes(name, minPriceLine, maxPriceLine, minCaloriesAmountLine, maxCaloriesAmountLine,
                    minAmountGramsLine, maxAmountGramsLine, states, minPos, maxPos);
        } catch (DaoException e) {
            logger.error("Impossible to find filtered dishes", e);
            throw new ServiceException("Impossible to find filtered dishes", e);
        }
    }
}
