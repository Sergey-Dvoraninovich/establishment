package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.exception.DaoException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    Long insertUser(User user, String passwordHash, String salt, String code) throws DaoException;

    Optional<User> findUserByLogin(String login) throws DaoException;

    boolean setPasswordById(Long id, String password) throws DaoException;

    Optional<String> getPasswordById(Long id) throws DaoException;

    Optional<String> getSaltById(Long id) throws DaoException;

    Optional<String> getCodeById(Long id) throws DaoException;

    Optional<LocalDateTime> getCodeExpirationTimeById(Long id) throws DaoException;

    boolean delete(Long id) throws DaoException;

    Long countUsers(String login, String mail, String phoneNumber, String cardNumber,
                    String[] userStatuses, String[] userRoles) throws DaoException;
    List<User> findFilteredUsers(String login, String mail, String phoneNumber, String cardNumber, String[] userStatuses,
                                 String[] userRoles, long minPos, long maxPos) throws DaoException;
}
