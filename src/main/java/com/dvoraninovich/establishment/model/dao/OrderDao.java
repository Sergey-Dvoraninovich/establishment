package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

/**
 * {@code OrderDao} interface extends {@link BaseDao}
 * It provides functions to manipulate data of stored {@link Order}
 */
public interface OrderDao extends BaseDao<Long, Order> {
    /**
     * Insert and get id.
     *
     * @param order the order
     * @return the long of inserted id (0 if inserting failed)
     * @throws DaoException the dao exception
     */
    Long insertAndGetId(Order order) throws DaoException;

    /**
     * Find order in creation.
     *
     * @param userId the user id
     * @return the optional of order
     * @throws DaoException the dao exception
     */
    Optional<Order> findOrderInCreation(long userId) throws DaoException;

    /**
     * Count order dishes amount.
     *
     * @param id the id
     * @return the long of dishes amount
     * @throws DaoException the dao exception
     */
    Long countOrderDishesAmount(long id) throws DaoException;

    /**
     * Count order final price.
     *
     * @param id the id
     * @return the big decimal of order final price
     * @throws DaoException the dao exception
     */
    BigDecimal countOrderFinalPrice(long id) throws DaoException;

    /**
     * Update order final price.
     *
     * @param id the id
     * @return the boolean of updating
     * @throws DaoException the dao exception
     */
    Boolean updateOrderFinalPrice(long id) throws DaoException;

    /**
     * Count orders.
     *
     * @param userIdLine   the user id line
     * @param minPriceLine the min price line
     * @param maxPriceLine the max price line
     * @param orderStates  the order states
     * @param paymentTypes the payment types
     * @return the long of orders amount
     * @throws DaoException the dao exception
     */
    Long countOrders(String userIdLine, String minPriceLine, String maxPriceLine,
                     String[] orderStates, String[] paymentTypes) throws DaoException;

    /**
     * Find orders with users limit.
     *
     * @param userIdLine   the user id line
     * @param minPriceLine the min price line
     * @param maxPriceLine the max price line
     * @param minPos       the min pos of pagination
     * @param maxPos       the max pos od pagination
     * @param orderStates  the order states
     * @param paymentTypes the payment types
     * @return the hash map of Orders and User for each Order
     * @throws DaoException the dao exception
     */
    HashMap<Order, User> findOrdersWithUsersLimit(String userIdLine, String minPriceLine, String maxPriceLine, long minPos, long maxPos,
                                                  String[] orderStates, String[] paymentTypes) throws DaoException;
}
