package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.dao.UserDao;
import com.dvoraninovich.establishment.model.dao.impl.UserDaoImpl;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.util.SaltGenerator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final Integer SALT_LENGTH = 16;
    private static UserServiceImpl instance;
    private UserDao userDao;
    private SaltGenerator saltGenerator;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
            instance.userDao = UserDaoImpl.getInstance();
            instance.saltGenerator = SaltGenerator.getInstance();
        }
        return instance;
    }

    public static UserServiceImpl getInstance(UserDao userDao, SaltGenerator saltGenerator) {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        instance.userDao = userDao;
        instance.saltGenerator = saltGenerator;
        return instance;
    }

    @Override
    public Optional<User> authenticate(String login, String password) throws ServiceException {
        Optional<User> optionalUser = Optional.empty();
        try {
            optionalUser = userDao.findUserByLogin(login);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                String salt = userDao.getSaltById(user.getId()).get();
                String passwordHash = makePasswordHash(password);
                passwordHash = makePasswordHash(passwordHash + salt);
                String benchmarkPasswordHash = userDao.getPasswordById(user.getId()).get();
                if (passwordHash.equals(benchmarkPasswordHash)) {
                    optionalUser = Optional.of(user);
                }
                else
                    optionalUser = Optional.empty();
            }
        } catch (DaoException e) {
            logger.warn("User authentication with login: " + login
                    + " and password" + password + " failed", e);
            throw new ServiceException(e);
        }
        return optionalUser;
    }

    @Override
    public Optional<User> register(User user, String password) throws ServiceException {
        Optional<User> registeredUser = Optional.empty();
        String salt = saltGenerator.makeSalt(SALT_LENGTH);
        String passwordHash = makePasswordHash(password);
        passwordHash = makePasswordHash(passwordHash + salt);
        try {
            registeredUser = userDao.findUserByLogin(user.getLogin());
            if (!registeredUser.isPresent()) {
                Long id = userDao.insertUser(user, passwordHash, salt);
                if (id != 0){
                    user.setId(id);
                    registeredUser = Optional.of(user);
                }
            }
        } catch (DaoException e) {
            logger.warn("User registration with login: " + user.getLogin() + " failed", e);
            throw new ServiceException(e);
        }
        return registeredUser;
    }

    @Override
    public boolean setPasswordById(long id, String password) throws ServiceException{
        try {
            String salt = userDao.getSaltById(id).get();
            String passwordHash = makePasswordHash(password);
            passwordHash = makePasswordHash(passwordHash + salt);
            return userDao.setPasswordById(id, passwordHash);
        } catch (DaoException e) {
            logger.error("Impossible to set password for user with id: " + id, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findById(long id) throws ServiceException {
        try {
            return userDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAdminsInfo() throws ServiceException {
        try {
            return userDao.getAdminsInfo();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long countUsers(String login, String mail, String phoneNumber, String cardNumber, String[] userStatuses, String[] userRoles) throws ServiceException {
        try {
            return userDao.countUsers(login, mail, phoneNumber, cardNumber, userStatuses, userRoles);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findFilteredUsers(String login, String mail, String phoneNumber, String cardNumber, String[] userStatuses, String[] userRoles, long minPos, long maxPos) throws ServiceException {
        try {
            return userDao.findFilteredUsers(login, mail, phoneNumber, cardNumber, userStatuses, userRoles, minPos, maxPos);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long countUsers(String login, String mail, String phoneNumber, String cardNumber, List<String> userStatusesList, List<String> userRolesList) throws ServiceException {
        String[] userStatuses = userStatusesList == null
                ? new String[0]
                : (String[]) userStatusesList.toArray();
        String[] userRoles = userRolesList == null
                ? new String[0]
                : (String[]) userRolesList.toArray();
        login = login == null ? "" : login;
        mail = mail == null ? "" : mail;
        phoneNumber = phoneNumber == null ? "" : phoneNumber;
        cardNumber = cardNumber == null ? "" : cardNumber;
        try {
            return userDao.countUsers(login, mail, phoneNumber, cardNumber, userStatuses, userRoles);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findFilteredUsers(String login, String mail, String phoneNumber, String cardNumber, List<String> userStatusesList, List<String> userRolesList, long minPos, long maxPos) throws ServiceException {
        String[] userStatuses = userStatusesList == null
                ? new String[0]
                : (String[]) userStatusesList.toArray();
        String[] userRoles = userRolesList == null
                ? new String[0]
                : (String[]) userRolesList.toArray();
        login = login == null ? "" : login;
        mail = mail == null ? "" : mail;
        phoneNumber = phoneNumber == null ? "" : phoneNumber;
        cardNumber = cardNumber == null ? "" : cardNumber;
        try {
            return userDao.findFilteredUsers(login, mail, phoneNumber, cardNumber, userStatuses, userRoles, minPos, maxPos);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean isLoginUnique(String login){
        boolean isSuccessful = false;
        try {
            Optional<User> optionalUser = userDao.findUserByLogin(login);
            isSuccessful = optionalUser.isPresent();
        } catch (DaoException e) {
            logger.error("Impossible to find out uniqueness of login " + login, e);
        }
        return isSuccessful;
    }

    @Override
    public boolean updateUser(User user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private String makePasswordHash(String password){
        String passwordHash = DigestUtils.sha256Hex(password);
        return passwordHash;
    }
}
