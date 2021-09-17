package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.DishListItem;
import com.dvoraninovich.establishment.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface DishListItemService {
    List<DishListItem> findAll() throws ServiceException;

    List<DishListItem> findAllByOrderId(long id) throws ServiceException;
    Optional<DishListItem> findByOrderAndDishId(Long orderId, Long dishId) throws ServiceException;

    Optional<DishListItem> findById(long id) throws ServiceException;

    boolean insert(DishListItem dishListItem) throws ServiceException;

    boolean update(DishListItem dishListItem) throws ServiceException;
}
