package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import javafx.util.Pair;

import java.util.List;

public interface OrderDao extends BaseDao<Long, Order> {
    List<Pair<Order, User>> findAllOrdersWithUserinfo() throws DaoException;
}
