package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.dao.OrderDao;
import com.dvoraninovich.establishment.model.dao.impl.OrderDaoImpl;
import com.dvoraninovich.establishment.model.entity.*;
import com.dvoraninovich.establishment.model.service.OrderService;
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
                LocalDateTime defaultOrderTime = LocalDateTime.of(1971, 1, 1, 0, 0, 1);
                LocalDateTime defaultFinishTime = LocalDateTime.of(2038, 1, 19, 3, 14, 7);
                Order defaultOrder = Order.builder()
                        .setUserId(customerId)
                        .setOrderState(IN_CREATION)
                        .setOrderTime(defaultOrderTime)
                        .setFinishTime(defaultFinishTime)
                        .setPaymentType(CASH)
                        .setBonusesInPayment(new BigDecimal("0"))
                        .setFinalPrice(new BigDecimal("0.00"))
                        .build();
                Long orderId = orderDao.insertAndGetId(defaultOrder);
                defaultOrder.setId(orderId);
                order = Optional.of(defaultOrder);
            }

        } catch (DaoException e) {
            logger.info("Impossible to find customer (id = " + customerId + ") basket or create new one", e);
            throw new ServiceException("Impossible to find customer (id = " + customerId + ") basket or create new one", e);
        }
        return order;
    }

    @Override
    public BigDecimal countNewOrderPrice(Order order, BigDecimal newBonusesAmount) throws ServiceException {
        BigDecimal currentBonusesAmount = order.getBonusesInPayment().divide(new BigDecimal(100));
        newBonusesAmount = newBonusesAmount.divide(new BigDecimal(100));
        BigDecimal finalPrice = order.getFinalPrice();
        BigDecimal resultPrice = finalPrice.add(currentBonusesAmount).subtract(newBonusesAmount);
        return resultPrice;
    }

    @Override
    public Long countFilteredOrders(String userIdLine, String minPriceLine, String maxPriceLine,
                                    String[] orderStates, String[] paymentTypes) throws ServiceException {
        try {
            return orderDao.countOrders(userIdLine, minPriceLine, maxPriceLine, orderStates, paymentTypes);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public HashMap<Order, User> findFilteredOrdersWithUsers(String userIdLine, String minPriceLine, String maxPriceLine, long minPos, long maxPos,
                                                            String[] orderStates, String[] paymentTypes) throws ServiceException {
        try {
            return orderDao.findOrdersWithUsersLimit(userIdLine, minPriceLine, maxPriceLine, minPos, maxPos, orderStates, paymentTypes);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long countFilteredOrders(Long userId, BigDecimal minPrice, BigDecimal maxPrice, String[] orderStates, String[] paymentTypes) throws ServiceException {
        return null;
    }

    @Override
    public HashMap<Order, User> findFilteredOrdersWithUsers(Long userId, BigDecimal minPrice, BigDecimal maxPrice, long minPos, long maxPos, String[] orderStates, String[] paymentTypes) throws ServiceException {
        return null;
    }

    @Override
    public Long countDishesAmount(long orderId) throws ServiceException {
        try {
            return orderDao.countOrderDishesAmount(orderId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BigDecimal countOrderFinalPrice(long id) throws ServiceException {
        try {
            return orderDao.countOrderFinalPrice(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Boolean updateOrderFinalPrice(long id) throws ServiceException {
        try {
            return orderDao.updateOrderFinalPrice(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
