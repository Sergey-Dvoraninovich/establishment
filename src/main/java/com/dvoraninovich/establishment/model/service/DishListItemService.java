package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.DishListItem;

import java.util.List;
import java.util.Optional;

/**
 * The interface Dish list item service.
 */
public interface DishListItemService {
    /**
     * Find all.
     *
     * @return the list of dish list items
     * @throws ServiceException the service exception
     */
    List<DishListItem> findAll() throws ServiceException;

    /**
     * Find all by order id.
     *
     * @param orderId the order id
     * @return the list of dish list items
     * @throws ServiceException the service exception
     */
    List<DishListItem> findAllByOrderId(long orderId) throws ServiceException;

    /**
     * Find by order and dish id.
     *
     * @param orderId the order id
     * @param dishId  the dish id
     * @return the optional dish list item. Empty if there is no such dish list item
     * @throws ServiceException the service exception
     */
    Optional<DishListItem> findByOrderAndDishId(long orderId, long dishId) throws ServiceException;

    /**
     * Find by id.
     *
     * @param dishListItemId the dish list item id
     * @return the optional dish list item
     * @throws ServiceException the service exception
     */
    Optional<DishListItem> findById(long dishListItemId) throws ServiceException;

    /**
     * Insert.
     *
     * @param dishListItem the dish list item
     * @return the boolean result of inserting
     * @throws ServiceException the service exception
     */
    boolean insert(DishListItem dishListItem) throws ServiceException;

    /**
     * Update boolean.
     *
     * @param dishListItem the dish list item
     * @return the boolean result of updating
     * @throws ServiceException the service exception
     */
    boolean update(DishListItem dishListItem) throws ServiceException;

    /**
     * Delete.
     *
     * @param id the id
     * @return the boolean result of deleting
     * @throws ServiceException the service exception
     */
    boolean delete(long id) throws ServiceException;
}
