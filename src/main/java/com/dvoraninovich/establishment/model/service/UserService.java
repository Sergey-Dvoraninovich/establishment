package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.exception.ServiceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String login, String password) throws ServiceException;
    Optional<User> register(User user, String password) throws ServiceException;
    boolean setPasswordById(Long id, String password) throws ServiceException;

    Optional<User> findById(long id) throws ServiceException;

    Optional<String> getCode(long id) throws ServiceException;
    Optional<LocalDateTime> getCodeExpirationTime(long id) throws ServiceException;

    Long countUsers(String login, String mail, String phoneNumber, String cardNumber,
                    String[] userStatuses, String[] userRoles) throws ServiceException;
    List<User> findFilteredUsers(String login, String mail, String phoneNumber, String cardNumber, String[] userStatuses,
                                 String[] userRoles, long minPos, long maxPos) throws ServiceException;

    Long countUsers(String login, String mail, String phoneNumber, String cardNumber,
                    List<String> userStatuses, List<String> userRoles) throws ServiceException;
    List<User> findFilteredUsers(String login, String mail, String phoneNumber, String cardNumber, List<String> userStatuses,
                                 List<String> userRoles, long minPos, long maxPos) throws ServiceException;

    boolean isLoginUnique(String login);

    boolean updateUser(User user) throws ServiceException;
}
