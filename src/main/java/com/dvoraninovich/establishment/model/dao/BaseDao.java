package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface BaseDao<K, T> {
    List<T> findAll() throws DaoException;

    Optional<T> findById(K id) throws DaoException;

    boolean insert(T t) throws DaoException;

    boolean update(T t) throws DaoException;
}
