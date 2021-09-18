package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.dao.IngredientDao;
import com.dvoraninovich.establishment.model.dao.OrderDao;
import com.dvoraninovich.establishment.model.dao.impl.IngredientDaoImpl;
import com.dvoraninovich.establishment.model.dao.impl.OrderDaoImpl;
import com.dvoraninovich.establishment.model.entity.*;
import com.dvoraninovich.establishment.model.service.OrderService;
import javafx.util.Pair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.model.entity.OrderState.IN_CREATION;
import static com.dvoraninovich.establishment.model.entity.PaymentType.CASH;

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
        try {
            return orderDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean insert(Order order) throws ServiceException {
        try {
            return orderDao.insert(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Order order) throws ServiceException {
        try {
            return orderDao.update(order);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Order> getCustomerBasket(long customerId) throws ServiceException {
        Optional<Order> order = Optional.empty();
        try {
            order = orderDao.findOrderInCreation(customerId);
            if (!order.isPresent()){
                Order defaultOrder = Order.builder()
                        .setUserId(customerId)
                        .setOrderState(IN_CREATION)
                        .setOrderTime(LocalDateTime.now())
                        .setFinishTime(LocalDateTime.now())
                        .setPaymentType(CASH)
                        .setBonusesInPayment(new BigDecimal(0.00))
                        .setFinalPrice(new BigDecimal(0.00))
                        .build();
                Long orderId = orderDao.insertAndGetId(defaultOrder);
                defaultOrder.setId(orderId);
                order = Optional.of(defaultOrder);
                System.out.println("default order " + defaultOrder);
            }
            else {
                System.out.println("order from db " + order.get());
            }

        } catch (DaoException e) {
            logger.info("Impossible to find customer (id = " + customerId + ") basket or create new one", e);
            throw new ServiceException("Impossible to find customer (id = " + customerId + ") basket or create new one", e);
        }
        return order;
    }

    @Override
    public HashMap<Order, User> findAllOrdersWithUserinfo() throws ServiceException {
        try {
            return orderDao.findAllOrdersWithUserinfo();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long countDishesAmount(long orderId) throws ServiceException {
        try {
            return orderDao.countOrderDishesAmount(orderId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
