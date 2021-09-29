package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.exception.ServiceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Authenticate.
     *
     * @param login    the login
     * @param password the password
     * @return the optional user. User is empty if authentication failed
     * @throws ServiceException the service exception
     */
    Optional<User> authenticate(String login, String password) throws ServiceException;

    /**
     * Register.
     *
     * @param user     the user
     * @param password the password
     * @return the optional user. User is empty if registration failed
     * @throws ServiceException the service exception
     */
    Optional<User> register(User user, String password) throws ServiceException;

    /**
     * Sets password by specified User id.
     *
     * @param id       the id of User
     * @param password the password
     * @return the the result of setting password
     * @throws ServiceException the service exception
     */
    boolean setPasswordById(Long id, String password) throws ServiceException;

    /**
     * Find by id.
     *
     * @param id the id of User
     * @return the optional User. User is empty if there is no such user
     * @throws ServiceException the service exception
     */
    Optional<User> findById(long id) throws ServiceException;

    /**
     * Gets code for User with specified id.
     *
     * @param id the id of User
     * @return the code. empty if there is no such user
     * @throws ServiceException the service exception
     */
    Optional<String> getCode(long id) throws ServiceException;

    /**
     * Gets code expiration time for User with specified id.
     *
     * @param id the id of User
     * @return the code expiration time
     * @throws ServiceException the service exception
     */
    Optional<LocalDateTime> getCodeExpirationTime(long id) throws ServiceException;

    /**
     * Count users long.
     *
     * @param login        the login
     * @param mail         the mail
     * @param phoneNumber  the phone number
     * @param cardNumber   the card number
     * @param userStatuses the user statuses
     * @param userRoles    the user roles
     * @return the long
     * @throws ServiceException the service exception
     */
    Long countUsers(String login, String mail, String phoneNumber, String cardNumber,
                    String[] userStatuses, String[] userRoles) throws ServiceException;

    /**
     * Find filtered users list.
     *
     * @param login        the login
     * @param mail         the mail
     * @param phoneNumber  the phone number
     * @param cardNumber   the card number
     * @param userStatuses the user statuses
     * @param userRoles    the user roles
     * @param minPos       the min pos
     * @param maxPos       the max pos
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findFilteredUsers(String login, String mail, String phoneNumber, String cardNumber, String[] userStatuses,
                                 String[] userRoles, long minPos, long maxPos) throws ServiceException;

    /**
     * Count users long.
     *
     * @param login        the login
     * @param mail         the mail
     * @param phoneNumber  the phone number
     * @param cardNumber   the card number
     * @param userStatuses the user statuses
     * @param userRoles    the user roles
     * @return the long
     * @throws ServiceException the service exception
     */
    Long countUsers(String login, String mail, String phoneNumber, String cardNumber,
                    List<String> userStatuses, List<String> userRoles) throws ServiceException;

    /**
     * Find filtered users list.
     *
     * @param login        the login
     * @param mail         the mail
     * @param phoneNumber  the phone number
     * @param cardNumber   the card number
     * @param userStatuses the user statuses
     * @param userRoles    the user roles
     * @param minPos       the min pos
     * @param maxPos       the max pos
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findFilteredUsers(String login, String mail, String phoneNumber, String cardNumber, List<String> userStatuses,
                                 List<String> userRoles, long minPos, long maxPos) throws ServiceException;

    /**
     * Is login unique boolean.
     *
     * @param login the login
     * @return the boolean
     */
    boolean isLoginUnique(String login);

    /**
     * Update user boolean.
     *
     * @param user the user
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateUser(User user) throws ServiceException;
}
