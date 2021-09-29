package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The interface Order service.
 */
public interface OrderService {
    /**
     * Find all.
     *
     * @return the list of orders
     * @throws ServiceException the service exception
     */
    List<Order> findAll() throws ServiceException;

    /**
     * Find by id.
     *
     * @param orderId the id of order
     * @return the optional order. Empty if there id no such order
     * @throws ServiceException the service exception
     */
    Optional<Order> findById(long orderId) throws ServiceException;

    /**
     * Insert.
     *
     * @param order the order
     * @return the boolean result of inserting
     * @throws ServiceException the service exception
     */
    boolean insert(Order order) throws ServiceException;

    /**
     * Update.
     *
     * @param order the order
     * @return the boolean result of updating
     * @throws ServiceException the service exception
     */
    boolean update(Order order) throws ServiceException;

    /**
     * Gets customer basket.
     * Creates new basket for user with specified id if it wasn't created before
     *
     * @param customerId the customer id
     * @return the optional customer basket.
     * @throws ServiceException the service exception
     */
    Optional<Order> getCustomerBasket(long customerId) throws ServiceException;

    /**
     * Count dishes amount.
     *
     * @param orderId the order id
     * @return the long of dishes amount
     * @throws ServiceException the service exception
     */
    Long countDishesAmount(long orderId) throws ServiceException;

    /**
     * Count new order price.
     *
     * @param order            the order
     * @param newBonusesAmount the new bonuses amount
     * @return the big decimal of new order price
     * @throws ServiceException the service exception
     */
    BigDecimal countNewOrderPrice(Order order, BigDecimal newBonusesAmount) throws ServiceException;

    /**
     * Count order final price.
     *
     * @param orderId the id of order
     * @return the big decimal of order final price
     * @throws ServiceException the service exception
     */
    BigDecimal countOrderFinalPrice(long orderId) throws ServiceException;

    /**
     * Update order final price.
     *
     * @param orderId the id of order
     * @return the boolean result of updating
     * @throws ServiceException the service exception
     */
    Boolean updateOrderFinalPrice(long orderId) throws ServiceException;

    /**
     * Count filtered orders.
     *
     * @param userIdLine   the user id line
     * @param minPriceLine the min price line
     * @param maxPriceLine the max price line
     * @param orderStates  the order states
     * @param paymentTypes the payment types
     * @return the long amount of orders
     * @throws ServiceException the service exception
     */
    Long countFilteredOrders(String userIdLine, String minPriceLine, String maxPriceLine,
                             String[] orderStates, String[] paymentTypes) throws ServiceException;

    /**
     * Find filtered orders with users.
     *
     * @param userIdLine   the user id line
     * @param minPriceLine the min price line
     * @param maxPriceLine the max price line
     * @param orderStates  the order states
     * @param paymentTypes the payment types
     * @param minPos       the min pos of pagination
     * @param maxPos       the max pos of pagination
     * @return the hash map of orders and user for each order
     * @throws ServiceException the service exception
     */
    HashMap<Order, User> findFilteredOrdersWithUsers(String userIdLine, String minPriceLine, String maxPriceLine, String[] orderStates,
                                                     String[] paymentTypes, long minPos, long maxPos) throws ServiceException;

    /**
     * Count filtered orders long.
     * Specified for counting orders with specified filter data form session
     *
     * @param userIdLine   the user id line
     * @param minPrice     the Big Decimal min price
     * @param maxPrice     the Big Decimal max price
     * @param orderStates  the List of order states
     * @param paymentTypes the List of payment types
     * @return the long amount of orders
     * @throws ServiceException the service exception
     */
    Long countFilteredOrders(String userIdLine, BigDecimal minPrice, BigDecimal maxPrice,
                             List<String> orderStates, List<String> paymentTypes) throws ServiceException;

    /**
     * Find filtered orders with users.
     * Specified for counting orders with specified filter data form session
     *
     * @param userIdLine   the user id line
     * @param minPrice     the Big Decimal min price
     * @param maxPrice     the Big Decimal max price
     * @param orderStates  the List of order states
     * @param paymentTypes the List of payment types
     * @param minPos       the min pos of pagination
     * @param maxPos       the max pos of pagination
     * @return the hash map of orders and user for each order
     * @throws ServiceException the service exception
     */
    HashMap<Order, User> findFilteredOrdersWithUsers(String userIdLine, BigDecimal minPrice, BigDecimal maxPrice, List<String> orderStates,
                                                     List<String> paymentTypes, long minPos, long maxPos) throws ServiceException;
}
