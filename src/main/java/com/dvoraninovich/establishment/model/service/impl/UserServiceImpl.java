package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.controller.command.validator.UserValidator;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.dao.UserDao;
import com.dvoraninovich.establishment.model.dao.impl.UserDaoImpl;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.util.CodeGenerator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.dvoraninovich.establishment.controller.command.SessionAttribute.ORDER;
import static com.dvoraninovich.establishment.controller.command.SessionAttribute.TOO_MANY_BONUSES;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private UserDao userDao = UserDaoImpl.getInstance();
    private static UserServiceImpl instance;
    private CodeGenerator codeGenerator = CodeGenerator.getInstance();
    private static final String SALT_ITEMS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String CODE_ITEMS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Integer SALT_LENGTH = 16;
    private static final Integer CODE_LENGTH = 16;

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    public Optional<User> authenticate(String login, String password) throws ServiceException {
        Optional<User> user = Optional.empty();
        try {
            user = userDao.findUserByLogin(login);
            if (user.isPresent()) {
                User presentedUser = user.get();
                String passwordHash = makePasswordHash(password);
                String benchmarkPasswordHash = userDao.getPasswordById(presentedUser.getId()).get();
                if (passwordHash.equals(benchmarkPasswordHash)) {
                    user = Optional.of(presentedUser);
                }
                else
                    user = Optional.empty();
            }
        } catch (DaoException e) {
            logger.warn("User authentication with login: " + login
                    + " and password" + password + " failed", e);
            throw new ServiceException(e);
        }
        return user;
    }

    public Optional<User> register(User user, String password) throws ServiceException {
        Optional<User> registeredUser = Optional.empty();
        String code = codeGenerator.getCode(CODE_ITEMS, CODE_LENGTH);
        String salt = makeSalt(SALT_LENGTH);
        String passwordHash = makePasswordHash(password);
        try {
            registeredUser = userDao.findUserByLogin(user.getLogin());
            if (!registeredUser.isPresent()) {
                Long id = userDao.insertUser(user, passwordHash, salt, code);
                if (id != 0){
                    user.setId(id);
                    registeredUser = Optional.of(user);

                    //String subject = "Email confirmation ";
                    //String mailBody = "Your verification code is: " + code;
                    //mailService.sendMail(mail, subject, mailBody);
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
            logger.warn("User registration with login: " + user.getLogin() + " failed", e);
            throw new ServiceException(e);
        }
        return registeredUser;
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
    public Optional<User> findByLogin(String login) throws ServiceException {
        try {
            return userDao.findUserByLogin(login);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<User> findByMail(String mail) throws ServiceException {
        try {
            return userDao.findUserByEmail(mail);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<String> getPassword(long id) throws ServiceException {
        try {
            return userDao.getPasswordById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<String> getCode(long id) throws ServiceException {
        try {
            return userDao.getCodeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<LocalDateTime> getCodeExpirationTime(long id) throws ServiceException {
        try {
            return userDao.getCodeExpirationTimeById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long countUsers() throws ServiceException {
        try {
            return userDao.countUsers();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findFilteredUsers(long minPos, long maxPos) throws ServiceException {
        try {
            return userDao.findFilteredUsers(minPos, maxPos);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean isLoginUnique(String login){
        Optional<User> user;

        try {
            user = userDao.findUserByLogin(login);
        } catch (DaoException e) {
            logger.error("Can't handle UserService.isLoginUnique", e);
            return false;
        }
        return !user.isPresent();
    }

    public boolean isMailUnique(String mail){
        Optional<User> user;

        try {
            user = userDao.findUserByEmail(mail);
        } catch (DaoException e) {
            logger.error("Can't handle UserService.isMailUnique", e);
            return false;
        }

        return !user.isPresent();
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

    private String makeSalt(Integer len) {
        Random random = new Random();
        int symbolsAmount = SALT_ITEMS.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int symbolPosition = random.nextInt(symbolsAmount);
            char symbol = SALT_ITEMS.charAt(symbolPosition);
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
}
