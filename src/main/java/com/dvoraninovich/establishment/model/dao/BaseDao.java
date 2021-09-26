package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * {@code BaseDao} is a top interface in the DAO hierarchy.
 * @param <K> the type parameter of id of stored entity
 * @param <T> the type parameter of stored entity
 */
public interface BaseDao<K, T> {
    /**
     * Find all stored entities.
     *
     * @return the list of specified entities
     * @throws DaoException the dao exception
     */
    List<T> findAll() throws DaoException;

    /**
     * Find single entity with specified id.
     *
     * @param id the id
     * @return the optional of specified entity
     * @throws DaoException the dao exception
     */
    Optional<T> findById(K id) throws DaoException;

    /**
     * Insert new entity
     *
     * @param t new entity to insert
     * @return the boolean, which describes the success of inserting process
     * @throws DaoException the dao exception
     */
    boolean insert(T t) throws DaoException;

    /**
     * Update existing entity.
     *
     * @param t new entity to update
     * @return the boolean, which describes the success of updating process
     * @throws DaoException the dao exception
     */
    boolean update(T t) throws DaoException;
}
