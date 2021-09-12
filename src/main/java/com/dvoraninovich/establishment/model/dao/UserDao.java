package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.exception.DaoException;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    // TODO description
    public Long insertUser(User user, String passwordHash, String salt, String code) throws DaoException;

    // TODO description
    Optional<User> findUserByEmail(String email) throws DaoException;

    // TODO description
    Optional<User> findUserByLogin(String login) throws DaoException;

    // TODO description
    Optional<User> findUserByPhoneNum(String phoneNum) throws DaoException;

    // TODO description
    boolean setPasswordById(Long id, String password) throws DaoException;

    // TODO description
    boolean setSaltById(Long id, String salt) throws DaoException;

    // TODO description
    boolean setCodeById(Long id, String code) throws DaoException;

    // TODO description
    Optional<String> getPasswordById(Long id) throws DaoException;

    // TODO description
    Optional<String> getSaltById(Long id) throws DaoException;

    // TODO description
    Optional<String> getCodeById(Long id) throws DaoException;

    // TODO description
    Optional<LocalDateTime> getCodeExpirationTimeById(Long id) throws DaoException;

    // TODO description
    boolean delete(Long id) throws DaoException;
}
