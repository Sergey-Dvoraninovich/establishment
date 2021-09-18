package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.dao.DishDao;
import com.dvoraninovich.establishment.model.dao.DishListItemDao;
import com.dvoraninovich.establishment.model.dao.IngredientDao;
import com.dvoraninovich.establishment.model.dao.impl.DishDaoImpl;
import com.dvoraninovich.establishment.model.dao.impl.DishListItemDaoImpl;
import com.dvoraninovich.establishment.model.dao.impl.IngredientDaoImpl;
import com.dvoraninovich.establishment.model.entity.DishListItem;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.service.DishListItemService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class DishListItemServiceImpl implements DishListItemService {
    private static final Logger logger = LogManager.getLogger(DishServiceImpl.class);
    private DishListItemDao dishListItemDao = DishListItemDaoImpl.getInstance();
    private static DishListItemServiceImpl instance;

    private DishListItemServiceImpl() {
    }

    public static DishListItemServiceImpl getInstance() {
        if (instance == null) {
            instance = new DishListItemServiceImpl();
        }
        return instance;
    }

    @Override
    public List<DishListItem> findAll() throws ServiceException {
        try {
            return dishListItemDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<DishListItem> findAllByOrderId(long id) throws ServiceException {
        try {
            return dishListItemDao.findAllByOrderId(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<DishListItem> findByOrderAndDishId(Long orderId, Long dishId) throws ServiceException {
        try {
            return dishListItemDao.findByOrderAndDishId(orderId, dishId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<DishListItem> findById(long id) throws ServiceException {
        try {
            return dishListItemDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean insert(DishListItem dishListItem) throws ServiceException {
        try {
            return dishListItemDao.insert(dishListItem);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(DishListItem dishListItem) throws ServiceException {
        try {
            return dishListItemDao.update(dishListItem);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            return dishListItemDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
