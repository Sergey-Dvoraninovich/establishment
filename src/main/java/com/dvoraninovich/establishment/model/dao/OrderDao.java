package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface OrderDao extends BaseDao<Long, Order> {
    Optional<Order> findOrderInCreation(long userId) throws DaoException;
    Long insertAndGetId(Order order) throws DaoException;
    HashMap<Order, User> findAllOrdersWithUserinfo() throws DaoException;
    public Long countOrderDishesAmount(long id) throws DaoException;
}
