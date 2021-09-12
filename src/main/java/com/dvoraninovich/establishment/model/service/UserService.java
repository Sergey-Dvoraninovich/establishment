package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.exception.ServiceException;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;
import java.util.Optional;

public interface UserService {
    Optional<User> authenticate(String login, String password) throws ServiceException;
    Optional<User> register(User user, String password) throws ServiceException;

    Optional<User> findById(long id) throws ServiceException;
    Optional<User> findByLogin(String login) throws ServiceException;
    Optional<User> findByMail(String mail) throws ServiceException;

    Optional<String> getPassword(long id) throws ServiceException;
    Optional<String> getCode(long id) throws ServiceException;
    Optional<LocalDateTime> getCodeExpirationTime(long id) throws ServiceException;

    boolean isLoginUnique(String login);
    boolean isMailUnique(String login);

    boolean updateUser(User user) throws ServiceException;
}
