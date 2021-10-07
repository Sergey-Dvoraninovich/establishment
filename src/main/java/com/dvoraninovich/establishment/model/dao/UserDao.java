package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.exception.DaoException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * {@code UserDao} interface extends {@link BaseDao}
 * It provides functions to manipulate data of stored {@link User}
 */
public interface UserDao extends BaseDao<Long, User> {
    /**
     * Insert user.
     *
     * @param user         the user
     * @param passwordHash the password hash
     * @param salt         the salt
     * @return the long of User id (0 if inserting failed)
     * @throws DaoException the dao exception
     */
    Long insertUser(User user, String passwordHash, String salt) throws DaoException;

    /**
     * Find user by login.
     *
     * @param login the login of user
     * @return the optional of user
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByLogin(String login) throws DaoException;

    /**
     * Sets password by id.
     *
     * @param id       the id
     * @param password the password
     * @return the boolean result of setting
     * @throws DaoException the dao exception
     */
    boolean setPasswordById(Long id, String password) throws DaoException;

    /**
     * Gets password by id.
     *
     * @param id the id
     * @return the optional password by id
     * @throws DaoException the dao exception
     */
    Optional<String> getPasswordById(Long id) throws DaoException;

    /**
     * Gets salt by id.
     *
     * @param id the id
     * @return the optional salt by id
     * @throws DaoException the dao exception
     */
    Optional<String> getSaltById(Long id) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean result of deleting
     * @throws DaoException the dao exception
     */
    boolean delete(Long id) throws DaoException;


    /**
     * Gets admins info.
     *
     * @return the admins info
     * @throws DaoException the dao exception
     */
    List<User> getAdminsInfo() throws DaoException;

    /**
     * Count users.
     *
     * @param login        the login
     * @param mail         the mail
     * @param phoneNumber  the phone number
     * @param cardNumber   the card number
     * @param userStatuses the user statuses
     * @param userRoles    the user roles
     * @return the long of users amount
     * @throws DaoException the dao exception
     */
    Long countUsers(String login, String mail, String phoneNumber, String cardNumber,
                    String[] userStatuses, String[] userRoles) throws DaoException;

    /**
     * Find filtered users list.
     *
     * @param login        the login
     * @param mail         the mail
     * @param phoneNumber  the phone number
     * @param cardNumber   the card number
     * @param userStatuses the user statuses
     * @param userRoles    the user roles
     * @param minPos       the min pos of pagination
     * @param maxPos       the max pos of pagination
     * @return the list of filtered users
     * @throws DaoException the dao exception
     */
    List<User> findFilteredUsers(String login, String mail, String phoneNumber, String cardNumber, String[] userStatuses,
                                 String[] userRoles, long minPos, long maxPos) throws DaoException;
}
