package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.dao.IngredientDao;
import com.dvoraninovich.establishment.model.dao.impl.IngredientDaoImpl;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.service.IngredientService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class IngredientServiceImpl implements IngredientService {
    private static final Logger logger = LogManager.getLogger(IngredientServiceImpl.class);
    private static IngredientServiceImpl instance;
    private IngredientDao ingredientDao;

    private IngredientServiceImpl() {
    }

    public static IngredientServiceImpl getInstance() {
        if (instance == null) {
            instance = new IngredientServiceImpl();
            instance.ingredientDao = IngredientDaoImpl.getInstance();
        }
        return instance;
    }

    public static IngredientServiceImpl getInstance(IngredientDao ingredientDao) {
        if (instance == null) {
            instance = new IngredientServiceImpl();
        }
        instance.ingredientDao = ingredientDao;
        return instance;
    }

    @Override
    public List<Ingredient> findAll() throws ServiceException{
        try {
            return ingredientDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Ingredient> findAllByName(String name) throws ServiceException {
        try {
            if (name.equals("")){
                return ingredientDao.findAll();
            }
            else {
                return ingredientDao.findAllByName(name);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Ingredient> findIngredientById(long id) throws ServiceException {
        try {
            return ingredientDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean addIngredient(Ingredient ingredient) throws ServiceException {
        try {
            return ingredientDao.insert(ingredient);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
