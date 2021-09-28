package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll() throws ServiceException;
    Optional<Order> findById(long id) throws ServiceException;
    boolean insert(Order order) throws ServiceException;
    boolean update(Order order) throws ServiceException;

    Optional<Order> getCustomerBasket(long customerId) throws ServiceException;
    Long countDishesAmount(long orderId) throws ServiceException;
    BigDecimal countNewOrderPrice(Order order, BigDecimal newBonusesAmount) throws ServiceException;
    BigDecimal countOrderFinalPrice(long id) throws ServiceException;
    Boolean updateOrderFinalPrice(long id) throws ServiceException;

    Long countFilteredOrders(String userIdLine, String minPriceLine, String maxPriceLine,
                             String[] orderStates, String[] paymentTypes) throws ServiceException;
    HashMap<Order, User> findFilteredOrdersWithUsers(String userIdLine, String minPriceLine, String maxPriceLine, long minPos, long maxPos,
                                                     String[] orderStates, String[] paymentTypes) throws ServiceException;

    Long countFilteredOrders(Long userId, BigDecimal minPrice, BigDecimal maxPrice,
                             String[] orderStates, String[] paymentTypes) throws ServiceException;
    HashMap<Order, User> findFilteredOrdersWithUsers(Long userId, BigDecimal minPrice, BigDecimal maxPrice, long minPos, long maxPos,
                                                     String[] orderStates, String[] paymentTypes) throws ServiceException;
}
