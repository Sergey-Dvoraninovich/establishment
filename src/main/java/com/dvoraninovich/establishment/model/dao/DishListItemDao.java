package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.DishListItem;

import java.util.List;
import java.util.Optional;

/**
 * {@code DishListItemDao} interface extends {@link BaseDao}
 * It provides functions to manipulate data of stored {@link DishListItem}
 */
public interface DishListItemDao extends BaseDao<Long, DishListItem>{
    /**
     * Find all by order id.
     *
     * @param orderId the order id
     * @return the list of dish list items
     * @throws DaoException the dao exception
     */
    List<DishListItem> findAllByOrderId(long orderId) throws DaoException;

    /**
     * Find by order and dish id.
     *
     * @param orderId the order id
     * @param dishId  the dish id
     * @return the optional of dish list item. Empty if there is no such dish list items
     * @throws DaoException the dao exception
     */
    Optional<DishListItem> findByOrderAndDishId(long orderId, long dishId) throws DaoException;

    /**
     * Delete.
     *
     * @param id the dish list item id
     * @return the boolean result of deletion
     * @throws DaoException the dao exception
     */
    boolean delete(long id) throws DaoException;
}
