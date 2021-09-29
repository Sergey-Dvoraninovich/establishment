package com.dvoraninovich.establishment.model.service.impl;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final String SALT_ITEMS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Integer SALT_LENGTH = 16;
    private static final Integer CODE_LENGTH = 16;
    private static UserServiceImpl instance;
    private UserDao userDao = UserDaoImpl.getInstance();
    private CodeGenerator codeGenerator = CodeGenerator.getInstance();

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

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

    public Optional<User> register(User user, String password) throws ServiceException {
        Optional<User> registeredUser = Optional.empty();
        String code = codeGenerator.getCode(CODE_LENGTH);
        String salt = makeSalt(SALT_LENGTH);
        String passwordHash = makePasswordHash(password);
        passwordHash = makePasswordHash(passwordHash + salt);
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
    public boolean setPasswordById(long id, String password) throws ServiceException{
        try {
            String salt = userDao.getSaltById(id).get();
            String passwordHash = makePasswordHash(password);
            passwordHash = makePasswordHash(passwordHash + salt);
            return userDao.setPasswordById(id, passwordHash);
        } catch (DaoException e) {
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
        Optional<User> user;

        try {
            user = userDao.findUserByLogin(login);
        } catch (DaoException e) {
            logger.error("Can't handle UserService.isLoginUnique", e);
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

    private String makeSalt(Integer length) {
        Random random = new Random();
        int symbolsAmount = SALT_ITEMS.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int symbolPosition = random.nextInt(symbolsAmount);
            char symbol = SALT_ITEMS.charAt(symbolPosition);
            stringBuilder.append(symbol);
        }
        return stringBuilder.toString();
    }
}
