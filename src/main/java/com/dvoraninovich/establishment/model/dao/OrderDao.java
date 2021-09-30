package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

public interface OrderDao extends BaseDao<Long, Order> {
    Long insertAndGetId(Order order) throws DaoException;

    Optional<Order> findOrderInCreation(long userId) throws DaoException;

    Long countOrderDishesAmount(long id) throws DaoException;
    BigDecimal countOrderFinalPrice(long id) throws DaoException;
    Boolean updateOrderFinalPrice(long id) throws DaoException;

    Long countOrders(String userIdLine, String minPriceLine, String maxPriceLine,
                     String[] orderStates, String[] paymentTypes) throws DaoException;
    HashMap<Order, User> findOrdersWithUsersLimit(String userIdLine, String minPriceLine, String maxPriceLine, long minPos, long maxPos,
                                                  String[] orderStates, String[] paymentTypes) throws DaoException;
}
