package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.DishListItem;

import java.util.List;
import java.util.Optional;

public interface DishListItemDao extends BaseDao<Long, DishListItem>{
    List<DishListItem> findAllByOrderId(long id) throws DaoException;
    Optional<DishListItem> findByOrderAndDishId(Long orderId, Long dishId) throws DaoException;
    boolean delete(long id) throws DaoException;
}
