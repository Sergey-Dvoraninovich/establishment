package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.DatabaseException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.pool.DatabaseConnectionPool;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface OrderDao extends BaseDao<Long, Order> {
    Optional<Order> findOrderInCreation(long userId) throws DaoException;
    Long insertAndGetId(Order order) throws DaoException;
    HashMap<Order, User> findAllOrdersWithUserinfo() throws DaoException;
    List<Order> findAllUserOrders(long userId) throws DaoException;
    Long countOrderDishesAmount(long id) throws DaoException;
    BigDecimal countOrderFinalPrice(long id) throws DaoException;
    Boolean updateOrderFinalPrice(long id) throws DaoException;
}
