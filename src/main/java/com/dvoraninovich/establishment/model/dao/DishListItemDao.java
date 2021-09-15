package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.DishListItem;

import java.util.List;

public interface DishListItemDao extends BaseDao<Long, DishListItem>{
    List<DishListItem> findAllByOrderId(long id) throws DaoException;
}
