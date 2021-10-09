package com.dvoraninovich.establishment.model.service.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.dao.UserDao;
import com.dvoraninovich.establishment.model.dao.impl.UserDaoImpl;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.service.UserService;
import com.dvoraninovich.establishment.util.SaltGenerator;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Optional;

public class UserServiceImplTest {
    private UserService service;

    @DataProvider(name = "validLoginData")
    public static Object[][] validLoginData() {
        return new Object[][] {{User.builder().setLogin("Alex").setId(1L).build(),
                    "qWer1234", "f34Hkn4H2BHB3bpJ", "d712bffab956401f1eecb538ff68572ae0b91bf7dad0dedfe8bab547945f131b"},
                {User.builder().setLogin("Bob").setId(1245L).build(),
                    "1234qWerASD", "f51308b4df39kn4H", "d83ed34de05dcfe2ac9369c9b04eb7445a274cfef0fa206218b49ad03c9e7339"}};
    }
    @Test(dataProvider = "validLoginData")
    public void userServiceAuthenticationTest(User user, String password, String salt, String passwordHash) {
        UserDao serviceDao = UserDaoImpl.getInstance();
        serviceDao = Mockito.mock(UserDao.class);
        try {
            Mockito.when(serviceDao.findUserByLogin(user.getLogin()))
                    .thenReturn(Optional.of(user));
            Mockito.when(serviceDao.getSaltById(user.getId()))
                    .thenReturn(Optional.of(salt));
            Mockito.when(serviceDao.getPasswordById(user.getId()))
                    .thenReturn(Optional.of(passwordHash));
            service = UserServiceImpl.getInstance(serviceDao, SaltGenerator.getInstance());
        } catch (DaoException e) {
            Assert.fail("Impossible to setup mocks");
        }
        Optional<User> resultUser = Optional.empty();
        try {
            resultUser = service.authenticate(user.getLogin(), password);
        } catch (ServiceException e) {
            Assert.fail("Service caused exception");
        }
        Assert.assertEquals(resultUser, Optional.of(user));
    }

    @DataProvider(name = "invalidLoginData")
    public static Object[][] invalidLoginData() {
        return new Object[][] {{User.builder().setLogin("Alex").setId(1L).build(),
                        "qWe543vr1234", "f34Hkn4H2BHB3bpJ", "d712bffab956401f1eecb538ff68572ae0b91bf7dad0dedfe8bab547945f131b"},
                {User.builder().setLogin("Bob").setId(1245L).build(),
                        "12343v54qWerASD", "f51308b4df39kn4H", "d83ed34de05dcfe2ac9369c9b04eb7445a274cfef0fa206218b49ad03c9e7339"}};
    }
    @Test(dataProvider = "invalidLoginData")
    public void userServiceAuthenticationInvalidTest(User user, String password, String salt, String passwordHash) {
        UserDao serviceDao = UserDaoImpl.getInstance();
        serviceDao = Mockito.mock(UserDao.class);
        try {
            Mockito.when(serviceDao.findUserByLogin(user.getLogin()))
                    .thenReturn(Optional.of(user));
            Mockito.when(serviceDao.getSaltById(user.getId()))
                    .thenReturn(Optional.of(salt));
            Mockito.when(serviceDao.getPasswordById(user.getId()))
                    .thenReturn(Optional.of(passwordHash));
            service = UserServiceImpl.getInstance(serviceDao, SaltGenerator.getInstance());
        } catch (DaoException e) {
            Assert.fail("Impossible to setup mocks");
        }
        Optional<User> resultUser = Optional.empty();
        try {
            resultUser = service.authenticate(user.getLogin(), password);
        } catch (ServiceException e) {
            Assert.fail("Service caused exception");
        }
        Assert.assertEquals(resultUser, Optional.empty());
    }
}
