package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.dao.IngredientDao;
import com.dvoraninovich.establishment.model.dao.OrderDao;
import com.dvoraninovich.establishment.model.dao.impl.IngredientDaoImpl;
import com.dvoraninovich.establishment.model.dao.impl.OrderDaoImpl;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.OrderService;
import javafx.util.Pair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
    private OrderDao orderDao = OrderDaoImpl.getInstance();
    private static OrderServiceImpl instance;

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Order> findAll() throws ServiceException {
        try {
            return orderDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Order> findById(long id) throws ServiceException {
        throw  new UnsupportedOperationException();
    }

    @Override
    public boolean insert(Order order) throws ServiceException {
        throw  new UnsupportedOperationException();
    }

    @Override
    public boolean update(Order order) throws ServiceException {
        throw  new UnsupportedOperationException();
    }

    @Override
    public List<Pair<Order, User>> findAllOrdersWithUserinfo() throws ServiceException {
        try {
            return orderDao.findAllOrdersWithUserinfo();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
